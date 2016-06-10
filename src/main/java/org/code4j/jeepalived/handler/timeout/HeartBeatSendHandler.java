package org.code4j.jeepalived.handler.timeout;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;
import org.code4j.jeepalived.bash.ShellExecutor;
import org.code4j.jeepalived.client.MonitorSend;
import org.code4j.jeepalived.config.Init;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 01:59
 * 从节点（或发送方）用来发送心跳信号检测主节点（或接收方）是否还存活
 * 1.发送端发送完一个ping信号后，8秒未收到pong信号，则未收心跳记录+1
 * 2.未收心跳记录超过三次，认为接收端失去连接，断开接收端链接
 * 3.每当发送端收到接收端的一个pong信号，未收心跳记录清零
 * 4.若发送端与接收端断开连接，则发送端每隔10秒发起重连操作。
 */
public class HeartBeatSendHandler  extends ChannelInboundHandlerAdapter {

    //表示发给远程jeepalived的心跳
    public static final int REAL_SERVER = 1;
    //表示发给本机server的心跳
    public static final int JEEPALIVED = 0;
    private Logger logger = Logger.getLogger(HeartBeatSendHandler.class);
    private ShellExecutor executor = new ShellExecutor();
    //未收心跳记录
    private int unRecPongTimes = 1;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String pong = (String) msg;
        logger.debug(ctx.channel().remoteAddress() + " response : " + pong + sdf.format(new Date()));
        clearRecord();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            String timestamp = sdf.format(new Date());
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                if (unRecPongTimes <= Init.MAX_UNREC_PONG_TIMES){
                    logger.debug("第" + unRecPongTimes + "次读空闲 "+timestamp);
                    unRecPongTimes++;
                }else{
                    logger.debug("读空闲次数太多，可能是接收端死了。启动断线重连");
//                    ctx.channel().close();
                    dealDisconnect();
                    //将自己的IP换成接收端的
                }
            }else if (e.state() == IdleState.WRITER_IDLE){
                logger.debug("发送端发起一次ping");
                ctx.channel().writeAndFlush(Init.PING);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("发送端心跳检测出现异常,可能是接收端死了。启动断线重连 ");
        dealDisconnect();
        cause.printStackTrace();
//        ctx.close();
    }

    /**
     * 处理断线重连
     */
    private void reconnect(){
        final MonitorSend client = new MonitorSend();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    logger.debug("发起一次断线重连");
                    client.connect();
                    if (client.isAlive()){
                        timer.cancel();
                        logger.debug("重连成功^_^");
                        executor.execute(Init.SET_MASTER_IP);
                    }else{
                        logger.debug("重连失败>_<");
                    }
                    client.listen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },0, Init.RECONNECT_SECOND*1000);
    }

    private void clearRecord(){
        unRecPongTimes = 1;
    }

    public void dealDisconnect(){
        switch (Init.SEND_TO){
            //
            case REAL_SERVER:
                logger.debug("this is a second server,It will reconnect");
                executor.execute(Init.SET_SLAVE_IP);
                reconnect();
                break;
            case JEEPALIVED:
                logger.debug("this is a local jeepalived,It will fin to it's recevier");
                MonitorSend client = new MonitorSend(Init.TARGET_HOST,Init.RECEIVE_PORT);
                client.fin();
                executor.execute(Init.SHUTDOWN_NETWORK);
                logger.error("sudo service network shutdown");
                break;
        }
    }
}
