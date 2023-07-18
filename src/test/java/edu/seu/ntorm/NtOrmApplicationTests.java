package edu.seu.ntorm;

import edu.seu.ntorm.binding.MapperProxyFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class NtOrmApplicationTests {

    interface IUserDao {
        String queryUserName(String uId);

        Integer queryUserAge(String uId);
    }

    @Test
    void testMapperProxyFactory() {

    }

}
