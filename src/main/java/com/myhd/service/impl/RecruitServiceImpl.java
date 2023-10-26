package com.myhd.service.impl;

import com.myhd.entity.Recruit;
import com.myhd.mapper.RecruitMapper;
import com.myhd.service.IRecruitService;
import lombok.val;
import com.myhd.util.Code;
import com.myhd.util.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    RecruitMapper recruitMapper;

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:55
     * @param
     * @return List<Recruit>
     */
    @Override
    public List<Recruit> acquireHighSalaryList() {

        return recruitMapper.getHighSalary();
    }

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:38
     * @param useQuickSearch true:启用快捷查询
    args
     * @return List<Recruit>
     */
    @Override
    public List<Recruit> searchRecruit(Boolean useQuickSearch, Object args) {
        List<Recruit> result;
        if (useQuickSearch){
            result = recruitMapper.getInfoViaQuickMethod((Integer) args);
        }else {
            result = recruitMapper.getLikeInfo((String) args);
        }
        return result;
    }

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:55
     * @param companyId
     * @return List<Recruit>
     */
    @Override
    public List<Recruit> acquireRecruitByCompanyId(Integer companyId) {
        return recruitMapper.getRecruitByCompanyId(companyId);
    }

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:56
     * @param companyId
    recruitId
     * @return Recruit
     */
    @Override
    public Recruit acquireRecruitInfo(Integer companyId, Integer recruitId) {
        return recruitMapper.getRecruitInfo(companyId, recruitId);
    }
}
