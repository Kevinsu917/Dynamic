package com.example.view;

import com.example.dynamicdemo.R;
import com.example.entity.DynamicItemBean;
import com.example.utils.MultiUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName DynamicItemVIew.java
 * @author KevinSu kevinsu917@126.com
 * @version 创建时间：2014-10-18 下午6:23:09
 * @Description: 单条动态的View
 */

public class DynamicItemVIew extends RelativeLayout{

	private ImageView ivPhoto;//头像
	private TextView tvName;//名字
	private TextView tvContent;//内容
	private MultiImageView mivImage;//图片View
	
	private TextView tvTime;//时间
	private TextView tvDelete;//删除按钮
	private TextView tvGreet;//点赞按钮
	private TextView tvComment;//评论按钮
	
	private CommentView commentView;//评论View
	
	
	public DynamicItemVIew(Context context) {
		super(context);
		LayoutInflater.from(getContext()).inflate(R.layout.view_dyanmic_item, this);
		initView();
	}
	
	private void initView()
	{
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
		tvName = (TextView) findViewById(R.id.tvName);
		tvContent = (TextView) findViewById(R.id.tvContent);
		mivImage = (MultiImageView) findViewById(R.id.mivImage);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvDelete = (TextView) findViewById(R.id.tvDelete);
		tvGreet = (TextView) findViewById(R.id.tvGreet);
		tvComment = (TextView) findViewById(R.id.tvComment);
		commentView = (CommentView) findViewById(R.id.dcvComment);
		
	}

	public void Build(DynamicItemBean bean, int position, View.OnClickListener listener)
	{
		String userName = bean.dynamicUserName;
		int avaterRes = MultiUtils.getAvaterResource(userName);
		
		ivPhoto.setImageResource(avaterRes);
		tvName.setText(userName);
		tvContent.setText(bean.dynamicContent);
		
		mivImage.setVisibility(bean.dynamicList.size() > 0 ? View.VISIBLE : View.GONE);
		int width = mivImage.getWidth();
		mivImage.setList(bean.dynamicList, width);
		
		tvTime.setText(MultiUtils.convertTime(bean.publishTime));
		tvDelete.setVisibility(bean.dynamicUserName == MultiUtils.getCurrentUserName() ? View.VISIBLE : View.GONE);
		tvDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		boolean isGreeted = false;
		String currentUser = MultiUtils.getCurrentUserName();
		for(int i = 0; i < bean.greetInfo.greetList.size(); i ++)
		{
			if(bean.greetInfo.greetList.get(i) == currentUser)
			{
				isGreeted = true;
				break;
			}
		}
		if(isGreeted)
		{
			tvGreet.setCompoundDrawablesWithIntrinsicBounds( R.drawable.greet_pressed, 0 , 0 , 0 );
			tvGreet.setTypeface(Typeface.DEFAULT_BOLD);
			tvGreet.setTag(R.drawable.greet_pressed);
		}else 
		{
			tvGreet.setCompoundDrawablesWithIntrinsicBounds( R.drawable.greet_normal, 0 , 0 , 0 );
			tvGreet.setTypeface(Typeface.DEFAULT);
			tvGreet.setTag(R.drawable.greet_normal);
		}
		
		tvGreet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int res = (int) arg0.getTag();
				if(res == R.drawable.greet_pressed)
				{
					tvGreet.setCompoundDrawablesWithIntrinsicBounds( R.drawable.greet_normal, 0 , 0 , 0 );
					tvGreet.setTypeface(Typeface.DEFAULT);
					tvGreet.setTag(R.drawable.greet_normal);
				}else
				{
					tvGreet.setCompoundDrawablesWithIntrinsicBounds( R.drawable.greet_pressed, 0 , 0 , 0 );
					tvGreet.setTypeface(Typeface.DEFAULT_BOLD);
					tvGreet.setTag(R.drawable.greet_pressed);
				}
			}
		});
		
		commentView.init(bean, null);
		
		tvComment.setTag(position);
		tvComment.setOnClickListener(listener);
	}
}
