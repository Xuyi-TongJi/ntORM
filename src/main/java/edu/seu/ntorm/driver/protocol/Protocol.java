package edu.seu.ntorm.driver.protocol;

public interface Protocol {

    /**
     * 解码
     */
    String decode(String code);

    /**
     * 编码
     */
    String encode(String code);
}
