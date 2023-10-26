package com.myhd.service;

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
}
