package com.clan.findmiss.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 画出图片
 * @author lanyj
 *
 */
public class GameBg {
	//存在不同的两张图片
	Bitmap bmpFirst;
	Bitmap bmpSecond;
	static int w, h;

	static float scaleX, scaleY;

	GameBg(Bitmap bmpFirst,Bitmap bmpSecond) {
		this.bmpFirst = bmpFirst;
		this.bmpSecond=bmpSecond;
		w = GameView.sw / 2;
		h = GameView.sh;
		scaleX = (float) w / bmpFirst.getWidth();
		scaleY = (float) h / bmpFirst.getHeight();
	}

	public int full = 0;

	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xffff0000);

		for (int i = 0; i < 2; i++) {
			canvas.save();
			canvas.scale(scaleX, scaleY, w * i, 0);
			if(i==0) {
				//画出左边图
				canvas.drawBitmap(bmpFirst, w * i, 0, paint);
			}else {
				//画出右边图
				canvas.drawBitmap(bmpSecond, w * i, 0, paint);
			}
			canvas.restore();
		}

		canvas.drawLine(w, 0, w, h, paint);

	}

}
