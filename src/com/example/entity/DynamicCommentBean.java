package com.example.entity;
/**
 * @ClassName DynamicCommentBean.java
 * @author KevinSu kevinsu917@126.com
 * @version 创建时间：2014-10-19 上午10:15:30
 * @Description: 单条评论的实体
 */

public class DynamicCommentBean {

	private String commentUserName;//评论的用户名
	private String replyUserName;//回复的用户名,如果没有回复用户的时候,为null
	private String commentContent;//评论内容
	
	public String getCommentUserName()
	{
		return commentUserName == null ? "":commentUserName;
	}
	
	public void setCommentUserName(String value)
	{
		commentUserName = value;
	}
	
	public String getReplyUserName()
	{
		return replyUserName == null ? "":replyUserName;
	}
	
	public void setReplyUserName(String value)
	{
		replyUserName = value;
	}
	
	public String getCommentContent()
	{
		return commentContent == null ? "":commentContent;
	}
	
	public void setCommentContent(String value)
	{
		commentContent = value;
	}
}
