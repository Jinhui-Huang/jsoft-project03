package com.myhd.service.impl;

import com.myhd.entity.Follow;
import com.myhd.mapper.FollowMapper;
import com.myhd.service.IFollowService;
import com.myhd.util.Code;
import com.myhd.util.Result;
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

    /**
     * @description: 根据用户编号和企业编号判断该企业有没有被该用户关注
     * @param userId 用户编号
     * @param companyId 企业编号
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/26 下午3:08
     */
    @Override
    public Result checkFollow(Integer userId, Integer companyId) {
        Follow follow = followMapper.findFollowStatus(userId, companyId);
        if (follow != null && follow.getFollowStatus() == 1){
            return Result.ok("该企业已关注");
        }else {
            return Result.fail("未关注该企业");
        }
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
