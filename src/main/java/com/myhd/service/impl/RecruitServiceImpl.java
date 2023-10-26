package com.myhd.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myhd.dto.Filed;
import com.myhd.entity.Recruit;
import com.myhd.exception.BusinessException;
import com.myhd.mapper.RecruitMapper;
import com.myhd.service.IRecruitService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import com.myhd.util.Code;
import com.myhd.util.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

import java.util.ArrayList;
import java.util.Comparator;
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
@Slf4j
public class RecruitServiceImpl implements IRecruitService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RecruitMapper recruitMapper;

    /**
     * Description: getFields 服务层中获取redis中存放的相关领域
     * <br></br>
     * 101~104: 销售 | 市场 | 客服 | 贸易
     * <br></br>
     * 201~204: 经验管理 | 人事 | 行政 | 财务
     * <br></br>
     * 301~304: 生产 | 质管 | 技工 | 物流
     * <br></br>
     * 401~404: 教育 | 法律 | 咨询 | 翻译
     * <br></br>
     * 501~504: 零售 | 家政 | 餐饮 | 旅游
     * <br></br>
     * 601~604: 广告 | 媒体 | 艺术 | 出版
     * <br></br>
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
        entries.forEach((o1, o2) -> {
            list.add(new Filed(Integer.parseInt(String.valueOf(o1)), String.valueOf(o2)));
        });
        list.sort(Comparator.comparingInt(Filed::getJobFiled));

        List<List<Filed>> resultList = new ArrayList<>();
        List<Filed> tempList = new ArrayList<>();
        int num1 = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                resultList.add(tempList);
            }
            if (num1 == 4) {
                resultList.add(tempList);
                tempList = new ArrayList<>();
                num1 = 0;
                i--;
            } else {
                tempList.add(list.get(i));
                num1++;
            }
        }
        return new Result(Code.GET_OK, resultList, "全职位领域展示在前端");
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
     * @description 高薪职位查询
     * 需要同企业表连接，获取公司logo
     * 根据薪资max值降序排列
     * 使用mybatis resultmap来映射这个图标字段
     * @author JoneElmo && CYQH
     * @date 2023-10-24 10:53
     * @return Recruit
     */
    @Override
    public Result acquireHighSalaryList() {
        PageHelper.startPage(1,10);
        String key = "index:highSalary";
        List<Recruit> highSalary;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            highSalary = JSONUtil.toList(s,Recruit.class);
            log.info("从redis中获取");
        }else {
            highSalary = recruitMapper.getHighSalary();
            log.info("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(highSalary);
            stringRedisTemplate.opsForValue().set(key,jsonStr,Duration.ofMinutes(10L));
        }
        PageInfo pageInfo = new PageInfo<>(highSalary);
        return new  Result(Code.OK,pageInfo,"高薪职位查询成功");
    }

    /**
     * @description
     * 企业详情页面查询该企业下所有招聘岗位功能
     * 需要分页
     * 根据企业ID查询招聘信息
     * 通过和申请表外连接来查询 判断是否被申请 （通过user_id来判断）
     * @author JoneElmo && CYQH
     * @date 2023-10-24 10:07
     * @param companyId 企业编号
     * @return RECRUIT 招聘信息
     */
    @Override
    public Result acquireRecruitByCompanyId(Integer companyId,Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        String key = "company:"+companyId+":"+pageNum;

        List<Recruit> companyRecruit;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            companyRecruit = JSONUtil.toList(s,Recruit.class);
            log.info("从redis中获取");
        }else {
            companyRecruit = recruitMapper.getRecruitByCompanyId(companyId);
            log.info("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(companyRecruit);
            stringRedisTemplate.opsForValue().set(key,jsonStr,Duration.ofMinutes(10L));
        }
        PageInfo pageInfo = new PageInfo<>(companyRecruit);
        return new Result(Code.GET_OK,pageInfo,"企业信息获取成功");
    }

    /**
     * @description 职位信息页面 展示相关信息功能
     * 通过和申请表外连接判断是否已经被申请 （通过user_id来判断）
     * 如果user_id为空 则表示该岗位没有被申请 否则表示岗位被申请
     * 示例：
     * select r.*,a.* from tb_recruit r
     * left join tb_apply a
     * on r.id = a.recruit_id
     * @author JoneElmo && CYQH
     * @date 2023-10-24 10:27
     * @param
     * @return
     */
    @Override
    public Result acquireRecruitInfo(Integer companyId, Integer recruitId) {
        String key = "job:" + companyId+":"+recruitId;
        Recruit recruitInfo;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            recruitInfo = JSONUtil.toBean(s, Recruit.class);
            log.info("从redis中获取");
        }else {
            recruitInfo = recruitMapper.getRecruitInfo(companyId,recruitId);
            log.info("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(recruitInfo);
            if (jsonStr != null){
                stringRedisTemplate.opsForValue().set(key,jsonStr,Duration.ofMinutes(30L));
            }
        }
        if(recruitInfo != null){
            return new Result(Code.GET_OK,recruitInfo,"获取职位信息成功");
        }else {
            return new Result(Code.GET_FAIL, null,"获取职位信息失败");
        }
    }

    /**
     * @description 模糊查询
     * 查询所有岗位信息  需要分页 pageInfo
     * 和申请表外连接 判断是否被申请
     * @author JoneElmo && CYQH
     * @date 2023-10-24 10:39
     * @param like 模糊查询的参数  招聘的职位关键字(recruit_name)
     * @return
     */
    @Override
    public Result getLikeInfo(String like,Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        String key = "index:"+like+":"+pageNum;
        List<Recruit> likeInfo;
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s != null){
            likeInfo = JSONUtil.toList(s,Recruit.class);
            log.info("从redis中获取");
        }else {
            likeInfo = recruitMapper.getLikeInfo(like);
            log.info("从数据库获取");
            String jsonStr = JSONUtil.toJsonStr(likeInfo);
            /*存储到数据库中,有效五分钟*/
            stringRedisTemplate.opsForValue().set(key,jsonStr, Duration.ofMinutes(5L));
        }
        PageInfo pageInfo = new PageInfo<>(likeInfo);
        return new Result(Code.GET_OK,pageInfo,"获取数据成功");
    }
}
