package com.myhd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myhd.dto.Filed;
import com.myhd.entity.Recruit;
import com.myhd.mapper.RecruitMapper;
import com.myhd.service.IRecruitService;
import com.myhd.util.Code;
import com.myhd.util.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 招聘职位表 服务实现类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@Service
public class RecruitServiceImpl implements IRecruitService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RecruitMapper recruitMapper;

    /**
     * Description: getFields 服务层中获取redis中存放的相关领域
     * 101~104: 销售 | 市场 | 客服 | 贸易
     * 201~204: 经验管理 | 人事 | 行政 | 财务
     * 301~304: 生产 | 质管 | 技工 | 物流
     * 401~404: 教育 | 法律 | 咨询 | 翻译
     * 501~504: 零售 | 家政 | 餐饮 | 旅游
     * 601~604: 广告 | 媒体 | 艺术 | 出版
     * 701~703: IT | 互联网 | 通信
     * @return com.myhd.util.Result
     * @author jinhui-huang
     * @Date 2023/10/26
     */
    @Override
    public Result getFields() {
        String key = "job:cache:filed";
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        List<Filed> list = new ArrayList<>();
        entries.forEach((o, o2) -> {
            int jobFiled = Integer.parseInt(String.valueOf(o));
            String filedName = String.valueOf(o2);
            list.add(new Filed(jobFiled, filedName));
        });
        return new Result(Code.GET_OK, list, "全领域展示在前端");
    }


    @Override
    public PageInfo<Recruit> getHighSalary() {
        PageHelper.startPage(1,3);
        List<Recruit> highSalary = recruitMapper.getHighSalary();
        return new PageInfo<>(highSalary);
    }
}
