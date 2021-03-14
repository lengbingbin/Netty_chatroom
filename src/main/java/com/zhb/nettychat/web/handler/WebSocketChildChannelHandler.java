package com.zhb.nettychat.web.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * worker线程的处理器
 */
@Component
@Qualifier("webSocketChildChannelHandler")
public class WebSocketChildChannelHandler extends ChannelInitializer<SocketChannel> {

    //用于业务处理的处理器
    @Resource
    private ChannelHandler webSocketServerHandler;
    @Resource
    private ChannelHandler httpRequestHandler;


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());//编码解码器
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));//把HTTP头、HTTP体拼成完整的HTTP请求;
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());//方便大文件传输，不过实质上都是短的文本数据
        socketChannel.pipeline().addLast("http-handler", httpRequestHandler);
        socketChannel.pipeline().addLast("websocket-handler", webSocketServerHandler);
    }
}
