package com.myhd.controller;


import com.myhd.service.impl.CompanyServiceImpl;
import com.myhd.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 企业表 前端控制器
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Resource
    private CompanyServiceImpl companyService;

    /**
     * @description: 获取企业的详细信息
     * @param companyId 企业编号
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/28 下午4:30
     */
    @GetMapping("getCompanyInfo/{companyId}")
    public Result getPhoneOrEmail(@PathVariable Integer companyId){
        return companyService.getCompanyInfo(companyId);
    }
}
