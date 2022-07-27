package com.createryan.yanmusic.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: createryan
 * @date 2022/7/27 1:59
 */

@Data
public class ConsumerDTO {

    private Integer id;

    private String username;

    private Byte sex;

    private String phoneNum;

    private String email;

    private Date birth;

    private String introduction;

    private String location;

    private String avator;
}
