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
	// ��Ļ���
	public static int screenWidth;
	// ��Ļ�߶�
	public static int screenHeight;
	private MyGallery gallery;
	public static List<Integer> imgs;
	private GalleryAdapter galleryAdapter;

	private final static String downloaduri[]=new String[]{
			"ģ����ѵ/LA����֪ʶ/LA1��·��·.docx",
			"ģ����ѵ/LA����֪ʶ/LA2��·��վ.docx",
			"ģ����ѵ/LA����֪ʶ/LA3�ź�ͨ���豸.docx",
			"ģ����ѵ/LA����֪ʶ/LA4��������.docx",
			"ģ����ѵ/LBרҵ֪ʶ/LB1�г�����.docx",
			"ģ����ѵ/LBרҵ֪ʶ/LB2�г�������.doc",
			"ģ����ѵ/LBרҵ֪ʶ/LB3�ӷ��г�.doc",
			"ģ����ѵ/LBרҵ֪ʶ/LB4������ҵ.doc",
			"ģ����ѵ/LCְҵ����/",
			"ģ����ѵ/LCְҵ����/·��������.DOC",
			"ģ����ѵ/LD��ȫ֪ʶ/LD1����ȫ��ҵ��׼.doc",
			"",
			"ģ����ѵ/LD��ȫ֪ʶ/LD3����ϵͳ�����¹ʰ���.doc",
			"ģ����ѵ/SBרҵ����/SB1�ӷ��г���׼",
			"ģ����ѵ/SBרҵ����/SB2����������ҵ.doc",
			"",
			"ģ����ѵ/SCӦ������/SC1 ��������������ҵ.docx",
			"ģ����ѵ/SCӦ������/SC2 ��������ҵӦ�����û�������.docx",
			"ģ����ѵ/SCӦ������/SC3�����豸����ʱ��Ӧ������.docx",
			"ģ����ѵ/SCӦ������/SC4�źŻ��豸����ʱ��Ӧ������.docx",
			"ģ����ѵ/SCӦ������/SC5�����豸����ʱ��Ӧ������.docx",
			"ģ����ѵ/SCӦ������/SC6 �����·����ʱ��Ӧ������.docx",
			"ģ����ѵ/SCӦ������/SC7ͣ��ʱ��Ӧ������.docx",
			"ģ����ѵ/SCӦ������/SC8 ͨ�÷������г��취.docx"};

	float beforeLenght = 0.0f; // ���������
	float afterLenght = 0.0f; // ���������
	boolean isScale = false;
	float currentScale = 1.0f;// ��ǰͼƬ�����ű���

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
		dowloadprogressDialog.setMessage("�����������Ե�...");
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
			T.show(getApplicationContext(), "���ص��ļ����𻵣�������",Toast.LENGTH_LONG);
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
			image_title.setText("��ѵ����");
			break;
		case 1:
			image_title.setText("ģ�黯��ѵ");
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
		gallery.setVerticalFadingEdgeEnabled(false);// ȡ����ֱ����߿�
		gallery.setHorizontalFadingEdgeEnabled(false);// ȡ��ˮƽ����߿�
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