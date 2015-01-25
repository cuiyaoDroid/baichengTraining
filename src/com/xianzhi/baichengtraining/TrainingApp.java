package com.xianzhi.baichengtraining;

import com.tingshuo.tool.CrashHandler;

import android.app.Application;
import android.os.Environment;

public class TrainingApp extends Application{
	
	public static final String appPath = Environment
			.getExternalStorageDirectory() + "/.cyHearFrom/";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CrashHandler.getInstance().init(this);
	}
}
