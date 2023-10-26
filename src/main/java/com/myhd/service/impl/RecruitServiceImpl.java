package com.myhd.service.impl;

import com.myhd.entity.Recruit;
import com.myhd.mapper.RecruitMapper;
import com.myhd.service.IRecruitService;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
