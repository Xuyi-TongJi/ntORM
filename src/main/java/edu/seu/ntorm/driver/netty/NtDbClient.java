package edu.seu.ntorm.driver.netty;

import edu.seu.ntorm.driver.protocol.Protocol;
import edu.seu.ntorm.driver.protocol.RespProtocol;
import io.netty.channel.Channel;

/**
 * 基于NettyClient实现NtDBClient
 */
public class NtDbClient implements NettyClient {

    /**
     * 目前只支持RESP协议
     */
    private final Protocol protocol = new RespProtocol();

    /**
     * IP
     */
    private final String ip;

    /**
     * Port
     */
    private final int port;


    public NtDbClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        // 初始化Client结构
        init();
    }

    /**
     * 连接到ntDB Server并获取
     * @param ip ip
     * @param port port
     * @return netty channel
     */
    @Override
    public Channel getChannel(String ip, int port) {
        return null;
    }

    @Override
    public String packageByProtocol(String code) {
        return null;
    }

    @Override
    public String unpackByProtocol(String code) {
        return null;
    }

    private void init() {

    }
}
