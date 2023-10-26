package com.myhd.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myhd.entity.Apply;
import com.myhd.entity.Recruit;
import com.myhd.mapper.ApplyMapper;
import com.myhd.service.IApplyService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 职位申请表 服务实现类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@Service
public class ApplyServiceImpl implements IApplyService {

    @Resource
    private ApplyMapper applyMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean insertApplyInfo(Apply apply) {
        Integer integer = null;
        try {
            integer = applyMapper.insertApplyInfo(apply);
        } catch (DuplicateKeyException e) {
            return false;
        }
        return integer == 1;
    }

    @Override
    public PageInfo<Recruit> getAllUserApply(Integer userId, String like,Integer pageNum) {
        PageHelper.startPage(pageNum,1);
        String key = userId+":"+like+":"+pageNum;
        List<Recruit> allUserApply;
//        String s = stringRedisTemplate.opsForValue().get(key);
//        if (s != null){
//            allUserApply = JSONUtil.toList(s,Recruit.class);
//            System.out.println("从redis中获取");
//        }else {
//            allUserApply = applyMapper.getAllUserApply(userId, like);
//            System.out.println("从数据库获取");
//            String jsonStr = JSONUtil.toJsonStr(allUserApply);
//            stringRedisTemplate.opsForValue().set(key,jsonStr, Duration.ofMinutes(30L));
//        }
        allUserApply = applyMapper.getAllUserApply(userId, like);
        return new PageInfo<>(allUserApply);
    }
}
