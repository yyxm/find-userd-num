package com.atguigu.gulimall.coupon.service.impl;

import com.atguigu.gulimall.coupon.common.PageUtils;
import com.atguigu.gulimall.coupon.common.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.atguigu.gulimall.coupon.dao.UsedNumsDao;
import com.atguigu.gulimall.coupon.entity.UsedNumsEntity;
import com.atguigu.gulimall.coupon.service.UsedNumsService;


@Service("usedNumsService")
public class UsedNumsServiceImpl extends ServiceImpl<UsedNumsDao, UsedNumsEntity> implements UsedNumsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsedNumsEntity> page = this.page(
                new Query<UsedNumsEntity>().getPage(params),
                new QueryWrapper<UsedNumsEntity>()
        );

        return new PageUtils(page);
    }

}