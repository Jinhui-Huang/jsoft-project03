package com.myhd.service;

import com.myhd.util.Result;

import com.myhd.entity.Recruit;

import java.util.List;

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
     * @description 高薪职位查询
     * @author JoneElmo
     * @date 2023-10-26 09:22
     * @param
     * @return
     */
    List<Recruit> acquireHighSalaryList();

    /**
     * @description 顶部搜索框查询功能
     * @author JoneElmo
     * @date 2023-10-26 09:25
     * @param useQuickSearch 是否启用快捷查询。true表示使用快捷查询
     * @param args 查询的参数
     * @return
     */
    List<Recruit> searchRecruit(Boolean useQuickSearch,Object args);

    /**
     * @description 企业详情页面查询该企业下所有招聘岗位功能
     * 通过企业编号查询其招聘信息
     * @author JoneElmo
     * @date 2023-10-26 09:29
     * @param companyId
     * @return
     */
    List<Recruit> acquireRecruitByCompanyId(Integer companyId);

    /**
     * @description 职位详情页面展示相关信息的功能
     * @author JoneElmo
     * @date 2023-10-26 09:33
     * @param companyId 企业编号
     * @param recruitId 招聘编号
     * @return
     */
    Recruit acquireRecruitInfo(Integer companyId,Integer recruitId);
    /**
     * Description: getFields 服务层中获取redis中存放的相关领域
     * @return com.myhd.util.Result
     * @author jinhui-huang
     * @Date 2023/10/26
     * */
    Result getFields();
}
