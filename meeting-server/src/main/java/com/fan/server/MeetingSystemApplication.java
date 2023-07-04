package com.fan.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author KeFan
 * @date 2023/7/3
 * @time 13:49
 */

@SpringBootApplication
@MapperScan("com.fan.server.mapper")
public class MeetingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeetingSystemApplication.class, args);
    }
}
