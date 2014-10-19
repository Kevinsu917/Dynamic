package com.example.view;

import java.util.ArrayList;

import com.example.utils.MultiUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

/**
 * @ClassName MultiImageView.java
 * @author KevinSu kevinsu917@126.com
 * @version 创建时间：2014-10-18 下午11:31:01
 * @Description: 显示1~N张图片的View
 */

public class MultiImageView extends LinearLayout {

	// 照片的Url列表
	private ArrayList<Integer> imagesList;

	/** 长度 单位为Pixel **/
	private int pxOneWidth = MultiUtils.dipToPx(getContext(), 115);// 单张图时候的宽
	private int pxOneHeight = MultiUtils.dipToPx(getContext(), 150);// 单张图时候的高
	private int pxMoreWandH = 0;// 多张图的宽高
	private int pxImagePadding = MultiUtils.dipToPx(getContext(), 3);// 图片间的间距

	private final int MAX_PER_ROW_COUNT = 3;// 每行显示最大数

	private LayoutParams onePicPara;
	private LayoutParams morePara;
	private LayoutParams rowPara;

	public MultiImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MultiImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setList(ArrayList<Integer> lists, int width) {
		initVariable();
		imagesList = lists;
		pxMoreWandH = width/3;
		initView();
	}

	private void initVariable() {
		
		
		onePicPara = new LayoutParams(pxOneWidth, pxOneHeight);

		morePara = new LayoutParams(pxMoreWandH, pxMoreWandH);
		morePara.setMargins(0, 0, pxImagePadding, 0);

		int wrap = LayoutParams.WRAP_CONTENT;
		int match = LayoutParams.MATCH_PARENT;
		rowPara = new LayoutParams(match, wrap);
		rowPara.setMargins(0, 0, 0, pxImagePadding);
	}

	// 根据imageView的数量初始化不同的View布局,还要为每一个View作点击效果
	private void initView()
		{
			this.setOrientation(VERTICAL);
			this.removeAllViews();
			if(imagesList == null || imagesList.size() == 0)
			{
				return;
			}
			
			if(imagesList.size() == 1)
			{
				for(Integer url : imagesList)
				{
					ImageView imageView = new ImageView(getContext());
					imageView.setId(url.hashCode());//指定id
					
					imageView.setLayoutParams(onePicPara);
					imageView.setMaxWidth( pxMoreWandH ) ;
					imageView.setScaleType(ScaleType.CENTER_CROP);
					imageView.setImageResource(url);
					
					int position = 0;
					imageView.setTag(position);
					imageView.setOnClickListener(ImageViewOnClickListener);
					addView(imageView);
					
				}
				
			}else
			{
				int allCount = imagesList.size();
				
				int rowCount = allCount/MAX_PER_ROW_COUNT + (allCount%MAX_PER_ROW_COUNT > 0 ? 1 : 0);//行数
				for(int rowCursor = 0; rowCursor < rowCount; rowCursor ++)
				{
					LinearLayout rowLayout = new LinearLayout(getContext());
					rowLayout.setOrientation(LinearLayout.HORIZONTAL);
					
					rowLayout.setLayoutParams(rowPara);
					if(rowCursor < 2)
					{
						rowLayout.setPadding(0, pxImagePadding, 0, 0);
					}
					
					int columnCount = allCount%MAX_PER_ROW_COUNT == 0 ? MAX_PER_ROW_COUNT : allCount%MAX_PER_ROW_COUNT;
					if(rowCursor != rowCount -1)
					{
						columnCount = MAX_PER_ROW_COUNT;
					}
					addView(rowLayout);
					
					int rowOffset = rowCursor * MAX_PER_ROW_COUNT;//行偏移
					for(int columnCursor = 0; columnCursor < columnCount; columnCursor ++)
					{
						int position = columnCursor + rowOffset;
						int thumbUrl = imagesList.get(position);
						
						ImageView imageView = new ImageView(getContext());
						imageView.setId(thumbUrl);//指定id
					
						imageView.setLayoutParams(morePara);
						imageView.setScaleType(ScaleType.CENTER_CROP);
						imageView.setImageResource(thumbUrl);
						
						imageView.setTag(position);
						imageView.setOnClickListener(ImageViewOnClickListener);
						
						
						
						rowLayout.addView(imageView);
					}
				}
			}
		}

	// 图片点击事件
	private View.OnClickListener ImageViewOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int position = (Integer) arg0.getTag();

		}
	};

}