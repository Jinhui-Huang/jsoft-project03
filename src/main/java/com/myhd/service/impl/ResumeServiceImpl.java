package com.myhd.service.impl;

import com.myhd.entity.Resume;
import com.myhd.mapper.ResumeMapper;
import com.myhd.service.IResumeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 简历表 服务实现类
 * </p>
 *
 * @author JoneElmo
 * @since 2023-10-23
 */
@Service
public class ResumeServiceImpl implements IResumeService {

    @Resource
    ResumeMapper resumeMapper;

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:52
     * @param userId
     * @return Resume
     */
    @Override
    public Resume acquireResumeInfo(Integer userId) {
        return resumeMapper.getResumeByUserId(userId);
    }

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:53
     * @param resume
     * @return Boolean
     */
    @Override
    public Boolean saveResumeInfo(Resume resume) {
        return resumeMapper.insertResume(resume) ==1 ?true:false;
    }
}
