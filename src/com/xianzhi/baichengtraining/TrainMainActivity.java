package com.xianzhi.baichengtraining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.xianzhi.view.adapter.ExpandableMainAdapter;

public class TrainMainActivity extends BaseTitleBarActivity {
	private ExpandableListView mExpandlist;
	private ExpandableListAdapter mExpandAdapter;
	private List<Map<String, Object>> group;
	private List<List<Map<String, Object>>> children;
	public static final String GROUP_CONTENT="group_content";
	public static final String CHILD_CONTENT="child_content";
	
	
	public static final String[] groupnames=new String[]{"培训管理","考试管理"
		,"演练管理","网络教育","综合管理"};
	public static final String[][] childnames=new String[][]{{"教学计划","培训流程管理"
		,"查询分析"},{},{},{},{"流程管理","模块管理","档案管理","兼课津贴"
			,"上岗证管理","培训游戏","统计分析","题库管理"}};
	
	@Override
	protected void initContentView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_train_main);
		mExpandlist = (ExpandableListView) findViewById(R.id.expand_list);
	}
	@Override
	protected void initTitleBar(){
		super.initTitleBar();
		title_right_btn.setVisibility(View.GONE);
		//title_goback_btn.setVisibility(View.GONE);
	}
	@Override
	protected void initContentData() {
		// TODO Auto-generated method stub
		
		refreshExpandData();
		mExpandAdapter = new ExpandableMainAdapter(this, group,
				R.layout.cell_expandable_group, new String[] { GROUP_CONTENT },
				new int[] { R.id.group_name }, children,
				R.layout.cell_expandable_child, new String[] { CHILD_CONTENT }, 
				new int[] { R.id.child_name });
		mExpandlist.setAdapter(mExpandAdapter);
		mExpandlist.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				if(groupPosition==0&&childPosition==0){
					Intent intent=new Intent(getApplicationContext(),TrainPlainActivity.class);
					startActivity(intent);
				}else if(groupPosition==0&&childPosition==1){
					Intent intent=new Intent(getApplicationContext(),TrainProcessActivity.class);
					startActivity(intent);
				}else if(groupPosition==0&&childPosition==2){
					Intent intent=new Intent(getApplicationContext(),TrainEnforceDetailListActivity.class);
					startActivity(intent);
				}
				return false;
			}
		});
		mExpandlist.expandGroup(0);
	}

	private void refreshExpandData(){
		group = new ArrayList<Map<String, Object>>();
		children = new ArrayList<List<Map<String, Object>>>();
		for(int i=0;i<groupnames.length;i++){
			Map<String, Object>groupdata=new HashMap<String,Object>();
			groupdata.put(GROUP_CONTENT, groupnames[i]);
			List<Map<String, Object>>childlist=new ArrayList<Map<String, Object>>();
			for(int j=0;j<childnames[i].length;j++){
				Map<String, Object>childdata=new HashMap<String,Object>();
				childdata.put(CHILD_CONTENT, childnames[i][j]);
				childlist.add(childdata);
			}
			group.add(groupdata);
			children.add(childlist);
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

	
}