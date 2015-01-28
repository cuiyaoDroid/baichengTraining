package com.xianzhi.baichengtraining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xianzhi.view.adapter.GridAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TrainEnforceActivity extends BaseSwipeBaceActivity {
	private ListView mListView;
	private GridAdapter adapter;
	private List<Map<String, Object>> mData;
	public static final String CONTENT1 = "content1";
	public static final String CONTENT2 = "content2";
	public static final String CONTENT3 = "content3";
	public static final String CONTENT4 = "content4";
	public static final String CONTENT5 = "content5";
	public static final String[][] childnames = new String[][] {
			{ "��վ", "ѧϰʱ��", "ѧϰ�ص�", "��֯��", "��ѵ��ʽ" }
			, { "��������", "2015-02-03\n09:00-11:00", "ѧϰ��" , "��־ǿ", "����" } };		
					
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
	protected void initTitleBar(){
		super.initTitleBar();
		title_right_btn.setVisibility(View.GONE);
		image_title.setText("��ѧ�ƻ�ʵʩ");
	}
	@Override
	protected void initContentView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_train_plain);
		mData = new ArrayList<Map<String, Object>>();
		refreshListData();
		mListView = (ListView) findViewById(R.id.listView);
		adapter = new GridAdapter(this, mData);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(position==0){
					return;
				}
				Intent intent=new Intent(getApplicationContext(),EnforceDetailActivity.class);
				startActivity(intent);
			}
		});
	}

	private void refreshListData() {
		mData.clear();
		for (int i = 0; i < childnames.length; i++) {
			Map<String, Object> data = new HashMap<String, Object>();
			for (int j = 0; j < childnames[i].length; j++) {
				switch (j) {
				case 0:
					data.put(CONTENT1, childnames[i][j]);
					break;
				case 1:
					data.put(CONTENT2, childnames[i][j]);
					break;
				case 2:
					data.put(CONTENT3, childnames[i][j]);
					break;
				case 3:
					data.put(CONTENT4, childnames[i][j]);
					break;
				case 4:
					data.put(CONTENT5, childnames[i][j]);
					break;
				default:
					break;
				}
			}
			mData.add(data);
		}

	}

	@Override
	protected void initContentData() {
		// TODO Auto-generated method stub

	}
	/*
	 * ��� ���Ʋ��� ��ѵ������ ��ѵ��ʽ ��ѵ��� ��ѵ���� ��ѵ�ص� ����ʱ�� ��ѵ���� ���� 1 ְ�̿� ������ѵ �Ѳ� ������ѵ ��������
	 * ѧϰ�� 9:00-9:30 ���� �鿴
	 */
}
