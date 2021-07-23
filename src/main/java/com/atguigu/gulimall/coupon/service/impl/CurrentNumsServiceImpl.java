package com.atguigu.gulimall.coupon.service.impl;

import com.atguigu.gulimall.coupon.common.PageUtils;
import com.atguigu.gulimall.coupon.common.Query;
import com.atguigu.gulimall.coupon.entity.CurrentNumsVo;
import com.atguigu.gulimall.coupon.entity.UsedNumsEntity;
import com.atguigu.gulimall.coupon.service.UsedNumsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.atguigu.gulimall.coupon.dao.CurrentNumsDao;
import com.atguigu.gulimall.coupon.entity.CurrentNumsEntity;
import com.atguigu.gulimall.coupon.service.CurrentNumsService;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("currentNumsService")
public class CurrentNumsServiceImpl extends ServiceImpl<CurrentNumsDao, CurrentNumsEntity> implements CurrentNumsService {

    @Autowired
    private UsedNumsService usedNumsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CurrentNumsEntity> page = this.page(
                new Query<CurrentNumsEntity>().getPage(params),
                new QueryWrapper<CurrentNumsEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void check() {
        // 模板
        List<CurrentNumsEntity> currentNumsEntities = this.list();
        List<CurrentNumsVo> currentNumsVos = new ArrayList<>();
        //已使用
        List<UsedNumsEntity> usedNumsEntities = usedNumsService.list();

        //
        for (CurrentNumsEntity currentNumsEntity : currentNumsEntities) {
            CurrentNumsVo currentNumsVo = new CurrentNumsVo();
            BeanUtils.copyProperties(currentNumsEntity,currentNumsVo);
            currentNumsVo.setNumBegin(Integer.valueOf(currentNumsEntity.getNumBegin()));
            currentNumsVo.setNumEnd(Integer.valueOf(currentNumsEntity.getNumEnd()));
            currentNumsVos.add(currentNumsVo);
        }

        List<CurrentNumsEntity> result = currentNumsEntities;
        
        //遍历模板和已使用
        for (int i = 0; i < currentNumsVos.size(); i++) {

            //进行判断 是否使用过当前元素的范围
            for (int i1 = 0; i1 < usedNumsEntities.size(); i1++) {
                //当前模板元素
                CurrentNumsVo currentNumsEntity = currentNumsVos.get(i);
                //当前使用元素
                UsedNumsEntity usedNumsEntity = usedNumsEntities.get(i1);

//                if (usedNumsEntity.getNumEnd()==7001 && usedNumsEntity.getNumBegin() ==7001){
//                    System.out.println("来来");
//                }
                //判断
                if (currentNumsEntity.getPccode().equalsIgnoreCase(usedNumsEntity.getPccode()) &&
                        currentNumsEntity.getNumBegin() <= usedNumsEntity.getNumBegin() &&
                        currentNumsEntity.getNumEnd() >= usedNumsEntity.getNumEnd()){
                    //如果进入这个判断 说明当前使用的元素属于这个模板之间

                    //使用的范围起止和模板的起止一样 就删除当前模板
                    if (currentNumsEntity.getNumBegin().equals(usedNumsEntity.getNumBegin()) &&
                            currentNumsEntity.getNumEnd().equals(usedNumsEntity.getNumEnd())){
                        currentNumsVos.remove(i);
                        //result.remove(i);

                    }
                    //如果使用范围起止是一样的
                    else if(usedNumsEntity.getNumBegin().equals(usedNumsEntity.getNumEnd())){
                        //判断
                        //如果头一样
                        if (usedNumsEntity.getNumBegin().equals(currentNumsEntity.getNumBegin())){
                            CurrentNumsVo entity = new CurrentNumsVo();
                            entity.setPccode(usedNumsEntity.getPccode());
                            entity.setNumEnd(currentNumsEntity.getNumEnd());
                            entity.setNumBegin(usedNumsEntity.getNumEnd()+1);
                            currentNumsVos.remove(i);
                            currentNumsVos.add(i,entity);
                        }//如果尾一样
                        else if (usedNumsEntity.getNumEnd().equals(currentNumsEntity.getNumEnd())){
                            CurrentNumsVo entity = new CurrentNumsVo();
                            entity.setPccode(usedNumsEntity.getPccode());
                            entity.setNumBegin(currentNumsEntity.getNumBegin());
                            entity.setNumEnd(usedNumsEntity.getNumBegin()-1);
                            currentNumsVos.remove(i);
                            currentNumsVos.add(i,entity);

                        }
                        else{
                            CurrentNumsVo numsEntity = new CurrentNumsVo();
                            numsEntity.setNumBegin(usedNumsEntity.getNumBegin()+1);
                            numsEntity.setNumEnd(currentNumsEntity.getNumEnd());
                            numsEntity.setPccode(usedNumsEntity.getPccode());
                            currentNumsVos.add(i+1,numsEntity);
                            currentNumsEntity.setNumEnd(usedNumsEntity.getNumBegin()-1);
                            currentNumsVos.set(i,currentNumsEntity);
                        }
                    }
                    //如果使用的范围 起始 == 模板范围的起始
                    else if (currentNumsEntity.getNumBegin().equals(usedNumsEntity.getNumBegin())){
                        //将模板范围的起始 设置成使用范围的结束
                        currentNumsEntity.setNumBegin(usedNumsEntity.getNumEnd()+1);
                        currentNumsVos.set(i,currentNumsEntity);
                        //result.set(i,currentNumsEntity);
                    }
                    //如果使用的范围 的结尾 == 模板的结尾 就修改模板的结尾 为使用范围的起始
                    else if (currentNumsEntity.getNumEnd().equals(usedNumsEntity.getNumEnd())){
                        currentNumsEntity.setNumEnd(usedNumsEntity.getNumBegin()-1);
                        currentNumsVos.set(i,currentNumsEntity);
                        //result.set(i,currentNumsEntity);

                    }
                    else{
                        CurrentNumsVo entity = new CurrentNumsVo();
                        entity.setPccode(usedNumsEntity.getPccode());
                        entity.setNumBegin(usedNumsEntity.getNumEnd()+1);
                        entity.setNumEnd(currentNumsEntity.getNumEnd());

                        currentNumsEntity.setNumEnd(usedNumsEntity.getNumBegin()-1);
                        currentNumsVos.set(i,currentNumsEntity);
                        //result.set(i,currentNumsEntity);
                        currentNumsVos.add(i+1,entity);
                        //result.add(entity);
                    }
                    //usedNumsEntities.remove(i1);
                }
            }
        }

        //System.out.println(currentNumsEntities);
        List<CurrentNumsEntity> list = new ArrayList<>();
        //整理
        for (CurrentNumsVo currentNumsEntity : currentNumsVos) {
            currentNumsEntity.setId(null);
            CurrentNumsEntity numsEntity = new CurrentNumsEntity();
            BeanUtils.copyProperties(currentNumsEntity,numsEntity);
            //对数字做处理
            String newBegin = toCase(currentNumsEntity.getNumBegin());
            String newEnd = toCase(currentNumsEntity.getNumEnd());
            numsEntity.setNumBegin(newBegin);
            numsEntity.setNumEnd(newEnd);
            list.add(numsEntity);
            log.info(newBegin,newEnd);
        }
        log.info("整理完的数据:{}",list);
        //删除原有的
        this.remove(new QueryWrapper<>());
        //添加
        this.saveBatch(list);

    }


    public String toCase(Integer value){
        String num = value.toString();
        //数字长度
        String newNum = null;
        int length = num.length();
        int max = 10;
        if (length<max){

            //填充长度
            //所相差的长度
            int size = max-length;
            switch (size){
                case 9 : newNum="000000000"+num; break;
                case 8 : newNum="00000000"+num; break;
                case 7 : newNum="0000000"+num; break;
                case 6 : newNum="000000"+num; break;
                case 5 : newNum="00000"+num; break;
                case 4 : newNum="0000"+num; break;
                case 3 : newNum="000"+num; break;
                case 2 : newNum="00"+num; break;
                default: newNum="0"+num; break;
            }
        }
        return newNum;
    }


}