package org.code4j.jeepalived.server;/**
 * Description : server
 * Created by YangZH on 16-5-25
 *  上午8:14
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.code4j.jeepalived.config.Init;
import org.code4j.jeepalived.handler.ReceiveChildHandler;

/**
 * Description : server
 * Created by YangZH on 16-5-25
 * 上午8:14
 */

public class MonitorReceive {

    private Logger logger = Logger.getLogger(MonitorReceive.class);
    private ChannelFuture future;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;
    public void startup(){
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        logger.debug("jeepalived receiver is starting...");
        try {
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ReceiveChildHandler())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true);
            future = bootstrap.bind(Init.RECEIVE_PORT).sync();
            logger.debug("jeepalived receiver is started successfully !!");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeChannel();
        }
    }

    public void closeChannel(){
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
