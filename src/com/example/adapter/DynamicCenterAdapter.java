package com.example.adapter;

import java.util.ArrayList;

import com.example.dynamicdemo.R;
import com.example.entity.DynamicItemBean;
import com.example.view.DynamicItemVIew;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @ClassName DynamicCenterAdapter.java
 * @author KevinSu kevinsu917@126.com
 * @version 创建时间：2014-10-18 下午6:18:08
 * @Description: 动态中心的适配器
 */

public class DynamicCenterAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<DynamicItemBean> dataList;
	private View.OnClickListener commentBtnListener;
	
	
	public DynamicCenterAdapter(Context context, ArrayList<DynamicItemBean> list, View.OnClickListener listener) {
		// TODO Auto-generated constructor stub
		mContext = context;
		dataList = list;
		commentBtnListener = listener;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public DynamicItemBean getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(arg1 == null)
		{
			arg1 = new DynamicItemVIew(mContext);
		}
		
		((DynamicItemVIew)arg1).Build(getItem(arg0), arg0, commentBtnListener);
		return arg1;
	}

}
