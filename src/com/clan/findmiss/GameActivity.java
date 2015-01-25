package com.clan.findmiss;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clan.findmiss.utils.AlertDialogUtils;
import com.clan.findmiss.utils.AlertDialogUtils.OnDialogListener;
import com.clan.findmiss.view.GameView;
import com.xianzhi.baichengtraining.R;

public class GameActivity extends Activity implements OnClickListener{

	private LinearLayout layout_game;
	private GameView gView;
	private float wPercent=0.98f,hPercent=0.80f;
	private DisplayMetrics displayMetrics;
	private Button btn_step;//过关数
	private Button btn_score;//分数
	private ImageButton btn_music;//
	private ImageButton btn_pause;//
	private RelativeLayout rl_find;
	private TextView tv_find;
	private ProgressBar pb_time;
	private int scoreBase=10;
	private long mLastBackPress;
	private static final long mBackPressThreshold = 3000;
	private Toast pressBackToast;
	public  int level=1;
	// 计时显示
    public static int timerIndex = 300;
    // 计时器类
    private Timer timer;
    /**
     * 计时器线程
     */
    private TimerTask timerTask;
    /**
     * UI更新Handler
     */
    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    switch (msg.what) {
	    case 1:
	    	// 更新计时器
	    	timerIndex--;
	    	if(timerIndex<=0||gView.bg.full>=gView.findCount&&!gView.isPause) {
	    		gView.isPause=true;
	    		String message=timerIndex<=0?"游戏结束！":"恭喜！闯关成功，进入下一关";
	    		AlertDialogUtils.show(GameActivity.this, "提示", message, "确定", null, new OnDialogListener() {
					
					@Override
					public void onConfirm() {
						// TODO Auto-generated method stub	
						if(timerIndex>0) {
							gView.doNextLevel();
						}
						timerIndex=300;
					}
					
					@Override
					public void onCancel() {}
				});
	    	}
	    	btn_step.setText(gView.level+"/8");
	    	pb_time.setProgress(timerIndex);
	    	btn_score.setText(String.valueOf(gView.bg.full*scoreBase));
	    	tv_find.setText(String.valueOf(gView.findCount-gView.bg.full));
	    	break;
	    default:
	    	break;
	    }
	}
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.game);
		initGameView();
	}
	@SuppressLint("ShowToast")
	void initGameView() {
		pressBackToast = Toast.makeText(getApplicationContext(),"再按一次退出游戏", Toast.LENGTH_SHORT);
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		layout_game=(LinearLayout)this.findViewById(R.id.layout_game);
		rl_find=(RelativeLayout)this.findViewById(R.id.rl_find);
		tv_find=(TextView)this.findViewById(R.id.tv_find);
		btn_step=(Button)this.findViewById(R.id.btn_step);
		btn_score=(Button)this.findViewById(R.id.btn_score);
		btn_music=(ImageButton)this.findViewById(R.id.btn_music);
		btn_pause=(ImageButton)this.findViewById(R.id.btn_pause);
		pb_time=(ProgressBar)this.findViewById(R.id.pb_time);
		btn_music.setOnClickListener(this);
		btn_pause.setOnClickListener(this);
		initGameLevel();
		pb_time.setMax(timerIndex);
		// 启用计时器
		timer = new Timer(true);
		// 计时器线程
		timerTask = new TimerTask() {

		    @Override
		    public void run() {
		    	if(!gView.isPause) {
		    		handler.sendEmptyMessage(1);
		    	}
		    }
		};
		// 每1000ms执行 延迟0s
		timer.schedule(timerTask, 0, 1000);	    
	}
	void initGameLevel() {
		int width=(int) (displayMetrics.widthPixels*wPercent);
		int height=(int) (displayMetrics.heightPixels*hPercent);
		gView=new GameView(this,width,height);
		layout_game.addView(gView);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.btn_music:
			
			break;
		case R.id.btn_pause:
			if(gView.isPause)
				gView.isPause=false;
			else
				gView.isPause=true;
			break;
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(timer!=null) {
			timer.cancel();
			timerTask.cancel();
		}
	}
	@Override 
	public void onResume() {
	    super.onResume();
	    //MobclickAgent.onResume(this);       //统计时长
	}
	@Override 
	public void onPause() {
	    super.onPause();
	    //MobclickAgent.onPause(this);
	}
	@Override
	public void onBackPressed() {
	    long currentTime = System.currentTimeMillis();
	    if (Math.abs(currentTime - mLastBackPress) > mBackPressThreshold) {
	        pressBackToast.show();
	        mLastBackPress = currentTime;
	    } else {
	    	pressBackToast.cancel();
	        super.onBackPressed();
	    }
	}
}
