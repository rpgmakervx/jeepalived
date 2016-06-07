package org.code4j.jeepalived.server;/**
 * Description : server
 * Created by YangZH on 16-5-25
 *  上午8:14
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.code4j.jeepalived.config.Config;
import org.code4j.jeepalived.config.Init;
import org.code4j.jeepalived.handler.ReceiveChildHandler;

/**
 * Description : server
 * Created by YangZH on 16-5-25
 * 上午8:14
 */

public class MonitorReceive {

    public void startup(){
        System.out.println("正在启动服务。。。");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        ChannelFuture future = null;
        try {
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ReceiveChildHandler())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true);
            future = b.bind(Init.RECEIVE_PORT).sync();
            System.out.println("服务已启动");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            future.addListener(ChannelFutureListener.CLOSE);
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
