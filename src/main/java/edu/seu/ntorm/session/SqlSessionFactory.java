package edu.seu.ntorm.session;

public interface SqlSessionFactory {

    /**
     * 工厂方法：打开一个Session
     * @return SqlSession
     */
    SqlSession openSession();
}
