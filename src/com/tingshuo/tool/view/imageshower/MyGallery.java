/**  
 * MyGallery.java
 * @version 1.0
 * @author Haven
 * @createTime 2011-12-9 下午03:42:53
 * android.widget.Gallery的子函数。此类很重要。建议仔细看
 */
package com.tingshuo.tool.view.imageshower;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

import com.xianzhi.baichengtraining.R;

public class MyGallery extends Gallery {
	private GestureDetector gestureScanner;
	private ShowerImageView imageView;

	Rect mRect0[] = new Rect[] { new Rect(900, 866, 1130, 940),
			new Rect(200, 720, 410, 840), new Rect(500, 730, 690, 810) };
	Rect mRect1[] = new Rect[] { new Rect(20, 465, 75, 650),
			new Rect(105, 465, 155, 650), new Rect(185, 465, 235, 650),
			new Rect(265, 465, 315, 650), new Rect(345, 465, 410, 650),
			new Rect(445, 465, 485, 650), new Rect(514, 465, 570, 660),
			new Rect(605, 465, 660, 660),

			new Rect(685, 465, 740, 650), new Rect(760, 465, 815, 650),
			
			new Rect(855, 465, 910, 650), new Rect(940, 465, 995, 660),
			new Rect(1025, 465, 1075, 650),
			
			new Rect(1125, 465, 1175, 650), new Rect(1205, 465, 1260, 660),
			new Rect(1285, 465, 1335, 650),
			
			new Rect(1395, 465, 1450, 650), new Rect(1475, 465, 1535, 660),
			new Rect(1560, 465, 1615, 1335),new Rect(1650, 465, 1705, 1335),
			new Rect(1745, 465, 1800, 650), new Rect(1835, 465, 1890, 660),
			new Rect(1920, 465, 1975, 1335),new Rect(2010, 465, 2070, 1335)};
	private Context mContext;

	public MyGallery(Context context) {
		super(context);
		this.mContext = context;
	}

	public MyGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

	private float density;
	private boolean clickin = false;

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;

		density = context.getResources().getDisplayMetrics().density;

		gestureScanner = new GestureDetector(new MySimpleGesture());
		this.setOnTouchListener(new OnTouchListener() {

			float baseValue;
			float originalScale;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				View view = MyGallery.this.getSelectedView().findViewById(
						R.id.shower_img);
				if (view instanceof ShowerImageView) {
					imageView = (ShowerImageView) view;

					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						baseValue = 0;
						originalScale = imageView.getScale();

						if (isInside(event, originalScale) != -1) {
							clickin = true;
						} else {
							clickin = false;
						}
					}
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						if (event.getPointerCount() == 2) {
							float x = event.getX(0) - event.getX(1);
							float y = event.getY(0) - event.getY(1);
							float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离
							// System.out.println("value:" + value);
							if (baseValue == 0) {
								baseValue = value;
							} else {
								float scale = value / baseValue;// 当前两点间的距离除以手指落下时两点间的距离就是需要缩放的比例。
								// scale the image
								imageView.zoomTo(originalScale * scale, x
										+ event.getX(1), y + event.getY(1));

							}
						}
					}
					if (event.getAction() == MotionEvent.ACTION_UP) {
						int tapid = isInside(event, originalScale);
						if (event.getPointerCount() == 2) {
							clickin = false;
						} else if (tapid >= 0 && clickin) {
							clickin = true;
							if (listener != null)
								listener.onSingleTapConfirmed(tapid);
						} else {
							clickin = false;
						}
					}
				}
				return false;
			}

		});
	}

	private int isInside(MotionEvent event, float originalScale) {
		float x = event.getX();
		float y = event.getY();
		Matrix mMatrix = imageView.getImageMatrix();
		float[] values = new float[9];
		mMatrix.getValues(values);

		float left = values[2];
		float top = values[5];
		float imgX = (x - left) / originalScale / density * 3;
		float imgY = (y - top) / originalScale / density * 3;
		if (ImageDialog.img_id == 0) {
			for (Rect rect : mRect0) {
				if (rect.contains((int) imgX, (int) imgY)) {
					// Log.i("mMatrix", "+++++++=====");
					return 0;
				}
			}
		} else {
			int i = 0;
			for (Rect rect : mRect1) {
				i++;
				if (rect.contains((int) imgX, (int) imgY)) {
					Log.i("mMatrix", "+++++++=====");
					return i;
				}
			}
		}
		Log.i("mMatrix", "imageView:" + imgX + "  imageView:" + imgY);
		return -1;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		View view = MyGallery.this.getSelectedView().findViewById(
				R.id.shower_img);
		if (view instanceof ShowerImageView) {
			imageView = (ShowerImageView) view;
			// L.i("distanceX:"+distanceX+"   distanceY:"+distanceY);
			if (Math.abs(distanceX) > 5 || Math.abs(distanceY) > 5) {
				clickin = false;
			}
			float v[] = new float[9];
			Matrix m = imageView.getImageMatrix();
			m.getValues(v);
			// 图片实时的上下左右坐标
			float left, right;
			// 图片的实时宽，高
			float width, height;
			width = imageView.getScale() * imageView.getImageWidth();
			height = imageView.getScale() * imageView.getImageHeight();
			// 一下逻辑为移动图片和滑动gallery换屏的逻辑。如果没对整个框架了解的非常清晰，改动以下的代码前请三思！！！！！！
			if ((int) width <= ImageDialog.screenWidth
					&& (int) height <= ImageDialog.screenHeight)// 如果图片当前大小<屏幕大小，直接处理滑屏事件
			{
				try {
					super.onScroll(e1, e2, distanceX, distanceY);
				} catch (Exception e) {

				}
			} else {
				left = v[Matrix.MTRANS_X];
				right = left + width;
				Rect r = new Rect();
				imageView.getGlobalVisibleRect(r);

				if (Math.abs(distanceY) > Math.abs(distanceX)) {
					imageView.postTranslate(-distanceX, -distanceY);
				} else if (distanceX > 0)// 向左滑动
				{

					if (r.left > 0) {// 判断当前ImageView是否显示完全
						super.onScroll(e1, e2, distanceX, distanceY);
					} else if (right < ImageDialog.screenWidth) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				} else if (distanceX < 0)// 向右滑动
				{
					if (r.right < ImageDialog.screenWidth) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else if (left > 0) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				}

			}

		} else {
			super.onScroll(e1, e2, distanceX, distanceY);
		}
		return false;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			// Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			// Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureScanner.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			// 判断上下边界是否越界
			View view = MyGallery.this.getSelectedView().findViewById(
					R.id.shower_img);
			if (view instanceof ShowerImageView) {
				imageView = (ShowerImageView) view;
				float width = imageView.getScale() * imageView.getImageWidth();
				float height = imageView.getScale()
						* imageView.getImageHeight();
				if (imageView.getScale() < imageView.getScaleRate()) {
					imageView.zoomTo(imageView.getScaleRate(),
							ImageDialog.screenWidth / 2,
							ImageDialog.screenHeight / 2, 200f);
					imageView.layoutToCenter();
				}
				if ((int) width <= ImageDialog.screenWidth
						&& (int) height <= ImageDialog.screenHeight)// 如果图片当前大小<屏幕大小，判断边界
				{
					break;
				}
				float v[] = new float[9];
				Matrix m = imageView.getImageMatrix();
				m.getValues(v);
				float top = v[Matrix.MTRANS_Y];
				float bottom = top + height;
				if (top > 0) {
					imageView.postTranslateDur(-top, 200f);
				}
				// L.i("manga", "bottom:" + bottom);
				if (bottom < ImageDialog.screenHeight) {
					imageView.postTranslateDur(ImageDialog.screenHeight
							- bottom, 200f);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private class MySimpleGesture extends SimpleOnGestureListener {
		// 按两下的第二下Touch down时触发
		public boolean onDoubleTap(MotionEvent e) {
			View view = MyGallery.this.getSelectedView().findViewById(
					R.id.shower_img);
			if (view instanceof ShowerImageView) {
				imageView = (ShowerImageView) view;
				if (imageView.getScale() > imageView.getScaleRate()) {
					imageView.zoomTo(imageView.getScaleRate(),
							ImageDialog.screenWidth / 2,
							ImageDialog.screenHeight / 2, 200f);
					imageView.layoutToCenter();
				} else {
					imageView.zoomTo(5.0f, ImageDialog.screenWidth / 2,
							ImageDialog.screenHeight / 2, 200f);
					imageView.layoutToCenter();
				}

			} else {

			}
			// return super.onDoubleTap(e);
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			// if(listener!=null)
			// listener.onSingleTapConfirmed();
			return super.onSingleTapConfirmed(e);
		}
	}

	public void setSingleTapListener(singleTapListener listener) {
		this.listener = listener;
	}

	private singleTapListener listener;

	interface singleTapListener {
		void onSingleTapConfirmed(int tapId);
	}
}
