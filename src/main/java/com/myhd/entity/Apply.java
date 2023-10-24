package com.myhd.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 职位申请表
 * </p>
 *
 * @author Jinhui-Huang
 * @since 2023-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Component
public class Apply implements Serializable {

    private static final long serialVersionUID = -4189479609085601919L;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 招聘编号
     */
    private Integer recruitId;

    /**
     * 企业编号
     */
    private Integer companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 工作地址
     */
    private String recruitAddress;

    /**
     * 刷新时间
     */
    private Date updateTime;

    /**
     * 薪水
     */
    private Double salary;


}
