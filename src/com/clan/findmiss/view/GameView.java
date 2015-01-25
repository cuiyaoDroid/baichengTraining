package com.clan.findmiss.view;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.clan.findmiss.utils.Constants;
import com.xianzhi.baichengtraining.R;

/**
 * 游戏界面
 * @author lanyj
 *
 */
public class GameView extends View {
	static int sw, sh;
	Context context;
	public GameBg bg;
	public Vector<GameRule> vcToch;
	private int circleWidth=55;
	private int circleHeight=55;
	//不同点总数
	public int findCount=3;
	//圆圈色值
	int color[] = { 0xff00ffff,0xff330000,0xff990066,0xfffca702,0xfffc02b2,0xffff0000, 0xff00ff00, 0xff0000ff};
	//存在不同的坐标点
	int info[][] = { { 210, 75}, { 288, 215},{ 220, 180}, { 215, 270},{ 235, 330}};
	//int info[][] = { { 78, 260}, { 320, 75},{ 205, 5}};
	public int level=1;
	private Map<Integer,int[][]> mapPoint=null;
	public boolean isPause=false;
	public GameView(Context context, int sw, int sh) {
		super(context);
		this.context = context;
		this.sw = sw;
		this.sh = sh;
		mapPoint=Constants.getMapData();
		init();
	}

	Resources res = getResources();

	public void init() {
		info=mapPoint.get(level);
		findCount=info.length;
		Bitmap bmpFirst=null,bmpSecond=null;
		if(level==1) {
			bmpFirst=BitmapFactory.decodeResource(res, R.drawable.photo);
			bmpSecond=BitmapFactory.decodeResource(res, R.drawable.photo_find);
		}else if(level==2) {
			bmpFirst=BitmapFactory.decodeResource(res, R.drawable.s1_lv_1_1);
			bmpSecond=BitmapFactory.decodeResource(res, R.drawable.s1_lv_1_1_d1);			
		}else if(level==3) {
			bmpFirst=BitmapFactory.decodeResource(res, R.drawable.s1_lv_1_1);
			bmpSecond=BitmapFactory.decodeResource(res, R.drawable.s1_lv_1_1_d2);
		}else if(level==4) {
			bmpFirst=BitmapFactory.decodeResource(res, R.drawable.s1_lv_1_1_d1);
			bmpSecond=BitmapFactory.decodeResource(res, R.drawable.s1_lv_1_1_d2);
		}
		bg = new GameBg(bmpFirst,bmpSecond);
		Random random = new Random();
		ArrayList<Integer> list = new ArrayList<Integer>();
		do {
			boolean isDiffer = true;
			int number = random.nextInt(info.length);
			for (int i = 0; i < list.size(); i++) {
				if (number == list.get(i)) {
					isDiffer = false;
				}
			}
			if (isDiffer) {
				list.add(number);
			}
		} while (list.size() < findCount);

		vcToch = new Vector<GameRule>();
		for (int i = 0; i < findCount; i++) {
			//BitmapFactory.decodeResource(res,R.drawable.b10_1 + list.get(i)),
			vcToch.addElement(new GameRule(info[list.get(i)][0]
					* GameBg.scaleX, info[list.get(i)][1] * GameBg.scaleY, circleWidth
					* GameBg.scaleX, circleHeight * GameBg.scaleY,
					color[Math.abs(color.length-i-1)]));//color[random.nextInt(color.length)]
		}
		isPause=false;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO: Implement this method
		if (event.getAction() == 0) {
			float xx = event.getX();
			float yy = event.getY();
			if(isPause)return true;
			for (int i = 0; i < vcToch.size(); i++) {
				GameRule area = vcToch.elementAt(i);
				if (area.isClick(xx, yy) && !area.isClicked) {
					area.isClicked = true;
					bg.full++;
					invalidate();
					if (bg.full >= findCount) {
						//成功
					}
					break;
				}
			}
		}

		return true;
	}
	public void doNextLevel() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				level++;
				if(level>mapPoint.size()) {
					level=1;
				}
				init();
				postInvalidate();
			}
		}).start();
	}
	@Override
	public void onDraw(Canvas canvas) {
		// TODO: Implement this method
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(0xffff0000);
		bg.draw(canvas);
		for (int i = 0; i < vcToch.size(); i++) {
			vcToch.elementAt(i).draw(canvas);
		}

	}

}
