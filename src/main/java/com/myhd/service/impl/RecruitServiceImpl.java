package com.myhd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myhd.entity.Recruit;
import com.myhd.mapper.RecruitMapper;
import com.myhd.service.IRecruitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private RecruitMapper recruitMapper;

    @Override
    public PageInfo<Recruit> getHighSalary() {
        PageHelper.startPage(1,3);
        List<Recruit> highSalary = recruitMapper.getHighSalary();
        return new PageInfo<>(highSalary);
    }
}
