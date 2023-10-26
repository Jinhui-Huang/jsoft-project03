package com.myhd.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myhd.dto.Filed;
import com.myhd.entity.Recruit;
import com.myhd.exception.BusinessException;
import com.myhd.mapper.RecruitMapper;
import com.myhd.service.IRecruitService;
import lombok.val;
import com.myhd.util.Code;
import com.myhd.util.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 招聘职位表 服务实现类
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@Service
public class RecruitServiceImpl implements IRecruitService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Description: getFields 服务层中获取redis中存放的相关领域
     * 101~104: 销售 | 市场 | 客服 | 贸易
     * 201~204: 经验管理 | 人事 | 行政 | 财务
     * 301~304: 生产 | 质管 | 技工 | 物流
     * 401~404: 教育 | 法律 | 咨询 | 翻译
     * 501~504: 零售 | 家政 | 餐饮 | 旅游
     * 601~604: 广告 | 媒体 | 艺术 | 出版
     * 701~703: IT | 互联网 | 通信
     * @return com.myhd.util.Result
     * @author jinhui-huang
     * @Date 2023/10/26
     */
    @Override
    public Result getFields() {
        String key = "job:cache:filed";
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        List<Filed> list = new ArrayList<>();
        entries.forEach((o, o2) -> {
            int jobFiled = Integer.parseInt(String.valueOf(o));
            String filedName = String.valueOf(o2);
            list.add(new Filed(jobFiled, filedName));
        });
        return new Result(Code.GET_OK, list, "全领域展示在前端");
    }
    @Resource
    RecruitMapper recruitMapper;

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:55
     * @param
     * @return List<Recruit>
     */
    @Override
    public Result acquireHighSalaryList() {
        List<Recruit> highSalary = recruitMapper.getHighSalary();
        if (highSalary!=null){
            String jsonStr = JSONUtil.toJsonStr(highSalary);
            String key = "index:highSalary";
            stringRedisTemplate.opsForValue().set(key,jsonStr);
            return new Result(Code.GET_OK,highSalary, "获取高薪职位列表成功");
        }else {
            return new Result(Code.FAIL, null, "获取高薪职位列表失败");
        }
    }

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:38
     * @param useQuickSearch true:启用快捷查询
    args
     * @return List<Recruit>
     */
    @Override
    public Result searchRecruit(Boolean useQuickSearch, Object args,Integer...pageNum) {
        List<Recruit> result;
        PageInfo recruitPageInfo;
        if (useQuickSearch){
            if (pageNum.length>0){
                throw new BusinessException(Code.SYSTEM_ERR, "已启用快捷查询，不允许传入可变参数！");
            }
            result = recruitMapper.getInfoViaQuickMethod((Integer) args);
            return new Result(Code.GET_OK, result, "查询成功");
        }else {
            if (pageNum.length>1){
                throw new BusinessException(Code.SYSTEM_ERR, "可变参数至多为1个！");
            }
            /* 模糊查询 */
            result = recruitMapper.getLikeInfo((String) args);
            Integer pageSize = 10;
            String key = "index:"+args+":"+pageNum[0];
            String jsonStr = JSONUtil.toJsonStr(result);
            stringRedisTemplate.opsForValue().set(key, jsonStr, Duration.ofMinutes(10));
            PageHelper.startPage(pageNum[0], pageSize);
            recruitPageInfo = new PageInfo(result);
            return new Result(Code.OK, recruitPageInfo, "查询成功");
        }
    }

    /**
     * @description
     * @author JoneElmo
     * @date 2023-10-26 09:55
     * @param companyId
     * @return List<Recruit>
     */
    @Override
    public Result acquireRecruitByCompanyId(Integer companyId) {
        List<Recruit> list = recruitMapper.getRecruitByCompanyId(companyId);
        if (list.size()>0){
            return new Result(Code.OK,list,"获取成功") ;
        }else {
            return new Result(Code.FAIL,null,"获取失败，数据不存在") ;
        }

    }

    /**
     * @description 职位信息页面 展示相关信息功能
     * @author JoneElmo
     * @date 2023-10-26 09:56
     * @param companyId
    recruitId
     * @return Recruit
     */
    @Override
    public Result acquireRecruitInfo(Integer companyId, Integer recruitId) {
        Recruit recruit = recruitMapper.getRecruitInfo(companyId, recruitId);
        if (recruit != null) {
            String key = "job:" + companyId + ":" + recruitId;
            val jsonStr = JSONUtil.toJsonStr(recruit);
            stringRedisTemplate.opsForValue().set(key, jsonStr, Duration.ofMinutes(10));
            return new Result(Code.OK, recruit, "获取成功");
        }else {
            return new Result(Code.FAIL, null, "获取失败，数据不存在");
        }
    }
}
