package edu.seu.ntorm.builder.xml;

import edu.seu.ntorm.builder.BaseBuilder;
import edu.seu.ntorm.exception.ParseConfigurationException;
import edu.seu.ntorm.io.ResourcesUtil;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.type.SqlCommandType;
import edu.seu.ntorm.ntDb.DefaultBuilderAutoConfigurator;
import edu.seu.ntorm.env.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 默认XML解析器
 * 解析XML文件，并存放在Configuration中
 * 初始化Configuration
 * 建造者模式
 * XML查询语句 格式
 * <select id="methodName" parameterType ="Integer" resultType="User">
 *  select * from user where id = #{id}
 * </select>
 *
 * XML配置文件格式
 * <xml> <!-- root element -->
 * <mappers>
 *     <!-- mapper -->
 *     <mapper resource="mapper/user_mapper.xml"/>
 *     <mapper resource="mapper/student_mapper.xml"/>
 * </mappers>
 * </xml>
 *
 * XML Mapper文件格式
 *  <xml namespace="DAO全类名(edu.seu.ntORM.mapper.UserMapper)"> <!-- root element -->
 *      <!-- select update delete insert -->
 *          <select id="queryUserById" parameterType="java.lang.Long" resultType="edu.seu.ntORM.pojo.User">
 *              <!-- SQL -->
 *              select id, username, age from user where id = #{id}
 *          </select>
 *          <!-- 以pojo为parameterType -->
 *          <select id="queryUser" parameterType"edu.seu.ntORM.pojo.User" resultType="edu.seu.ntORM.pojo.User">
 *              select id, username, age from user where id = #{id} and age = #{age} and username = #{username}
 *          </select>
 *          <!-- 以Map为parameterType --> <!-- resultType也可以为Map -->
 *          <select id="queryUser" parameterType"java.util.Map" resultType="edu.seu.ntORM.pojo.User">
 *              select id, username, age from user where id = #{id} and age = #{age} and username = #{username}
 *          </select>
 *
 *          <update>
 *
 *          </update>
 *          <delete>
 *
 *          </delete>
 *          <insert>
 *
 *          </insert>
 * </xml>
 */
@Slf4j
@ConditionalOnBean(value = {DefaultBuilderAutoConfigurator.class})
@ConditionalOnMissingBean(value = {BaseBuilder.class})
@Component
public class DefaultXmlConfigBuilder extends BaseBuilder {

    @Autowired
    private Configuration configuration;

    private static final String MAPPERS = "mappers";

    private static final String ENVIRONMENTS = "environments";

    private static final String MAPPER = "mapper";

    private static final String RESOURCE = "resource";

    private static final String NAMESPACE = "namespace";

    private static final String PARAMETER_TYPE = "parameterType";

    private static final String RESULT_TYPE = "resultType";

    private static final String ID = "id";

    // TODO environment 参数

    @Override
    public void parseByReader(Reader reader) throws ParseConfigurationException {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            mapperElement(document.getRootElement().element(MAPPERS));
            // 改为Spring注解配置
            // environmentElement(document.getRootElement().element(ENVIRONMENTS));
        } catch (Exception e) {
            throw new ParseConfigurationException();
        }
    }

    /**
     * 解析XML的逻辑, 使用dom4j
     * 完成 1. 映射所有MapperInterface到Configuration的MapperRegistry中
     *     2. 对每个Mapper,解析所有Mapper的XML文件，将所有SQL映射到Configuration中的statements中，ID为Mapper Interface全类名+方法名
     * @param mappers mappers
     */
    private void mapperElement(Element mappers) throws Exception {
        List<Element> elements = mappers.elements(MAPPER);
        for (Element element : elements) {
            String resource = element.attributeValue(RESOURCE);
            // read mapper
            Reader reader = ResourcesUtil.getResourceAsReader(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();
            // namespace Mapper Interface全类名
            String namespace = root.attributeValue(NAMESPACE);

            // 映射Mapper XML文件中的所有SQL Statement
            List<Element> statements = root.elements();
            for (Element statement : statements) {
                parseStatement(statement, namespace);
            }
            // 注册Mapper映射器类
            configuration.addMapper(ResourcesUtil.classForName(namespace));
        }
    }

//    private void environmentElement(Element context) {
//        String environment = context.attributeValue("default");
//        if (environment == null) {
//            throw new ParseConfigurationException();
//        }
//        List<Element> envs = context.elements("environment");
//        for (Element env : envs) {
//            String id = env.attributeValue("id");
//            if (env.equals(id)) {
//                // 配置事务管理器
//
//            }
//        }
//    }


    private void parseStatement(Element e, String namespace) {
        // <select id="" parameterType="" resultType=""> sql </select>
        String id = e.attributeValue(ID);
        String resultType = e.attributeValue(RESULT_TYPE);
        String parameterType = e.attributeValue(PARAMETER_TYPE);
        String sql = e.getText(); // sql
        // ? 匹配获得params
        Map<Integer, String> params = new HashMap<>();
        sql = matchParameter(sql, params);
        String methodId = namespace + "." + id; // methodId 全类名+方法名(id)
        String statementName = e.getName();

        // Mapped Statement To Configuration
        SqlCommandType commandType = SqlCommandType.typeOf(statementName);
        MappedStatement statement = MappedStatement.build(
                this.configuration,
                methodId, // edu.seu.ntORM.mapper.UserMapper.queryUserById
                commandType, // select
                parameterType, // java.lang.Long
                resultType, // edu.seu.ntORM.pojo.User
                sql, // select * from user where id = ? and username = ?
                params); // 0->id; 1->username (实质上是?对应的参数name)
        configuration.addMappedStatement(statement);
    }

    /**
     * ?匹配, 基于正则表达式
     * @param sql SQL
     * @return SQL after matching
     */
    private String matchParameter(String sql, Map<Integer, String> params) {
        Pattern pattern = Pattern.compile("(#\\{(.*?)})");
        Matcher matcher = pattern.matcher(sql);
        for (int i = 1; matcher.find(); ++i) {
            String g1 = matcher.group(1);
            String g2 = matcher.group(2);
            params.put(i - 1, g2);
            sql = sql.replace(g1, "?");
        }
        // 修改前 select * from user where id = #{id} and username = #{username}
        // 修改后 select * from user where id = ? and username = ?
        // params: 0->id; 1->username
        return sql;
    }
}
