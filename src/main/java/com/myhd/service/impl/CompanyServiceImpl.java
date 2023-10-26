package com.myhd.service.impl;

import cn.hutool.json.JSONUtil;
import com.myhd.entity.Company;
import com.myhd.mapper.CompanyMapper;
import com.myhd.service.ICompanyService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Key;
import java.time.Duration;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@Service
public class CompanyServiceImpl implements ICompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Company getCompanyInfo(Integer companyId) {
        String key = "company:" + companyId;
        Company companyInfo = new Company();
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            companyInfo = JSONUtil.toBean(s, Company.class);
            System.out.println("从redis中获取");
        }else {
            companyInfo = companyMapper.findById(companyId);
            System.out.println("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(companyInfo);
            stringRedisTemplate.opsForValue().set(key,jsonStr,Duration.ofMinutes(30L));
        }
        return companyInfo;
    }
}
