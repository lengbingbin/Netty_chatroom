package com.zhb.nettychat;

import com.zhb.nettychat.config.BeanConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan(value = "com.zhb.nettychat.dao")
public class NettychatApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettychatApplication.class, args);
    }

}
