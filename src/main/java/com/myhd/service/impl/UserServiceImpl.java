package com.myhd.service.impl;

import cn.hutool.json.JSONUtil;
import com.myhd.dto.FormDTO;
import com.myhd.entity.User;
import com.myhd.exception.BusinessException;
import com.myhd.mapper.UserMapper;
import com.myhd.service.IUserService;
import com.myhd.util.Code;
import com.myhd.util.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:49
     * @param user
     * @return Boolean
     */
    @Override
    public Result registerUser(User user) {
        Integer i = null;
        try {
            i = userMapper.saveUser(user);
        } catch (Exception e) {
            throw new BusinessException(Code.FAIL,"注册失败，用户已存在");
        }
        return i==1 ? new Result(Code.OK,true,"注册成功") : new Result(Code.FAIL,false,"注册失败");
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
    public Result loginUser(FormDTO formDTO) {
        Integer userExist = userMapper.isUserExist(formDTO);
        if (userExist==0){  /*用户不存在无法登陆*/
            return new Result(Code.FAIL,false,"用户不存在");
        }else {
            User args = userMapper.findByArgs(formDTO);
            if (args.getPassword().equals(formDTO.getPassword())){
                /* 密码正确就将信息缓存到redis中 */
                String key = "login:token:"+args.getUserId();
                String jsonStr = JSONUtil.toJsonStr(args);
                stringRedisTemplate.opsForValue().set(key, jsonStr);
                return new Result(Code.OK,true,"登陆成功");
            }
        }
        return new Result(Code.FAIL,false,"密码错误");
    }

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:49
     * @param user
     * @return Boolean
     */
    @Override
    public Result changePassword(User user) {
        Integer i = userMapper.updateUserPassword(user);
        return i==1 ? new Result(Code.OK,true,"修改成功") : new Result(Code.FAIL,false,"修改失败");
    }

    @Override
    public Result getUserById(Integer userId) {
        User userById = userMapper.getUserById(userId);
        if (userById != null){
            return Result.ok(Code.GET_OK,userById,"信息获取成功");
        }
        return Result.ok(Code.GET_FAIL, null,"信息获取失败");
    }
}
