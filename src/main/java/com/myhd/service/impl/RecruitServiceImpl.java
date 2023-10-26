package com.myhd.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myhd.dto.Filed;
import com.myhd.entity.Company;
import com.myhd.entity.Recruit;
import com.myhd.mapper.RecruitMapper;
import com.myhd.service.IRecruitService;
import com.myhd.util.Code;
import com.myhd.util.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
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
        PageHelper.startPage(1,10);
        String key = "index:highSalary";

        List<Recruit> highSalary;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            highSalary = JSONUtil.toList(s,Recruit.class);
            System.out.println("从redis中获取");
        }else {
            highSalary = recruitMapper.getHighSalary();
            System.out.println("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(highSalary);
            stringRedisTemplate.opsForValue().set(key,jsonStr,Duration.ofMinutes(10L));
        }
        return new PageInfo<>(highSalary);
    }

    @Override
    public PageInfo<Recruit> getRecruitByCompanyId(Integer companyId,Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        String key = "company:"+companyId+":"+pageNum;

        List<Recruit> companyRecruit;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            companyRecruit = JSONUtil.toList(s,Recruit.class);
            System.out.println("从redis中获取");
        }else {
            companyRecruit = recruitMapper.getRecruitByCompanyId(companyId);
            System.out.println("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(companyRecruit);
            stringRedisTemplate.opsForValue().set(key,jsonStr,Duration.ofMinutes(10L));
        }
        return new PageInfo<>(companyRecruit);
    }

    @Override
    public Recruit getRecruitInfo(Integer companyId, Integer recruitId) {
        String key = "job:" + companyId+":"+recruitId;
        Recruit recruitInfo;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            recruitInfo = JSONUtil.toBean(s, Recruit.class);
            System.out.println("从redis中获取");
        }else {
            recruitInfo = recruitMapper.getRecruitInfo(companyId,recruitId);
            System.out.println("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(recruitInfo);
            if (jsonStr != null){
                stringRedisTemplate.opsForValue().set(key,jsonStr,Duration.ofMinutes(30L));
            }
        }
        return recruitInfo;
    }

    @Override
    public PageInfo<Recruit> getLikeInfo(String like,Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        String key = "index:"+like+":"+pageNum;
        List<Recruit> likeInfo;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            likeInfo = JSONUtil.toList(s,Recruit.class);
            System.out.println("从redis中获取");
        }else {
            likeInfo = recruitMapper.getLikeInfo(like);
            System.out.println("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(likeInfo);
            /*存储到数据库中,有效五分钟*/
            stringRedisTemplate.opsForValue().set(key,jsonStr, Duration.ofMinutes(5L));
        }
        return new PageInfo<>(likeInfo);
    }
}
