package com.myhd.service.impl;

import com.myhd.entity.Apply;
import com.myhd.entity.Recruit;
import com.myhd.mapper.ApplyMapper;
import com.myhd.service.IApplyService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Recruit> getAllUserApply(Integer userId, String like) {
        return applyMapper.getAllUserApply(userId,like);
    }
}
