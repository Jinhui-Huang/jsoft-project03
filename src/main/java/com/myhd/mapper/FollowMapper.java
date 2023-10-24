package com.myhd.mapper;

import com.myhd.entity.Follow;

/**
 * <p>
 * 关注企业表 Mapper 接口
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
public interface FollowMapper {
    /**
     * @description 数据库插入操作
     * 用户点击关注企业后，会将信息插入关注表中
     * @author JoneElmo && CYQH
     * @date 2023-10-24 09:25
     * @param follow 关注对象
     * @return int 1插入成功 0插入失败
     */
    Integer saveFollowInfo(Follow follow);

    /**
     * @description 查询关注状态信息
     * @author JoneElmo && CYQH
     * @date 2023-10-24 09:32
     * @param userId 用户编号
     * @param companyId 公司编号
     * @return int 1成功 0失败
     */
    Follow findFollowStatus(Integer userId,Integer companyId);

    /**
     * @description 设置关注状态
     * 用户点击关注企业后，会将信息插入关注表中
     * @author JoneElmo && CYQH
     * @date 2023-10-24 09:28
     * @param status 关注状态 1已关注 0未关注
     * @return int 1成功 0失败
     */
    Integer setFollowStatus(int status);

}
