package com.zhb.nettychat.web.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

public class WebSocketServer implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private int port;

    private ChannelFuture serverChannelFuture;
    @Resource(name = "webSocketChildChannelHandler")
    private ChannelHandler childChannelHandler;

    @Autowired
    private EventLoopGroup bossGroup;
    @Autowired
    private EventLoopGroup workerGroup;
    @Autowired
    private ServerBootstrap serverBootstrap;

    public WebSocketServer() {

    }

    @Override
    public void run() {
        try {
            long begin = System.currentTimeMillis();
            //option针对的是boss线程，childoption针对的是worker线程
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)//配置TCP，握手字符串长度设置
                    .option(ChannelOption.TCP_NODELAY, true)//使其尽可能的发送大块数据，提高效率
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳机制
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))//配置固定长度接收缓存区分配器
                    .childHandler(childChannelHandler);
            long end = System.currentTimeMillis();
            logger.info("Netty Websocket服务器启动完成，耗时 " + (end - begin));
            //绑定端口
            serverChannelFuture = serverBootstrap.bind(port).sync();
            //监听关闭事件
            serverChannelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            logger.info(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接，将boss线程和worker线程都关闭
     */
    public void close() {
        serverChannelFuture.channel().close();
        Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();
        try {
            bossGroupFuture.await();//？
            workerGroupFuture.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ChannelHandler getChildChannelHandler() {
        return childChannelHandler;
    }

    public void setChildChannelHandler(ChannelHandler childChannelHandler) {
        this.childChannelHandler = childChannelHandler;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
