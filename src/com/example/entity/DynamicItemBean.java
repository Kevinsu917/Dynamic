package com.example.entity;

import java.util.ArrayList;

/**
 * @ClassName DynamicItemBean.java
 * @author KevinSu kevinsu917@126.com
 * @version 创建时间：2014-10-18 下午6:21:50
 * @Description: 单条动态的数据实体
 */

public class DynamicItemBean {

	public String dynamicUserName;//动态发布者的名字
	public String dynamicContent;//动态发布的内容
	public ArrayList<Integer> dynamicList = new ArrayList<>();//动态发布图片list
	public long publishTime;//动态发布的时间
	public DynamicGreetInfo greetInfo = new DynamicGreetInfo();//动态点赞的信息
	public DynamicReviewInfo reviewInfo = new DynamicReviewInfo();//动态评论的信息
	
}
