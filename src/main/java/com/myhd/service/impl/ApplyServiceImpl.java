package com.myhd.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myhd.entity.Apply;
import com.myhd.entity.Recruit;
import com.myhd.mapper.ApplyMapper;
import com.myhd.service.IApplyService;
import com.myhd.util.Code;
import com.myhd.util.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
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

    /**
     * @description 点击立即申请按钮，发送信息
     * @author JoneElmo && CYQH
     * @date 2023-10-24 10:25
     * @param apply 申请的数据对象
     * @return Result
     */
    @Override
    public Result insertApplyInfo(Apply apply) {
        Integer integer = null;
        try {
            integer = applyMapper.insertApplyInfo(apply);
        } catch (DuplicateKeyException e) {
            return Result.fail(Code.POST_FAIL,false,"申请职位失败");
        }
        if (integer == 1){
            return Result.ok(Code.POST_OK,true,"申请职位成功");
        }else {
            return Result.fail(Code.POST_FAIL,false,"申请职位失败");
        }
    }

    /**
     * @description: 在我的职位页面显示所有已申请的职位,模糊查询和分页
     * @param userId 用户编号
     * @param like 模糊查询关键字
     * @param pageNum 分页页码
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/26 下午2:42
     */
    @Override
    public Result getAllUserApply(Integer userId, String like,Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        String key = userId+":"+like+":"+pageNum;
        List<Recruit> allUserApply;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            allUserApply = JSONUtil.toList(s,Recruit.class);
            System.out.println("从redis中获取");
        }else {
            allUserApply = applyMapper.getAllUserApply(userId, like);
            System.out.println("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(allUserApply);
            /*存储到数据库中,有效五分钟*/
            stringRedisTemplate.opsForValue().set(key,jsonStr, Duration.ofMinutes(5L));
        }
        PageInfo<Recruit> pageInfo = new PageInfo<>(allUserApply);
        return Result.ok(Code.GET_OK,pageInfo,"查询成功");
    }
}
