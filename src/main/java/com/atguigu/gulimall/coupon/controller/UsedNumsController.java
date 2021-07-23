package com.atguigu.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.coupon.common.PageUtils;
import com.atguigu.gulimall.coupon.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gulimall.coupon.entity.UsedNumsEntity;
import com.atguigu.gulimall.coupon.service.UsedNumsService;



/**
 * 
 *
 * @author yyxm
 * @email yyxm@sina.com
 * @date 2021-07-15 20:52:30
 */
@RestController
@RequestMapping("coupon/usednums")
public class UsedNumsController {
    @Autowired
    private UsedNumsService usedNumsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:usednums:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usedNumsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:usednums:info")
    public R info(@PathVariable("id") Integer id){
		UsedNumsEntity usedNums = usedNumsService.getById(id);

        return R.ok().put("usedNums", usedNums);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:usednums:save")
    public R save(@RequestBody UsedNumsEntity usedNums){
		usedNumsService.save(usedNums);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:usednums:update")
    public R update(@RequestBody UsedNumsEntity usedNums){
		usedNumsService.updateById(usedNums);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:usednums:delete")
    public R delete(@RequestBody Integer[] ids){
		usedNumsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
