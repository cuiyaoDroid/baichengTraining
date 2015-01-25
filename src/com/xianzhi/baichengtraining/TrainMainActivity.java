package com.xianzhi.baichengtraining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xianzhi.service.NotificationService;

public class TrainMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_train_main);
		Intent intent =new Intent(this,NotificationService.class);
		startService(intent);
		
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				float a=9/0;
			}
		}, 2000);
	}

}