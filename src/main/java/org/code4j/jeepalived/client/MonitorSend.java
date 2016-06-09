package org.code4j.jeepalived.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;
import org.code4j.jeepalived.config.Config;
import org.code4j.jeepalived.config.Init;
import org.code4j.jeepalived.config.Type;
import org.code4j.jeepalived.handler.SendChildHandler;
import org.code4j.jeepalived.handler.SendFinChildHandler;
import org.code4j.jeepalived.handler.timeout.HeartBeatSendHandler;

import java.net.InetSocketAddress;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 01:05
 */
public class MonitorSend {

    private Logger logger = Logger.getLogger(MonitorSend.class);

    private ChannelFuture future = null;
    //发送端要发给接收端的 域名和端口
    private InetSocketAddress address;
    private boolean isAlive;

    private EventLoopGroup group = null;
    private Bootstrap bootstrap = null;

    public MonitorSend() {
        address = new InetSocketAddress(Init.PRIMARY_HOST,Init.SEND_TO_PORT);
    }

    public MonitorSend(String host,int port) {
        address = new InetSocketAddress(host,port);
    }

    public void connect() throws Exception{
        logger.debug("jeepalived sender is starting...");
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group);								//group 组
        bootstrap.channel(NioSocketChannel.class);			//channel 通道
        bootstrap.option(ChannelOption.TCP_NODELAY, true);	//option 选项
        bootstrap.handler(new SendChildHandler());		//handler 处理
        //发起异步链接
        future = bootstrap.connect(address.getHostName(), address.getPort()).sync();
//            listen(f.channel());
        future.channel().writeAndFlush(Init.PING);
        isAlive = future.channel().isActive()||future.channel().isOpen();
        //等待客户端链路关闭
        logger.debug("jeepalived sender is started successfully !!");
    }

    public void fin(){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group);								//group 组
        bootstrap.channel(NioSocketChannel.class);			//channel 通道
        bootstrap.option(ChannelOption.TCP_NODELAY, true);	//option 选项
        bootstrap.handler(new SendFinChildHandler());		//handler 处理
        //发起异步链接
        try {
            future = bootstrap.connect(address.getHostName(), address.getPort()).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//            listen(f.channel());
        future.channel().writeAndFlush(Init.PING);
        isAlive = future.channel().isActive()||future.channel().isOpen();
    }

    public Channel channel(){
        return future.channel();
    }

    public boolean isAlive(){
        return this.isAlive;
    }
    public void listen(){
        System.out.println("客户端监听中");
        try {
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.debug("listen 重连失败>_<");
        }finally {
            closeChannel();
        }
    }

    public void closeChannel(){
        future.addListener(ChannelFutureListener.CLOSE);
        group.shutdownGracefully();
    }
}
