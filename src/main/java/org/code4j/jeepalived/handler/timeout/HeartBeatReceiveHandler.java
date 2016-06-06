package org.code4j.jeepalived.handler.timeout;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;
import org.code4j.jeepalived.config.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description :
 * Created by code4j on 2016/6/6 0006
 * 12:43
 *
 * 1.接收端8秒未读到心跳，则未读心跳记录+1
 * 2.未读心跳记录超过3次，则认为发送端失去连接，断开发送端链接
 * 3.当接收端读到发送端的心跳时，未读心跳记录清零
 */
public class HeartBeatReceiveHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = Logger.getLogger(HeartBeatReceiveHandler.class);
    //未读心跳记录
    private int unRecPingTimes = 0;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String ping = (String) msg;
        logger.debug(ctx.channel().remoteAddress()+" says : "+ping);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("触发事件");
        if (evt instanceof IdleStateEvent) {
            String timestamp = sdf.format(new Date());
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                if (unRecPingTimes < Configuration.MAX_UNREC_PING_TIMES){
                    logger.debug("第" + unRecPingTimes + "次读空闲");
                }else{
                    logger.debug("读空闲次数太多，断开发送端链接");
                    ctx.channel().close();
                }
            }else if (e.state() == IdleState.WRITER_IDLE) {
                logger.debug("写空闲了：" + timestamp);
                ctx.writeAndFlush("服务端回pong "+timestamp);
            }else if (e.state() == IdleState.ALL_IDLE){
                logger.debug("读写全部空闲了：" + sdf.format(new Date()));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error("心跳检测出现异常");
    }

    private void clearRecord(){
        unRecPingTimes = 0;
    }
}
