package com.myhd.controller;


import com.myhd.service.IRecruitService;
import com.myhd.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 招聘职位表 前端控制器
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Resource
    private IRecruitService recruitService;

    /**
     * Description: getFields 获取Redis里面存储的相关领域
     * @return com.myhd.util.Result
     * @author jinhui-huang
     * @Date 2023/10/26
     * */
    @GetMapping("/get_fields")
    public Result getFields(){
        return recruitService.getFields();
    }

    /**
     * @description: 获取招聘职位信息页面
     * @param companyId 企业编号
     * @param recruitId 招聘编号
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/28 下午4:35
     */

    @GetMapping("getRecruitInfo/{companyId}/{recruitId}/{userId}")
    public Result getRecruitInfo(@PathVariable Integer companyId, @PathVariable Integer recruitId, @PathVariable Integer userId){
        return recruitService.acquireRecruitInfo(companyId,recruitId,userId);
    }

    /**
     * @description: 获取高薪职位列表
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/29 下午2:05
     */
    @GetMapping("getHighSalaryRecruit")
    public Result getHighSalaryRecruit(){
        return recruitService.acquireHighSalaryList();
    }


}
