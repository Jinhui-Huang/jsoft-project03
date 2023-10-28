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

    /*require('@/../dist/static/images/20.PNG' )
    import axios from 'axios';
export default {
    data() {
        return {
            companyIcon: "",//企业图标
            companyName: "",//企业名称
            companyType: "",//企业性质
            companyField: "",//企业行业
            companyScale: "",//企业规模
            companyHome: "",//企业主页
            companyAddress: "",//企业地址
            companyText: "",//企业介绍

            recruitName: "",//职位名称
            recruitTag: "",//招聘标签
            recruitAddress: "",//职位地址
            salary: "",//薪水
            recruitNumber: "",//招聘人数
            recruitDegree: "",//招聘学历
            recruitAge: "",//招聘年龄
            recruitExp: "",//工作经验
            recruitType: "",//招聘方式
            recruitTime: "",//更新时间
            recruitTextDuty: "",//岗位职责
            recruitTextNeed: ""//岗位要求
        }
    },
    methods: {

    },
    mounted() {
        let that = this;
        axios({
            method: 'GET',
            url: '/api/company/getCompanyInfo/200001' //你的后端路径
        })
            .then(response => {
                let data = response.data
                let code = data.code
                let msg = data.msg
                let info = data.object
                if (code === 200001) { //判断你的请求是否成功
                    console.log(data)
                    // that.companyIcon = require('@/../dist/static/images/' + info.companyIcon)
                    that.companyName = info.companyName
                    that.companyType = info.companyType
                    that.companyField = info.companyField
                    that.companyScale = info.companyScale
                    that.companyHome = info.companyHome
                    that.companyAddress = info.companyAddress
                    that.companyText = info.companyText
                } else {
                    alert(msg)
                }
            }, error => {
                console.log('错误', error.message)
                // alert(error.message)
            })
        axios({
            method: 'GET',
            url: '/api/recruit/getRecruitInfo/200001/300001/100001' //你的后端路径
        })
            .then(response => {
                let data = response.data
                let code = data.code
                let msg = data.msg
                let info = data.object
                if (code === 200001) { //判断你的请求是否成功
                    console.log(data)
                    that.recruitName = info.recruitName
                    that.recruitTag = info.recruitTag
                    that.recruitAddress = info.recruitAddress
                    that.salary= info.recruitSalaryMin+"K"+info.recruitSalaryMax+"K[参考工资]"
                    that.recruitNumber = info.recruitNumber
                    that.recruitDegree = info.recruitDegree
                    that.recruitAge = info.recruitAge
                    that.recruitExp = info.recruitExp
                    that.recruitType= info.recruitType
                    that.recruitTime= info.recruitTime
                    that.recruitTextDuty= info.recruitTextDuty
                    that.recruitTextNeed= info.recruitTextNeed
                } else {
                    alert(msg)
                }
            }, error => {
                console.log('错误', error.message)
                // alert(error.message)
            })

    },
}
</script>*/
    @GetMapping("getRecruitInfo/{companyId}/{recruitId}/{userId}")
    public Result getRecruitInfo(@PathVariable Integer companyId, @PathVariable Integer recruitId, @PathVariable Integer userId){
        return recruitService.acquireRecruitInfo(companyId,recruitId,userId);
    }

}
