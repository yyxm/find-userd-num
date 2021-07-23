package com.atguigu.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author yyxm
 * @email yyxm@sina.com
 * @date 2021-07-15 20:52:30
 */
@Data
@TableName("current_nums")
public class CurrentNumsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String pccode;
	/**
	 * 
	 */
	private String numBegin;
	/**
	 * 
	 */
	private String numEnd;

}
