package com.clan.findmiss.view;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 游戏逻辑
 * @author lanyj
 *
 */
public class GameRule {
	//Bitmap bmp;
	float x, y, w, h;
	Paint paint;
	boolean isClicked = false;
	int xw=1;

	GameRule(float x, float y, float w, float h, int color) {
		//this.bmp = bmp;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(GameBg.scaleY * 2);//圆圈边框大小
		paint.setStyle(Paint.Style.STROKE);
		xw = new Random().nextInt(1) * GameBg.w;

	}

	public void draw(Canvas canvas) {
		//画圆圈
		canvas.save();
		canvas.scale(GameBg.scaleX, GameBg.scaleY, x + xw, y);
		//canvas.drawBitmap(bmp, x + xw, y, paint);
		canvas.restore();
		if (isClicked) {
			for (int i = 0; i < 2; i++) {
				canvas.drawOval(new RectF(GameBg.w * i + x, y, GameBg.w * i + x + w, y
						+ h), paint);
			}
		}

	}
	/**
	 * 判断点击区域	
	 * @param xx
	 * @param yy
	 * @return
	 *
	 * @version:v1.0
	 * @author:lanyj
	 * @date:2014-6-25 下午4:14:17
	 */
	public boolean isClick(float xx, float yy) {
		for (int i = 0; i < 2; i++) {
			if (xx > GameBg.w * i + x && xx < GameBg.w * i + x + w && yy > y
					&& yy < y + h) {
				return true;
			}

		}

		return false;
	}

}
