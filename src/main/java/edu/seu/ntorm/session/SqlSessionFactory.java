package edu.seu.ntorm.session;

public interface SqlSessionFactory {

    /**
     * 打开一个Session
     * @return SqlSession
     */
    SqlSession openSession();
}
