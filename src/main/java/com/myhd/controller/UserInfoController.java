package com.myhd.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.myhd.dto.FormDTO;
import com.myhd.entity.User;
import com.myhd.exception.BusinessException;
import com.myhd.service.IUserService;
import com.myhd.util.AliSms;
import com.myhd.util.Code;
import com.myhd.util.Result;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpCookie;
import java.time.Duration;
import java.util.Random;

/**
 * <p>
 * 用户信息表 存放用户详细信息 前端控制器
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@RestController
@RequestMapping("/user-info")
@Slf4j
public class UserInfoController {
    @Resource
    private IUserService userService;

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 14:07
     * @param
     * @return
     */
    @GetMapping("/getImageVerifyCode")
    public String getImageVerifyCode(){
        return null;
    }

    /**
     * @description 生成图片验证码并返回给前端
     * @author JoneElmo
     * @date 2023-10-26 19:18
     * @param
     * @return
     */
    @PostMapping("/getUserInfo")
    public Result getUserInfo(@RequestBody FormDTO formDTO){
        Result userInfo = userService.getUserInfo(formDTO);
        return userInfo;
    }

}
