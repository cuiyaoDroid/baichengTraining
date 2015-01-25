package com.xianzhi.service;

import java.util.List;

import com.xianzhi.baichengtraining.TrainMainActivity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class NotificationService extends BaseRongYunService {
	private ActivityManager mActivityManager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mActivityManager = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Intent mNotificationIntent = new Intent(this, TrainMainActivity.class);
		notifyClient("", "新消息提醒", "测试", mNotificationIntent);
		return START_STICKY;
	}




	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public boolean isAppOnForeground() {
		List<RunningTaskInfo> taskInfos = mActivityManager.getRunningTasks(1);
		if (taskInfos.size() > 0
				&& TextUtils.equals(getPackageName(),
						taskInfos.get(0).topActivity.getPackageName())) {
			return true;
		}
		return false;
	}

}
