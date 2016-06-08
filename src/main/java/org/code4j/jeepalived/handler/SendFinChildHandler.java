package org.code4j.jeepalived.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.code4j.jeepalived.config.Config;
import org.code4j.jeepalived.handler.timeout.HeartBeatSendHandler;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 00:59
 */
public class SendFinChildHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 字符串解码 和 编码
        pipeline.addLast("string_decoder", new StringDecoder());
        pipeline.addLast("string_encoder", new StringEncoder());
        pipeline.addLast("heartbeat_handler",new HeartBeatSendHandler());
    }
}
