package com.example.dynamicdemo;

import java.util.ArrayList;

import com.example.adapter.DynamicCenterAdapter;
import com.example.entity.DynamicCommentBean;
import com.example.entity.DynamicGreetInfo;
import com.example.entity.DynamicItemBean;
import com.example.entity.DynamicReviewInfo;
import com.example.utils.MultiUtils;
import com.handmark.pulltorefresh.library.PullToRefreshScrollBarListView;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	//滑动的可刷新的ListView
	private PullToRefreshScrollBarListView prvDynamicList;
	private ImageView ivMyAvater;
	private TextView tvMyName;
	private View myBar;
	private DynamicCenterAdapter mAdapter;
	private ArrayList<DynamicItemBean> dataList = new ArrayList<DynamicItemBean>();
	private String userName = MultiUtils.getCurrentUserName();
	
	private EditText edInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		initData();
		initView();
		
		mAdapter = new DynamicCenterAdapter(this, dataList, commentBtnClickListener);
		prvDynamicList.setAdapter(mAdapter);
	}

	
	private void initView()
	{
		prvDynamicList = (PullToRefreshScrollBarListView) findViewById(R.id.prvDynamicList);
		
		myBar = View.inflate(this, R.layout.view_mine_bar, null);
		myBar.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, MultiUtils.dipToPx(this, 192)));
		
		prvDynamicList.getRefreshableView().addHeaderView(myBar);
		ivMyAvater = (ImageView) myBar.findViewById(R.id.ivMyAvater);
		ivMyAvater.setImageResource(MultiUtils.getAvaterResource(userName));
		
		tvMyName = (TextView) findViewById(R.id.tvMyName);
		tvMyName.setText(userName);
		
		edInput = (EditText) findViewById(R.id.etInput);
	}
	
	private void initData()
	{
		//自造数据
		int count = 10;
		for(int i = 0; i < count; i ++)
		{
			DynamicItemBean bean = new DynamicItemBean();
			bean.dynamicUserName = MultiUtils.getRandomUserName();
			bean.dynamicContent = "这是一条自造的数据内容" + i;
			bean.dynamicList.addAll(MultiUtils.getRandomImageResList((int) (Math.random() * 7)));
			bean.publishTime = System.currentTimeMillis( ) - (60 * 1000) * (long)(Math.random() * (10 * 24 * 60));
		
			bean.greetInfo = new DynamicGreetInfo();
			int greetCount = (int)(Math.random() * 4);
			for(int greetIndex = 0; greetIndex < greetCount; greetIndex ++)
			{
				bean.greetInfo.greetList.add(MultiUtils.getRandomUserName());
			}
			
			bean.reviewInfo = new DynamicReviewInfo();
			int reviewCount = (int)(Math.random() * 4);
			for(int reviewIndex = 0; reviewIndex < reviewCount; reviewIndex ++)
			{
				DynamicCommentBean object = new DynamicCommentBean();
				String commentUser = MultiUtils.getRandomUserName();
				String replyUser = MultiUtils.getRandomUserName();
				object.setCommentUserName(commentUser);
				object.setReplyUserName(replyUser == commentUser ? "" : replyUser);
				object.setCommentContent("这是一条奇怪的评论" + reviewIndex);
				bean.reviewInfo.reviewList.add(object);
			}
			
			dataList.add(bean);
		}	
	}
	
	private View.OnClickListener commentBtnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			/*加2的原因是:setSelectionFromTop从1开始算,而position从0开始算
			 * prvDynamicList开头有个headView所以又要加1
			 */
			int position = (int) arg0.getTag() + 2;
			
			
			int firstIndex = prvDynamicList.getRefreshableView().getFirstVisiblePosition();
			View currentView = prvDynamicList.getRefreshableView().getChildAt(position - firstIndex);
			int currentViewHeight = currentView == null ? 0 : currentView.getHeight();

			int listHeight = prvDynamicList.getRefreshableView().getHeight();
			int offset = listHeight - currentViewHeight;
			
			prvDynamicList.getRefreshableView().setSelectionFromTop(position, offset);
			
			edInput.requestFocus();
			MultiUtils.showInputMethod(MainActivity.this, edInput);
			
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			}, 200);
		}
	};

}
