package com.fan.server.config;

import com.fan.server.interceptor.JwtValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author KeFan
 * @date 2023/5/9
 * @time 20:49
 */

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtValidateInterceptor jwtValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
        registration.addPathPatterns("/**")
                .excludePathPatterns(
                        "/student/login",
                        "/student/logout",
                        "/student/register",
                        "/teacher/register",
                        "/teacher/login",
                        "/teacher/logout",
                        "/enterprise/register",
                        "/enterprise/login",
                        "/enterprise/logout",
                        "/error",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/**"
                );
    }
}
