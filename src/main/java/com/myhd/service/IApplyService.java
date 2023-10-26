package com.myhd.service;

import com.myhd.entity.Apply;
import com.myhd.entity.Recruit;

import java.util.List;

/**
 * <p>
 * 职位申请表 服务类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
public interface IApplyService {

    /**
     * @description 点击立即申请按钮，发送信息
     * @author JoneElmo && CYQH
     * @date 2023-10-24 10:25
     * @param apply 申请的数据对象
     * @return Integer 1 成功 0 失败
     */
    Boolean insertApplyInfo(Apply apply);

    /**
     * @description 我的职位页面，显示所有个人已经申请的职位 功能
     * select a.*,r.* from tb_apply a
     * join tb_recruit r
     * using(recruit_id,company_id)
     * where user_id = #{userId}
     * @author JoneElmo && CYQH
     * @date 2023-10-24 11:05
     * @param userId 当前用户编号
     * @return
     */
    List<Recruit> getAllUserApply(Integer userId, String like);

}
