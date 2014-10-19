package com.example.view;

import com.example.dynamicdemo.R;
import com.example.entity.DynamicGreetInfo;
import com.example.entity.DynamicItemBean;
import com.example.entity.DynamicReviewInfo;
import com.example.utils.MultiUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

/**
 * @ClassName CommentView.java
 * @author KevinSu kevinsu917@126.com
 * @version 创建时间：2014-10-19 上午10:06:28
 * @Description: 评论和点赞的View
 */

public class CommentView extends LinearLayout {

	/** 长度 Pixel为单位 **/
	private final int pxPerPhoto = MultiUtils.dipToPx(getContext(), 21);// 点赞头像的尺寸
	private final int pxPhotoPadding = MultiUtils.dipToPx(getContext(), 5);// 点赞头像间隔

	// 赞头像，每行最大个数
	private final int MAX_COUNT_PER_ROW = 9;

	private LayoutParams onePicPara;
	private LinearLayout.LayoutParams rowPara;
	private int desResID = R.drawable.avater_boy_1;
	
	private LinearLayout llGreetPhoto;// 点赞头像的Layout
	private TextView tvGreetTitle;//点赞数量
	private LinearLayout llComment;// 评论的Layout
	
	private View.OnClickListener commentOnClickListener;//评论内容的点击事件
	private View.OnClickListener userPicOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
		}
	};
	

	public CommentView(Context context) {
		super(context);
		LayoutInflater.from(getContext()).inflate(R.layout.view_comment_bar, this);
	}

	public CommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(getContext()).inflate(R.layout.view_comment_bar, this);
	}

	public void init(DynamicItemBean bean, View.OnClickListener itemClickListener) {
		initView();
		initVariable();
		setGreetLayout(bean.greetInfo);
		setCommentLayout(bean.reviewInfo);
	}

	private void initView()
	{
		llGreetPhoto = (LinearLayout) findViewById(R.id.llGreetPhoto);
		tvGreetTitle = (TextView) findViewById(R.id.tvGreetTitle);
		llComment = (LinearLayout) findViewById(R.id.llComment);
	}
	
	private void initVariable() {
		onePicPara = new LayoutParams(pxPerPhoto, pxPerPhoto);
		onePicPara.setMargins(0, 0, pxPhotoPadding, 0);

		int wrap = LayoutParams.WRAP_CONTENT;
		int match = LayoutParams.MATCH_PARENT;
		rowPara = new LayoutParams(match, wrap);
		rowPara.setMargins(0, 0, 0, pxPhotoPadding);
	}

	/**
	 * 设置赞的布局，包括数字和头像
	 */
	public void setGreetLayout(DynamicGreetInfo greetInfo) {
		
		//llGreetPhoto原本有两个View，所以删除要从第三个view开始删
		llGreetPhoto.removeViews(2, llGreetPhoto.getChildCount() - 2);

		int PicCount = greetInfo.greetList.size();
		String str = PicCount + "个赞";
		tvGreetTitle.setText(str);

		if (PicCount <= 0) {
			return;
		}

		// 对第一行的头像插入	
		int rowCount = PicCount / MAX_COUNT_PER_ROW
				+ (PicCount % MAX_COUNT_PER_ROW > 0 ? 1 : 0);

		for (int row = 0; row < rowCount; row++) {
			LinearLayout llRow = new LinearLayout(getContext());
			llRow.setLayoutParams(rowPara);
			llGreetPhoto.addView(llRow);

			int columnCount = PicCount%MAX_COUNT_PER_ROW == 0 ? MAX_COUNT_PER_ROW : PicCount%MAX_COUNT_PER_ROW;
			if(row != rowCount -1)
			{
				columnCount = MAX_COUNT_PER_ROW;
			}
			int rowOffset = row * MAX_COUNT_PER_ROW;//行偏移
			for (int column = 0; column < columnCount; column++) {
				
				int picIndex = rowOffset + column;
				String name = greetInfo.greetList.get(picIndex);
				int avaterRes = MultiUtils.getAvaterResource(name);
				
				ImageView ivPic = new ImageView(getContext());
				ivPic.setScaleType(ScaleType.CENTER_CROP);
				ivPic.setLayoutParams(onePicPara);
				ivPic.setImageResource(avaterRes);
				
				ivPic.setOnClickListener(userPicOnClickListener);
				
				llRow.addView(ivPic);
			}
		}
	}

	/**
	 * 设置评论的布局
	 */
	public void setCommentLayout(DynamicReviewInfo bean) {
		
		llComment.removeAllViews();
		for(int i = 0; i < bean.reviewList.size(); i ++)
		{
			CommentItemView view = new CommentItemView(getContext());
			view.init(bean.reviewList.get(i), commentOnClickListener);
			llComment.addView(view);
		}
	}
}
