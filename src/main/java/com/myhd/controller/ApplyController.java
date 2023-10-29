package com.myhd.controller;


import com.myhd.entity.Apply;
import com.myhd.service.impl.ApplyServiceImpl;
import com.myhd.util.Result;
import org.springframework.web.bind.annotation.*;

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
    /**
     * @description: 进行职位申请
     * @param apply 申请实体类
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/29 下午2:55
     */
    @PostMapping
    public Result applyRecruit(@RequestBody Apply apply){
        return applyService.insertApplyInfo(apply);
    }

    /**
     * @description: 个人申请职位的查询以及模糊查询和分页
     * @param userId 用户编号
     * @param like 模糊查询信息
     * @param pageNum   页码
     * @return: com.myhd.util.Result
     * @author CYQH
     * @date: 2023/10/29 下午3:30
     */
    @GetMapping("getAllUserApply/{like}/{pageNum}/{userId}")
    public Result getCompanyInfo(@PathVariable Integer userId, @PathVariable String like, @PathVariable Integer pageNum){
        System.out.println(like == null);
        return applyService.getAllUserApply(userId,like,pageNum);
    }
}
