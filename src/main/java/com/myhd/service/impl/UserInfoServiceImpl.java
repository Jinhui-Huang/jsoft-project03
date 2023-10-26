package com.myhd.service.impl;

import com.myhd.entity.UserInfo;
import com.myhd.mapper.UserInfoMapper;
import com.myhd.service.IUserInfoService;
import com.myhd.util.Code;
import com.myhd.util.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 存放用户详细信息 服务实现类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * @description: 进行个人信息的完善插入
     * @param userInfo 用户个人信息
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/26 下午3:28
     */
    @Override
    public Result addUserInfo(UserInfo userInfo) {
        Integer integer;
        try {
            integer = userInfoMapper.saveUserInfo(userInfo);
        } catch (DuplicateKeyException e) {
            return Result.fail(Code.POST_FAIL,false,"完善个人信息失败");
        }
        if(integer == 1){
            return Result.ok(Code.POST_OK,true,"完善个人信息成功");
        }
        return Result.fail(Code.POST_FAIL,false,"完善个人信息失败");
    }

    /**
     * @description: 根据用户编号获取用户个人信息进行数据展示
     * @param userId 用户编号
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/26 下午3:33
     */
    @Override
    public Result getUserInfoById(Integer userId) {

        UserInfo userInfo = userInfoMapper.getUserInfoById(userId);
        if (userInfo != null){
            return Result.ok(Code.GET_OK,userInfo,"获取个人信息成功");
        }
        return Result.fail(Code.GET_FAIL, null,"获取个人信息失败");
    }
}
