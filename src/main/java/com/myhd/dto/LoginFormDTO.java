package com.myhd.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description: LoginFormDto
 * <br></br>
 * className: LoginFormDto
 * <br></br>
 * packageName: com.myhd.dto
 *
 * @author jinhui-huang
 * @version 1.0
 * @email 2634692718@qq.com
 * @Date: 2023/10/22 17:00
 */
@Data
public class LoginFormDTO{
    private Integer userId;
    private String phone;
    private String userEmail;
    private String userName;
    private String password;

}
