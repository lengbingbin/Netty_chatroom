package com.zhb.nettychat.web.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器
 */
@Configuration
public class MyHandlerInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //加载登录适配器
        registry.addInterceptor(new UserAuthInteceptor())
                .addPathPatterns("/chat/chatroom")
                .excludePathPatterns("/chat/login");
    }
}
