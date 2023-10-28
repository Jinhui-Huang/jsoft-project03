package com.myhd.controller;


import com.myhd.entity.UserInfo;
import com.myhd.service.impl.UserInfoServiceImpl;
import com.myhd.service.impl.UserServiceImpl;
import com.myhd.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
public class UserInfoController {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private UserInfoServiceImpl userInfoService;
    /**
     * @description: 初次进入个人信息页面时获取个人信息页面的手机号或密码使用
     * @param userId 用户编号
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/26 下午7:56
     */
    @GetMapping("getPhoneOrEmail/{userId}")
    public Result getPhoneOrEmail(@PathVariable Integer userId){
        return userService.getUserById(userId);
    }

    @GetMapping("getUserInfo/{userId}")
    public Result getUserInfo(@PathVariable Integer userId){
        return userInfoService.getUserInfoById(userId);
    }

    @PostMapping
    public Result addUserInfo(@RequestBody UserInfo userInfo){
        System.out.println(userInfo);
        return userInfoService.addUserInfo(userInfo);
    }

}
