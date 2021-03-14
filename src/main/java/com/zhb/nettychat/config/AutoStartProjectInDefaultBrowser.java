package com.zhb.nettychat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;

/**
 * 设置自动开启浏览器
 */
@SpringBootConfiguration
public class AutoStartProjectInDefaultBrowser implements CommandLineRunner {

    //注入项目的端口号
    @Value("${server.port}")
    private String port;

    /**
     * springboot自带的监听任务
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:" + port + "/chat/login");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
