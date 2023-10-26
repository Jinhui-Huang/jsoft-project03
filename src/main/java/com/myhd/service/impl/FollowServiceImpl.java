package com.myhd.service.impl;

import com.myhd.entity.Follow;
import com.myhd.mapper.FollowMapper;
import com.myhd.service.IFollowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 关注企业表 服务实现类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@Service
public class FollowServiceImpl implements IFollowService {

    @Resource
    private FollowMapper followMapper;


    @Override
    public Boolean checkFollow(Integer userId,Integer companyId) {
        Follow follow = followMapper.findFollowStatus(userId, companyId);
        return follow != null && follow.getFollowStatus() == 1 ;
    }

    @Override
    public Boolean follow(Integer userId,Integer companyId) {
        Follow followStatus = followMapper.findFollowStatus(userId, companyId);
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setCompanyId(companyId);
        follow.setFollowStatus(1);
        if (followStatus == null){
            return followMapper.saveFollowInfo(follow) == 1;
        }else {
            return followMapper.setFollowStatus(follow) == 1;
        }
    }

    @Override
    public Boolean unfollow(Integer userId,Integer companyId) {
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setCompanyId(companyId);
        follow.setFollowStatus(0);
        return followMapper.setFollowStatus(follow) == 1;
    }
}
