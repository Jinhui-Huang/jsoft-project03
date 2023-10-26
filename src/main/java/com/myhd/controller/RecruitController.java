package com.myhd.controller;


import com.myhd.service.IRecruitService;
import com.myhd.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
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

}
