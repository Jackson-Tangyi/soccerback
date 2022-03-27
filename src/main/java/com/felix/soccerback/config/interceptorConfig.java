package com.felix.soccerback.config;

import com.felix.soccerback.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class interceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(jwtInterceptor())//注册拦截器
                    .addPathPatterns("/**")//拦截所有请求，通过判断token是否合法 决定是否需要登录
                    .excludePathPatterns("/user/login","/user/register","/**/export","/**/import","/file/**","/history/**",
                            "/front/**"); //放行这些接口
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }//直接去new没有注入到容器，需要自己去注入一下JwtInterceptor

}
