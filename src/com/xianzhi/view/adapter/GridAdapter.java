package com.xianzhi.view.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xianzhi.baichengtraining.R;
import com.xianzhi.baichengtraining.TrainPlainActivity;

public class GridAdapter extends BaseAdapter {
	private List<? extends Map<String, Object>> list;
	protected LayoutInflater mInflater;
	private Context context;
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public GridAdapter(Context context, List<? extends Map<String, Object>> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if (position==0) {
			convertView=initfirstView(position,convertView);
		}else if(position==list.size()-1){
			convertView=initlastView(position,convertView);
		}else{
			convertView=initmidView(position,convertView);
		}
		
		return convertView;
	}
	private View initfirstView(int position,View convertView){
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cell_grid_top, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		setContent(position,viewHolder);
		return convertView;
	}
	private void setContent(int position,ViewHolder viewHolder){
		String content1=(String) list.get(position).get(TrainPlainActivity.CONTENT1);
		viewHolder.content1.setText(content1);
		String content2=(String) list.get(position).get(TrainPlainActivity.CONTENT2);
		viewHolder.content2.setText(content2);
		String content3=(String) list.get(position).get(TrainPlainActivity.CONTENT3);
		viewHolder.content3.setText(content3);
		String content4=(String) list.get(position).get(TrainPlainActivity.CONTENT4);
		viewHolder.content4.setText(content4);
		String content5=(String) list.get(position).get(TrainPlainActivity.CONTENT5);
		viewHolder.content5.setText(content5);
	}
	private View initmidView(int position,View convertView){
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cell_grid_middle, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		setContent(position,viewHolder);
		return convertView;
	}
	private View initlastView(int position,View convertView){
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cell_grid_end, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		setContent(position,viewHolder);
		return convertView;
	}
	public static class ViewHolder {
		public TextView content1;
		public TextView content2;
		public TextView content3;
		public TextView content4;
		public TextView content5;
		public TextView content6;
		public ViewHolder(View convertView){
			content1=(TextView) convertView.findViewById(R.id.content1);
			content2=(TextView) convertView.findViewById(R.id.content2);
			content3=(TextView) convertView.findViewById(R.id.content3);
			content4=(TextView) convertView.findViewById(R.id.content4);
			content5=(TextView) convertView.findViewById(R.id.content5);
		}
	}

}
