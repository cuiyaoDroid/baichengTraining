package com.xianzhi.baichengtraining;

import com.tingshuo.tool.L;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrainEnforceDetailActivity extends BaseSwipeBaceActivity{
	private LinearLayout linear_layout;
	
	
	private static final String[] titles=new String[]{"序号","车站","车间","职名","姓名","考试地点","考试时间","考试分数","操作"};
	
	
	private static final String[] contents=new String[]{"1","乌兰浩特","运转"
		,"技术员","刘宝训","学习室","2015-02-04\n09:02-9:26","99","<u>重考</u>"};
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
			//Intent intent=new Intent(getApplicationContext(),TrainEnforceActivity.class);
			//startActivity(intent);
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
