package com.myhd.service;

import com.github.pagehelper.PageInfo;
import com.myhd.entity.Recruit;

import java.util.List;

import com.myhd.util.Result;

/**
 * <p>
 * 招聘职位表 服务类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
public interface IRecruitService {

    /**
     * Description: getFields 服务层中获取redis中存放的相关领域
     * @return com.myhd.util.Result
     * @author jinhui-huang
     * @Date 2023/10/26
     * */
    Result getFields();
    /**
     * @description 高薪职位查询
     * 需要同企业表连接，获取公司logo
     * 根据薪资max值降序排列
     * 使用mybatis resultmap来映射这个图标字段
     * @author JoneElmo && CYQH
     * @date 2023-10-24 10:53
     * @return Recruit
     */
    PageInfo<Recruit> getHighSalary();

}
