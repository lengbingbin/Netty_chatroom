package com.zhb.nettychat.config;

import com.zhb.nettychat.web.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
@Scope("singleton")
public class ServerConfig {

    private final Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    private Thread nettythread;
    @Autowired
    private WebSocketServer webSocketServer;


    @PostConstruct
    public void init() {
        nettythread = new Thread(webSocketServer);
        logger.info("开始加载netty服务器......");
        nettythread.start();
        logger.info("开始加载用户数据......");
        //userInfo.loadUserInfo();
        logger.info("开始加载群组数据......");
        //groupInfo.loadGroupInfo();
    }

    //这样关闭会可能会接收不到其他登录的用户发来的消息(猜想)/后续务必确认！！！！！
    @PreDestroy
    public void close() {
        logger.info("正在释放Netty WebSocket连接......");
        webSocketServer.close();
        logger.info("正在关闭Netty WebSocket线程......");
        nettythread.stop();
        logger.info("系统成功关闭！");
    }

}
