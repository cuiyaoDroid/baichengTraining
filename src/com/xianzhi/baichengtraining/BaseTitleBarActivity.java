package com.xianzhi.baichengtraining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public abstract class BaseTitleBarActivity extends Activity implements OnClickListener{
	protected ImageButton title_goback_btn;
	protected TextView image_title;
	protected ImageButton title_right_btn;
	protected ProgressBar title_progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initContentView(savedInstanceState);
		initTitleBar();
		initContentData();
	}
	protected void initTitleBar(){
		title_goback_btn = (ImageButton)findViewById(R.id.goback_btn);
		title_goback_btn.setOnClickListener(this);
		image_title = (TextView)findViewById(R.id.image_title);
		title_right_btn = (ImageButton)findViewById(R.id.right_btn);
		title_right_btn.setOnClickListener(this);
		title_progressBar = (ProgressBar)findViewById(R.id.progressBar);
	}
	
	abstract protected void initContentView(Bundle savedInstanceState);
	abstract protected void initContentData();
	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);    
		super.startActivity(intent);
	}
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);    
		super.startActivityForResult(intent, requestCode);
	}
}
