package edu.seu.ntorm.driver.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Netty测试类
 */
public class NettyTest {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.bind();
        ChannelFuture cf = bootstrap.connect("127.0.0.1", 8080);
        try {
            cf.sync();
            cf.addListener((ChannelFutureListener) channelFuture -> {

            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
