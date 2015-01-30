package com.xianzhi.baichengtraining;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.tingshuo.tool.L;
import com.tingshuo.tool.db.DBHelper;
import com.tingshuo.web.http.HttpJsonTool;


public class InitActivity extends Activity {
	public static String SDCardRoot = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator;
	public static String RAIL = ".bc_rail";
	public static void putVersion(String s) {
		try {
			FileOutputStream outStream = new FileOutputStream(SDCardRoot + RAIL
					+ "/1.txt", false);
			OutputStreamWriter writer = new OutputStreamWriter(outStream,
					"gb2312");
			writer.write(s);
			writer.flush();
			writer.close();

			outStream.close();
		} catch (Exception e) {
			System.out.println("write to sdcard for error");
		}
	}
	private void createPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	private void upGradeDBifnessage(){
		DBHelper helper=new DBHelper(getApplicationContext());
		helper.close();
	}
	public static String getToken() {
		String txt = "";
		try {
			String filename = InitActivity.SDCardRoot + InitActivity.RAIL
					+ "/token.txt";
			FileInputStream fin = new FileInputStream(filename);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			fin.close();
			String type = codetype(buffer);
			txt = EncodingUtils.getString(buffer, type);
			return txt;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";

	}
	static String codetype(byte[] head) {
		String type = "";
		byte[] codehead = new byte[3];
		System.arraycopy(head, 0, codehead, 0, 3);
		if (codehead[0] == -1 && codehead[1] == -2) {
			type = "UTF-16";
		} else if (codehead[0] == -2 && codehead[1] == -1) {
			type = "UNICODE";
		} else if (codehead[0] == -17 && codehead[1] == -69
				&& codehead[2] == -65) {
			type = "UTF-8";
		} else {
			type = "GB2312";
		}
		return type;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		createPath(TrainingApp.appPath);
		upGradeDBifnessage();
		putVersion(getString(R.string.version));
		
		//Bundle bundle = getIntent().getExtras();
		int type=getIntent().getIntExtra("type", 0);
		//if(bundle!=null){
			//TrainingApp.token = bundle.getString("token");
			//HttpJsonTool.ServerUrl = bundle.getString("weburl");
			//TrainingApp.userId = bundle.getInt("userId");
			//type=bundle.getInt("type");
		//}
		//L.i("type  "+type);
		if(type==0){
			Intent intent=new Intent(getApplicationContext(),TrainMainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
			startActivity(intent);
		}else if(type==1) {
			Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
			startActivity(intent);
		}
		else if(type==2) {
			Intent intent=new Intent(getApplicationContext(),RiskDetailActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
			startActivity(intent);
		}
		else if(type==3) {
			Intent intent=new Intent(getApplicationContext(),BlankActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
			startActivity(intent);
		}
		else if(type==4) {
			Intent intent=new Intent(getApplicationContext(),DynamicDetailActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
			startActivity(intent);
		}else if(type==5) {
			Intent intent=new Intent(getApplicationContext(),DynamicReDetailActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
			startActivity(intent);
		}else if(type==6) {
			Intent intent=new Intent(getApplicationContext(),DynamicPublishActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
			startActivity(intent);
		}
		finish();
		
	}
}
