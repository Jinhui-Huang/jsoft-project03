package com.myhd.dto;

import lombok.Data;

/**
 * @Classname ToEmail
 * @Description TODO
 * @Date 2023/10/25 下午9:49
 * @Created by joneelmo
 */
@Data
public class ToEmail {
    private String toUser;
    private String subject;
    private String content;
}
