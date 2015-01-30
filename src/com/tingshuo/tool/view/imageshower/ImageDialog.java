package com.tingshuo.tool.view.imageshower;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.tingshuo.tool.DensityUtil;
import com.tingshuo.tool.OpenFiles;
import com.tingshuo.tool.view.imageshower.MyGallery.singleTapListener;
import com.tingshuo.web.http.DownloadAsyncTask;
import com.tingshuo.web.http.HttpJsonTool;
import com.tingshuo.web.http.downloadCallbackListener;
import com.xianzhi.baichengtraining.BaseTitleBarActivity;
import com.xianzhi.baichengtraining.DetailActivity;
import com.xianzhi.baichengtraining.R;
import com.xianzhi.baichengtraining.TrainingApp;
import com.xianzhi.stool.FileNameTool;
import com.xianzhi.stool.T;


public class ImageDialog extends BaseTitleBarActivity implements singleTapListener{
	// 屏幕宽度
	public static int screenWidth;
	// 屏幕高度
	public static int screenHeight;
	private MyGallery gallery;
	public static List<Integer> imgs;
	private GalleryAdapter galleryAdapter;

	private final static String downloaduri[]=new String[]{
			"模块培训/LA基本知识/LA1铁路线路.docx",
			"模块培训/LA基本知识/LA2铁路车站.docx",
			"模块培训/LA基本知识/LA3信号通信设备.docx",
			"模块培训/LA基本知识/LA4机车车辆.docx",
			"模块培训/LB专业知识/LB1列车编组.docx",
			"模块培训/LB专业知识/LB2行车闭塞法.doc",
			"模块培训/LB专业知识/LB3接发列车.doc",
			"模块培训/LB专业知识/LB4调车作业.doc",
			"模块培训/LC职业道德/",
			"模块培训/LC职业道德/路情局情题库.DOC",
			"模块培训/LD安全知识/LD1人身安全作业标准.doc",
			"",
			"模块培训/LD安全知识/LD3车务系统溜逸事故案例.doc",
			"模块培训/SB专业技能/SB1接发列车标准",
			"模块培训/SB专业技能/SB2车机联控作业.doc",
			"",
			"模块培训/SC应急处理/SC1 深入理解非正常作业.docx",
			"模块培训/SC应急处理/SC2 非正常作业应急处置基本方法.docx",
			"模块培训/SC应急处理/SC3闭塞设备故障时的应急处置.docx",
			"模块培训/SC应急处理/SC4信号机设备故障时的应急处置.docx",
			"模块培训/SC应急处理/SC5道岔设备故障时的应急处置.docx",
			"模块培训/SC应急处理/SC6 轨道电路故障时的应急处置.docx",
			"模块培训/SC应急处理/SC7停电时的应急处置.docx",
			"模块培训/SC应急处理/SC8 通用非正常行车办法.docx"};

	float beforeLenght = 0.0f; // 两触点距离
	float afterLenght = 0.0f; // 两触点距离
	boolean isScale = false;
	float currentScale = 1.0f;// 当前图片的缩放比率

	private class GalleryChangeListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			currentScale = 1.0f;
			isScale = false;
			beforeLenght = 0.0f;
			afterLenght = 0.0f;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	ProgressDialog dowloadprogressDialog;
	private void initdowloadprogressDialog(){
		dowloadprogressDialog = new ProgressDialog(this);
		dowloadprogressDialog.setTitle("");
		dowloadprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dowloadprogressDialog.setMessage("正在下载请稍等...");
		dowloadprogressDialog.setCancelable(false);
	}
	private void downloadFile(String url){
		dowloadprogressDialog.show();
		DownloadAsyncTask task=new DownloadAsyncTask(dowloadprogressDialog);
		task.setdownloadCallbackListener(new downloadCallbackListener() {
			@Override
			public void onComplete(File downloadFile) {
				// TODO Auto-generated method stub
				openfile(downloadFile);
			}
		});
		task.execute(url);
	}
	private void openfile(File downloadFile) {
		if(downloadFile==null){
			T.show(getApplicationContext(), "下载的文件有损坏，请重试",Toast.LENGTH_LONG);
			return;
		}
		try {
			OpenFiles.OpenFile(getApplicationContext(), downloadFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Override
	protected void initTitleBar(){
		super.initTitleBar();
		title_right_btn.setVisibility(View.GONE);
		switch (pre_img) {
		case 0:
			image_title.setText("培训流程");
			break;
		case 1:
			image_title.setText("模块化培训");
			break;
		default:
			break;
		}
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	public void onSingleTapConfirmed(int tapId) {
		// TODO Auto-generated method stub
		//dismiss();
		if(tapId==0){
			Intent intent=new Intent(getApplicationContext(),ImageDialog.class);
			intent.putExtra("ImageId", 1);
			startActivityForResult(intent,0);
		}else if(tapId>0){
			File downloadFile = new File(TrainingApp.appPath+FileNameTool.getExtensionName(downloaduri[tapId-1]));
			if(!downloadFile.exists()){
				downloadFile(downloaduri[tapId-1]);
			}else{
				openfile(downloadFile);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.goback_btn:
			finish();
			break;

		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		img_id=pre_img;
	}
	public static int img_id;
	private int pre_img;
	@Override
	protected void initContentView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.dialog_img);
		pre_img=getIntent().getIntExtra("ImageId", 0);
		initdowloadprogressDialog();
		screenWidth = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();
		screenHeight = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight()-DensityUtil.getStatusBarHeight(getApplicationContext());
		gallery = (MyGallery) findViewById(R.id.gallery);
		gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
		gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
		imgs=new ArrayList<Integer>();
		switch (pre_img) {
		case 0:
			imgs.add(R.drawable.education_three_new);
			break;
		case 1:
			imgs.add(R.drawable.img_tree);
			break;
		default:
			break;
		}
		galleryAdapter = new GalleryAdapter(getApplicationContext(), imgs,gallery);
		gallery.setAdapter(galleryAdapter);
		gallery.setSelection(0);
		gallery.setOnItemSelectedListener(new GalleryChangeListener());
		gallery.setSingleTapListener(this);
	}

	@Override
	protected void initContentData() {
		// TODO Auto-generated method stub
		
	}
}