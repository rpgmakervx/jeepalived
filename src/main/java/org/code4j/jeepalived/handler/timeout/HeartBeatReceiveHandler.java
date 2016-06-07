package org.code4j.jeepalived.handler.timeout;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;
import org.code4j.jeepalived.config.Config;
import org.code4j.jeepalived.config.Init;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description :
 * Created by code4j on 2016/6/6 0006
 * 12:43
 *主节点（或接收方）接收心跳包并回复给从节点（或发送方）心跳
 * 1.接收端8秒未读到心跳，则未读心跳记录+1
 * 2.未读心跳记录超过3次，则认为发送端失去连接，断开发送端链接
 * 3.当接收端读到发送端的心跳时，未读心跳记录清零
 */
public class HeartBeatReceiveHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = Logger.getLogger(HeartBeatReceiveHandler.class);


    //未读心跳记录
    private int unRecPingTimes = 1;

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String ping = (String) msg;
        logger.debug(ctx.channel().remoteAddress()+" says : "+ping);
        clearRecord();
        logger.debug("接收端回应起一次pong");
        ctx.channel().writeAndFlush(Init.PONG);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            String timestamp = sdf.format(new Date());
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                if (unRecPingTimes <= Init.MAX_UNREC_PING_TIMES){
                    logger.debug("第" + unRecPingTimes + "次读空闲 "+timestamp);
                    unRecPingTimes++;
                }else{
                    logger.debug("读空闲次数太多，断开发送端链接");
                    ctx.channel().close();
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error("接收端心跳检测出现异常,启动断线重连");
        ctx.channel().close();
    }


    private void clearRecord(){
        unRecPingTimes = 1;
    }

}
