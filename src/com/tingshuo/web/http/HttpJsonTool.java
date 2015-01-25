package com.tingshuo.web.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.tingshuo.tool.L;
import com.tingshuo.tool.StatusTool;
import com.tingshuo.web.http.MyMultipartEntity.ProgressListener;

public class HttpJsonTool {

	public enum LoginInfo {
		correct, webfaild, wronginput, correctnoname
	};

	public static String ServerUrl = "http://v.cc-railway.xzh-soft.com:8083";
	public static String BUGServerUrl = "http://211.137.14.52:8080";
	//public static String imgServerUrl = "http://192.168.43.235:9999/";
	//public static String ServerUrl = "http://192.168.43.235:8888/tingshuo/index.php";
	// public static String ServerUrl2 =
	// "http://192.168.1.118/tingshuo/index.php";
	// public static final String ServerUrl = "http://192.168.137.1:8080";
	// public static final String ServerUrl = "http://10.35.45.100:8083";
	// public static final String ServerUrl = "http://10.10.1.45:8080";
	// public static final String ServerUrl =
	// "https://eaccess.syrailway.cn:8443/mapping/sjznbg";
	private static HttpJsonTool httpjsontool = null;
	public static final String STATUS = "status";

	public static final String ERROR = "<error>";
	public static final String SUCCESS = "<success>";
	public static final String ERROR403 = "<error403>";

	public static synchronized HttpJsonTool getInstance() {
		if (httpjsontool == null) {
			httpjsontool = new HttpJsonTool();
		}
		return httpjsontool;
	}

	private static CookieStore cookieInfo = null;

	private HttpClient getHttpClient() {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);// Á¬½ÓÊ±¼ä20s
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
		return client;
	}

	public void getCookieInfo() {
		Thread th = new Thread() {
			public void run() {
				HttpClient httpClient = HttpsClient.getInstance()
						.getHttpsClient();
				HttpGet httpRequest = new HttpGet(ServerUrl + "/1.jsp");
				try {
					httpClient.execute(httpRequest);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cookieInfo = ((AbstractHttpClient) httpClient).getCookieStore();
			}
		};
		th.start();
	}

	/**
	 * ¿ìËÙ×¢²á
	 * 
	 * @param context
	 * @param role_id
	 * @param sex
	 * @return
	 */
	public synchronized String register(Context context, int role_id, int sex) {
		try {
			HttpClient client = getHttpClient();
			if (cookieInfo != null) {
				((AbstractHttpClient) client).setCookieStore(cookieInfo);
			}
			StringBuilder builder = new StringBuilder();
			HttpPost httpRequest = new HttpPost(ServerUrl
					+ "/user/fastregister/");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("role_id", String
					.valueOf(role_id)));
			params.add(new BasicNameValuePair("sex", String.valueOf(sex)));
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = client.execute(httpRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 403) {
				httpjsontool = null;
				return ERROR403;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			L.i(builder.toString());
			JSONObject jsonObject = new JSONObject(builder.toString());
			int status = jsonObject.optInt(STATUS);
			if (status != StatusTool.STATUS_OK) {
				return ERROR;
			}
			JSONObject json = jsonObject.getJSONObject("data");
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			return ERROR + "ÍøÂç´íÎó";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR + "ÍøÂç´íÎó";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR + "ÍøÂç´íÎó";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR + "ÍøÂç´íÎó";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR + "ÍøÂç´íÎó";
		}
		return SUCCESS;
	}
	/**
	 * ·¢ËÍ´íÎóÈÕÖ¾
	 * 
	 * @param context
	 * @param holder
	 *            Ö÷ÌâÄÚÈÝ
	 * @param progressDialog
	 * @return
	 */
	long filesize;
	public synchronized String uploadBUG(Context context,
			File file) {
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreProtocolPNames.HTTP_CONTENT_CHARSET,
					Charset.forName("UTF-8"));
			StringBuilder builder = new StringBuilder();
			HttpPost httpRequest = new HttpPost(BUGServerUrl + "/CrashReport/upload.json");
			filesize = file.length();
			
			ProgressListener listener = new ProgressListener() {

				@Override
				public void transferred(long num) {
					// TODO Auto-generated method stub
					
				}
			};
			MyMultipartEntity mpEntity = new MyMultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE, null,
					Charset.forName("UTF-8"), listener);
			ContentBody cbFile = new FileBody(file, "",
					"UTF-8");
			mpEntity.addPart("data", cbFile);
			
			
			httpRequest.setEntity(mpEntity);
			HttpResponse response = client.execute(httpRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 403) {
				httpjsontool = null;
				return ERROR403;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			Log.i("error", builder.toString());
			System.exit(1);
			//L.i("=======result=======");
			/*JSONObject jsonObject = new JSONObject(builder.toString());
			int status = jsonObject.optInt(STATUS);
			if (status != StatusTool.STATUS_OK) {
				return ERROR;
			}*/

			//JSONObject json = jsonObject.getJSONObject("data");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR + "ÍøÂç´íÎó";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR + "ÍøÂç´íÎó";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR + "ÍøÂç´íÎó";
		} 
		return SUCCESS;
	}
	
}
