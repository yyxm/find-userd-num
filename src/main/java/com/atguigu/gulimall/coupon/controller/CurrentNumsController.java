package com.atguigu.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.coupon.common.PageUtils;
import com.atguigu.gulimall.coupon.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.coupon.entity.CurrentNumsEntity;
import com.atguigu.gulimall.coupon.service.CurrentNumsService;




/**
 * 
 *
 * @author yyxm
 * @email yyxm@sina.com
 * @date 2021-07-15 20:52:30
 */
@RestController
@RequestMapping("coupon/currentnums")
public class CurrentNumsController {
    @Autowired
    private CurrentNumsService currentNumsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:currentnums:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = currentNumsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:currentnums:info")
    public R info(@PathVariable("id") Integer id){
		CurrentNumsEntity currentNums = currentNumsService.getById(id);

        return R.ok().put("currentNums", currentNums);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:currentnums:save")
    public R save(@RequestBody CurrentNumsEntity currentNums){
		currentNumsService.save(currentNums);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:currentnums:update")
    public R update(@RequestBody CurrentNumsEntity currentNums){
		currentNumsService.updateById(currentNums);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:currentnums:delete")
    public R delete(@RequestBody Integer[] ids){
		currentNumsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 校验
     * @return
     */
    @GetMapping("/check")
    public R check(){
        currentNumsService.check();
        return R.ok();
    }

}
