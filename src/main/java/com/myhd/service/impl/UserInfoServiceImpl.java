package com.myhd.service.impl;

import com.myhd.entity.UserInfo;
import com.myhd.mapper.UserInfoMapper;
import com.myhd.service.IUserInfoService;
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

    @Override
    public Boolean addUserInfo(UserInfo userInfo) {

        Integer integer = null;
        try {
            integer = userInfoMapper.saveUserInfo(userInfo);
        } catch (DuplicateKeyException e) {
            return false;
        }
        return integer == 1;
    }

    @Override
    public UserInfo getUserInfoById(Integer userId) {
        return userInfoMapper.getUserInfoById(userId);
    }
}
