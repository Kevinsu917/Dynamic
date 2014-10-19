package com.example.view;

import com.example.dynamicdemo.R;
import com.example.entity.DynamicCommentBean;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.BaseMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * @ClassName CommentItemView.java
 * @author KevinSu kevinsu917@126.com
 * @version 创建时间：2014-10-19 上午10:14:01
 * @Description: 单条评论的View
 */

public class CommentItemView extends RelativeLayout{

	//展示评论的TextView
	private TextView tvShowComment;
	
	//是否需要要整个View接受点击事件
	private boolean bReceiveTouchEvent = true;
	
	private View.OnClickListener commentClickListener;//评论点击事件
	//设置是否接受点击事件
	public void setIsReceiveTouchEvent(boolean isReceive)
	{
		bReceiveTouchEvent = isReceive;
	}
	
	//获取是否接受点击事件
	public boolean getIsReceiveTouchEvent()
	{
		return bReceiveTouchEvent;
	}
	
	public CommentItemView(Context context) {
		super(context);
	}

	public CommentItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void init(final DynamicCommentBean bean, View.OnClickListener listener) {

		//需要考虑表情的情况
		tvShowComment = new TextView(getContext());
		commentClickListener = listener;
	
		// 1.某人评论；XXX：something
		// 2.A回复B的评论; A 回复 B : something
		
		//评论的主语
		String subjectName = bean.getCommentUserName();
		SpannableString subjectSpanText = new SpannableString(subjectName);
		subjectSpanText.setSpan(new Clickable(new NameClickListener(subjectSpanText)),
				0, subjectSpanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(subjectSpanText);
		
		if(bean.getReplyUserName() != null)
		{
			
			
			//评论的宾语
			String objectName = bean.getReplyUserName();
			if(!TextUtils.isEmpty(objectName))
			{
				builder.append(getResources().getString(R.string.reply_text));
				
				SpannableString objectSpanText = new SpannableString(objectName);
				
				objectSpanText.setSpan(new Clickable(new NameClickListener(objectSpanText)),
						0, objectSpanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
				builder.append(objectSpanText);
			}
			
		}

		builder.append(":");
		builder.append(bean.getCommentContent());
		
		
		tvShowComment.setText(builder);
	
		int colorValue = getResources().getColor(R.color.c_333333);
		tvShowComment.setTextColor(colorValue);
		tvShowComment.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		tvShowComment.setMovementMethod(new MyMovementMethod(this));
		tvShowComment.setOnClickListener(commentClickListener);
		
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tvShowComment.setLayoutParams(param);
		addView(tvShowComment);
		
	}


	//评论中名字的点击事件
	class NameClickListener implements View.OnClickListener{

		private SpannableString userName;
		
		public NameClickListener(SpannableString name) {
			userName = name;
		}
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	//TextView中的Span区域点击事件
	class Clickable extends ClickableSpan implements OnClickListener {

		private final View.OnClickListener mListener;

		public Clickable(View.OnClickListener l) {
			mListener = l;
		}

		@Override
		public void onClick(View widget) {
			// TODO Auto-generated method stub
			mListener.onClick(widget);
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			// TODO Auto-generated method stub
			super.updateDrawState(ds);
			ds.setUnderlineText(false);
			int colorValue = getResources().getColor(R.color.c_324d82);
			ds.setColor(colorValue);
		}
	}

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public class MyMovementMethod extends BaseMovementMethod {

		private CommentItemView mItemView;
		public MyMovementMethod(CommentItemView view)
		{
			mItemView = view;
		}
			
		//是否要传递到整个CommentItemView
		private void setPassToTV(boolean isClick)
		{
			if(mItemView != null)
			{
				mItemView.bReceiveTouchEvent = isClick;
			}
		}
		
		
		public boolean onTouchEvent(TextView widget, Spannable buffer,
				MotionEvent event) {
			int action = event.getAction();

			if (action == MotionEvent.ACTION_UP
					|| action == MotionEvent.ACTION_DOWN) {
				int x = (int) event.getX();
				int y = (int) event.getY();

				x -= widget.getTotalPaddingLeft();
				y -= widget.getTotalPaddingTop();

				x += widget.getScrollX();
				y += widget.getScrollY();

				Layout layout = widget.getLayout();
				int line = layout.getLineForVertical(y);
				int off = layout.getOffsetForHorizontal(line, x);

				ClickableSpan[] link = buffer.getSpans(off, off,
						ClickableSpan.class);
				
				if (link.length != 0) {
				
					//点击的是Span区域，不要把点击事件传递CommentItemView
					setPassToTV(false);
					
					if (action == MotionEvent.ACTION_UP) {
						link[0].onClick(widget);
						Selection.removeSelection(buffer);
					} else if (action == MotionEvent.ACTION_DOWN) {
						Selection.setSelection(buffer,
								buffer.getSpanStart(link[0]),
								buffer.getSpanEnd(link[0]));
					}
					((View) widget.getParent()).setBackgroundResource(R.color.transparent);
					
				} else {
					
					setPassToTV(true);
					if (action == MotionEvent.ACTION_UP) {
						((View) widget.getParent()).setBackgroundResource(R.color.transparent);
					} else if (action == MotionEvent.ACTION_DOWN) {
						//点击选中效果
						((View) widget.getParent()).setBackgroundResource(R.color.transparent);
					}else
					{
						((View) widget.getParent()).setBackgroundResource(R.color.transparent);
					}
					Selection.removeSelection(buffer);
				}
			}
			
			return Touch.onTouchEvent(widget, buffer, event);
		}
		
	}
}
