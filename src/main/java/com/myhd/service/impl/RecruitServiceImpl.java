package com.myhd.service.impl;

import com.myhd.dto.Filed;
import com.myhd.entity.Recruit;
import com.myhd.mapper.RecruitMapper;
import com.myhd.service.IRecruitService;
import com.myhd.util.Code;
import com.myhd.util.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

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
}
