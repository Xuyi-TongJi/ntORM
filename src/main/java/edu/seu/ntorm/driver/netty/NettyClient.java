package edu.seu.ntorm.driver.netty;

import io.netty.channel.Channel;

public interface NettyClient {

    /**
     * 获得Channel
     * @return Channel
     */
    Channel getChannel(String ip, int port);

    /**
     * 协议封装
     */
    String packageByProtocol(String code);

    /**
     * 协议拆装
     */
    String unpackByProtocol(String code);
}
