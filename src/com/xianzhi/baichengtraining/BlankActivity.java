package com.xianzhi.baichengtraining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class BlankActivity extends Activity {
	private LinearLayout back;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		final int image_id = getIntent().getIntExtra("img_id", 0);
		setContentView(R.layout.activity_white_page);
		back = (LinearLayout) findViewById(R.id.back);
		setImage(image_id, i);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (image_id == 5) {
					finish();
					return;
				}
				if (i == 1) {
					Intent intent = new Intent(getApplicationContext(),
							BlankActivity.class);
					intent.putExtra("img_id", image_id + 1);
					startActivity(intent);
					finish();
				} else if (i == 0) {
					i++;
					setImage(image_id, i);
				}
			}
		});
	}

	private void setImage(int image_id, int i) {
		switch (image_id) {
		case 0:
			if (i == 0) {
				back.setBackgroundResource(R.drawable.test_1_1);
			} else {
				back.setBackgroundResource(R.drawable.test_1_2);
			}
			break;
		case 1:
			if (i == 0) {
				back.setBackgroundResource(R.drawable.test_2_1);
			} else {
				back.setBackgroundResource(R.drawable.test_2_2);
			}
			break;
		case 2:
			if (i == 0) {
				back.setBackgroundResource(R.drawable.test_3_1);
			} else {
				back.setBackgroundResource(R.drawable.test_3_2);
			}
			break;
		case 3:
			if (i == 0) {
				back.setBackgroundResource(R.drawable.test_4_1);
			} else {
				back.setBackgroundResource(R.drawable.test_4_2);
			}
			break;
		case 4:
			if (i == 0) {
				back.setBackgroundResource(R.drawable.test_5_1);
			} else {
				back.setBackgroundResource(R.drawable.test_5_2);
			}
			break;
		case 5:
			back.setBackgroundResource(R.drawable.test1);
			break;
		default:
			break;
		}
	}
}
