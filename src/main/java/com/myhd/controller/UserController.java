package com.myhd.controller;


import cn.hutool.core.util.RandomUtil;
import com.myhd.dto.FormDTO;
import com.myhd.entity.User;
import com.myhd.service.IUserService;
import com.myhd.util.AliSms;
import com.myhd.util.Code;
import com.myhd.util.Result;
import com.myhd.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 存放用户基本信息 前端控制器
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private IUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/login")
    public Result login(@RequestBody FormDTO formDTO, HttpServletResponse response){
        val result = userService.loginUser(formDTO);
        val userInfo = userService.getUserInfo(formDTO);
        User user = (User) userInfo.getObject();
        val realPhone = user.getPhone();
        /*手机号脱敏处理*/
        String phone = null;
        if (realPhone!=null){
            phone = realPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }

        val userName = user.getUserName();
        val userId = user.getUserId();
        val realEmail = user.getEmail();
        /*邮箱脱敏处理*/
        String maskedEmail = null;
        if (realEmail != null){
            val split = realEmail.split("@");
            maskedEmail = maskEmail(split[0]) + "@" + split[1];
        }

        val cookiePhone = new Cookie("cookiePhone", phone);
        val cookieUserName = new Cookie("cookieUserName", userName);
        val cookieUserId = new Cookie("cookieUserId", String.valueOf(userId));
        val cookieUserEmail = new Cookie("cookieUserEmail", maskedEmail);

        log.info("获取到的用户名："+userName);

        int maxAge = 2 * 24 * 60 * 60; // 2天 = 2 * 24小时 * 60分钟 * 60秒
        setCookie(response, cookiePhone, maxAge);
        setCookie(response, cookieUserName,maxAge);
        setCookie(response, cookieUserEmail, maxAge);
        setCookie(response, cookieUserId, maxAge);

        /*启用跨域请求*/
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");

        log.info("获取到的userInfo："+userInfo);

        if (result.getCode() == 100001){
            UserHolder.saveUser(formDTO);
        }

        return result;
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        val result = userService.registerUser(user);
        return result;
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody User user){
        val result = userService.changePassword(user);
        log.info("修改密码返回值是"+result);
        return result;
    }
    @PostMapping("/sendVerifyCode")
    public Result sendVerifyCode(@RequestBody FormDTO formDTO){
        String verifyCode = RandomUtil.randomNumbers(6);
        String key = null;
        if (formDTO.getPhone()!=null){  //发短信验证码
            key = "reset:code:"+ formDTO.getPhone();
            stringRedisTemplate.opsForValue().set(key,verifyCode); //5分钟过期
            try {
                AliSms.sendPhoneCode(formDTO.getPhone(), verifyCode, true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if (formDTO.getUserEmail()!=null){  //发邮箱验证码
            key = "reset:code:"+ formDTO.getUserEmail();
            stringRedisTemplate.opsForValue().set(key,verifyCode); //5分钟过期 

        }
        //发送验证码
        return new Result(Code.OK, verifyCode, "发送验证码");
    }

    @PostMapping("/validateCode")
    public Result validateCode(@RequestBody FormDTO formDTO){
        log.info("获取到的表单信息是"+String.valueOf(formDTO));
        if (formDTO.getPhone()!=null){
            String key = "reset:code:"+formDTO.getPhone();
            val code = stringRedisTemplate.opsForValue().get(key);
            if (formDTO.getCode().equals(code)){
                return new Result(Code.OK, true, "验证码正确");
            }else {
                return new Result(Code.FAIL, false, "验证码错误");
            }
        }else if (formDTO.getUserEmail()!=null){
            String key = "reset:code:"+formDTO.getUserEmail();
            val code = stringRedisTemplate.opsForValue().get(key);
            log.info("redis中的验证码是"+code);
            if (formDTO.getCode().equals(code)){
                return new Result(Code.OK, true, "验证码正确");
            }else {
                return new Result(Code.FAIL, false, "验证码错误");
            }
        }
        return  new Result(Code.FAIL, false, "系统错误");
    }

    private String maskEmail(String emailPart) {
        int length = emailPart.length();
        if (length <= 2) {
            return emailPart.charAt(0) + "**";
        } else {
            return emailPart.charAt(0) + "*".repeat(length - 2) + emailPart.charAt(length - 1);
        }
    }

    private void setCookie(HttpServletResponse response, Cookie cookie1, int maxAge) {
        cookie1.setMaxAge(maxAge);
        cookie1.setPath("/");
        cookie1.setHttpOnly(true);
        response.addCookie(cookie1);
    }
}
