package edu.seu.ntorm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
