package com.myhd.controller;

import com.myhd.entity.UserInfo;
import com.myhd.service.impl.UserInfoServiceImpl;
import com.myhd.util.Result;
import org.springframework.web.bind.annotation.*;
import com.myhd.dto.FormDTO;
import com.myhd.service.IUserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserInfoController {
    @Resource
    private IUserService userService;

    @Resource
    private UserInfoServiceImpl userInfoService;
    /**
     * @description 获取用户信息
     * @author JoneElmo
     * @date 2023-10-26 19:18
     * @param
     * @return
     */
    @PostMapping("/getUserInfo")
    public Result getUserInfo(@RequestBody FormDTO formDTO){
        return userService.getUserInfo(formDTO);
    }

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

    /**
     * @description: 获取用户个人信息用于数据显示
     * @param userId 用户编号
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/28 下午4:30
     */
    @GetMapping("getUserInfo/{userId}")
    public Result getUserInfo(@PathVariable Integer userId){
        return userInfoService.getUserInfoById(userId);
    }

    /**
     * @description: 添加用户个人信息到个人用户信息表上
     * @param userInfo 用户个人信息实体类
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/28 下午4:31
     */
    @PostMapping
    public Result addUserInfo(@RequestBody UserInfo userInfo){
        System.out.println(userInfo);
        return userInfoService.addUserInfo(userInfo);
    }

}
