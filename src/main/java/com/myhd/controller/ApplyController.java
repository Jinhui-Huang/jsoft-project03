package com.myhd.controller;


import com.myhd.entity.Apply;
import com.myhd.service.impl.ApplyServiceImpl;
import com.myhd.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 职位申请表 前端控制器
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Resource
    private ApplyServiceImpl applyService;
    @PostMapping
    public Result applyRecruit(@RequestBody Apply apply){
        return applyService.insertApplyInfo(apply);
    }
}
