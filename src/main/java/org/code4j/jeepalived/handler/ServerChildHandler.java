package org.code4j.jeepalived.handler;/**
 * Description : ChildHandler
 * Created by YangZH on 16-5-25
 *  上午9:13
 */

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.code4j.jeepalived.config.Configuration;
import org.code4j.jeepalived.handler.timeout.HeartBeatReceiveHandler;
/**
 * Description : ChildHandler
 * Created by YangZH on 16-5-25
 * 上午9:13
 */

public class ServerChildHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("ping_handler", new IdleStateHandler(Configuration.READ_SECOND,
                Configuration.WRITE_SECOND, Configuration.READ_WRITE_SECOND));
        // 字符串解码 和 编码
        pipeline.addLast("string_decoder", new StringDecoder());
        pipeline.addLast("string_encoder", new StringEncoder());
        pipeline.addLast("heartbeat_handler", new HeartBeatReceiveHandler());
    }
}
