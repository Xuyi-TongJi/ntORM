package edu.seu.ntorm.builder.xml;

import edu.seu.ntorm.builder.BaseBuilder;
import edu.seu.ntorm.builder.session.defaults.DefaultConfiguration;
import edu.seu.ntorm.exception.ParseConfigurationException;
import edu.seu.ntorm.io.ResourcesUtil;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.mapping.SqlCommandType;
import edu.seu.ntorm.session.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
 *     <mapper resource=""/>
 *     <mapper resource=""/>
 * </mappers>
 * </xml>
 *
 * XML Mapper文件格式
 *  <xml namespace="DAO全类名"> <!-- root element -->
 *      <!-- select update delete insert -->
 *          <select>
 *              <!-- SQL -->
 *          </select>
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
public class DefaultXmlConfigBuilder extends BaseBuilder {

    /**
     * XML根标签
     */
    private Element root;

    private static final String MAPPERS = "mappers";

    private static final String MAPPER = "mapper";

    private static final String RESOURCE = "resource";

    private static final String NAMESPACE = "namespace";

    private static final String PARAMETER_TYPE = "parameterType";

    private static final String RESULT_TYPE = "resultType";

    private static final String ID = "id";

    public DefaultXmlConfigBuilder(Reader reader) {
        super(new DefaultConfiguration());
        // dom4j 处理XML
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new InputSource(reader));
            root = document.getRootElement();
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
    }

    public Configuration parse() throws ParseConfigurationException {
        try {
            mapperElement(root.element(MAPPERS));
        } catch (Exception e) {
            throw new ParseConfigurationException();
        }
        return configuration;
    }

    /**
     * 解析XML的逻辑, 使用dom4j
     * @param mappers mappers
     */
    private void mapperElement(Element mappers) throws Exception {
        List<Element> elements = mappers.elements(MAPPER);
        for (Element element : elements) {
            String resource = element.attributeValue(RESOURCE);
            Reader reader = ResourcesUtil.getResourceAsReader(resource);
            // read mapper
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();
            // namespace
            String namespace = root.attributeValue(NAMESPACE);

            List<Element> statements = root.elements();
            for (Element statement : statements) {
                parseStatement(statement, namespace);
            }
        }
    }

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
        SqlCommandType commandType = SqlCommandType.valueOf(statementName);
        MappedStatement statement = MappedStatement.build(
                this.configuration,
                methodId,
                commandType,
                parameterType,
                resultType,
                sql,
                params);
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
            params.put(i, g2);
            sql = sql.replace(g1, "?");
        }
        return sql;
    }
}
