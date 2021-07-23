package com.atguigu.gulimall.coupon.service;

import com.atguigu.gulimall.coupon.common.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.coupon.entity.CurrentNumsEntity;

import java.util.Map;

/**
 * 
 *
 * @author yyxm
 * @email yyxm@sina.com
 * @date 2021-07-15 20:52:30
 */
public interface CurrentNumsService extends IService<CurrentNumsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void check();
}

