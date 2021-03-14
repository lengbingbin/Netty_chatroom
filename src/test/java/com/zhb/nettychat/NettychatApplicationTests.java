package com.zhb.nettychat;

import com.zhb.nettychat.config.ServerConfig;
import com.zhb.nettychat.controller.ChatController;
import com.zhb.nettychat.dao.impl.UserInfoDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NettychatApplicationTests {

    @Test
    void contextLoads() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.init();
    }

    @Test
    void contextLoads1() {
        ChatController chatController = new ChatController();
        //chatController.login("1","0");
    }

//    @Test
//    void contextLoads2() {
//        UserInfoDaoImpl userInfoDao = new UserInfoDaoImpl();
//        userInfoDao.loadUserInfo();
//    }


}
