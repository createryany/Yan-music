package com.createryan.yanmusic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.createryan.yanmusic.mapper")
@SpringBootApplication
public class YanMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(YanMusicApplication.class, args);
    }

}
