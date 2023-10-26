package com.myhd.service.impl;

import com.myhd.dto.FormDTO;
import com.myhd.entity.User;
import com.myhd.mapper.UserMapper;
import com.myhd.service.IUserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 存放用户基本信息 服务实现类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    UserMapper userMapper;

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:49
     * @param user
     * @return Boolean
     */
    @Override
    public Boolean registerUser(User user) {
        return userMapper.saveUser(user)==1?true:false;
    }

    /**
     * @description 用户登录功能。首先判断用户是否存在。如果用户不存在，不允许登陆
     * 否则，判断密码是否正确。
     * @author JoneElmo
     * @date 2023-10-26 09:49
     * @param formDTO
     * @return Boolean
     */
    @Override
    public Boolean loginUser(FormDTO formDTO) {

        Integer userExist = userMapper.isUserExist(formDTO);
        if (userExist==0){  /*用户不存在无法登陆*/
            return false;
        }else {
            User args = userMapper.findByArgs(formDTO);
            if (args.getPassword().equals(formDTO.getPassword())){
                return true;
            }
        }
        return false;
    }

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:49
     * @param user
     * @return Boolean
     */
    @Override
    public Boolean changePassword(User user) {
        return userMapper.updateUserPassword(user)==1?true:false;
    }
}
