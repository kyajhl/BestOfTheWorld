package com.fan.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author KeFan
 * @date 2023/5/7
 * @time 0:20
 */

@Configuration
public class MyCORSConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许哪些域名访问服务器，不要写 * ，否则cookie无法使用
        corsConfiguration.addAllowedOrigin("http://localhost");
        // 是否发送cookie信息
        corsConfiguration.setAllowCredentials(true);
        // 允许哪些请求方式
        corsConfiguration.addAllowedMethod("*");
        // 允许的头信息
        corsConfiguration.addAllowedHeader("*");

        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}
