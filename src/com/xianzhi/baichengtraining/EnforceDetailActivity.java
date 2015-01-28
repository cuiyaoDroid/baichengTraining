package com.xianzhi.baichengtraining;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EnforceDetailActivity extends BaseSwipeBaceActivity{
	private LinearLayout linear_layout;
	
	
	private static final String[] titles=new String[]{"序号","车站","学习时间"
		,	"学习地点","组织人","培训方式","考试时间","考试地点","操作"};
	private static final String[] contents=new String[]{"1","乌兰浩特","2015-02-03\n09:00-11:00"
		,"学习室","张志强","讲解","2015-02-04\n09:00-9:30","学习室","<u>查看</u>"};
	@Override
	protected void initContentView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_detail);
		linear_layout = (LinearLayout)findViewById(R.id.linear_layout);
	}

	@Override
	protected void initContentData() {
		// TODO Auto-generated method stub
		for(int i=0;i<titles.length&&i<contents.length;i++){
			View cell=getLayoutInflater().inflate(R.layout.cell_detail,null );
			TextView title=(TextView)cell.findViewById(R.id.title);
			title.setText(titles[i]);
			TextView content=(TextView)cell.findViewById(R.id.content);
			content.setText(Html.fromHtml(contents[i]));
			if(i==titles.length-1){
				content.setClickable(true);
				content.setTextColor(getResources().getColorStateList(R.color.title_txt_bg));
				content.setOnClickListener(this);
			}
			linear_layout.addView(cell);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.goback_btn:
			finish();
			break;
		case R.id.content:
			Intent intent=new Intent(getApplicationContext(),TrainEnforceDetailListActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	@Override
	protected void initTitleBar(){
		super.initTitleBar();
		title_right_btn.setVisibility(View.GONE);
		image_title.setText("详情");
	}
}
