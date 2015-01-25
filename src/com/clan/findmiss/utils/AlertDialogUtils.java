package com.clan.findmiss.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

/**
 * 确认提示框
 * @author lanyj
 *
 */
public class AlertDialogUtils {
	
	/**
	 * 弹出确认提示框
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param confirm
	 * @param cancel
	 * @param listener
	 * @author lanyj
	 * @data 
	 * 
	 */
	public static Builder show(Context context, String title, String message,
			String confirm, String cancel, final OnDialogListener listener) {
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setTitle(isNull(title));
		ab.setMessage(isNull(message));
		if (null != isNull(confirm)) {
			ab.setPositiveButton(confirm,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							listener.onConfirm();
						}
					});
		}
		if (null != isNull(cancel)) {
			ab.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					listener.onCancel();
				}
			});
		}
		ab.setOnCancelListener(new OnCancelListener(){//除确定取消按钮外，其他照成dialog消失将会执行的方法

			@Override
			public void onCancel(DialogInterface dialog) {
				listener.onCancel();
			}
		});
		ab.create().show();
		return ab;
	}
	
    /**
     * 判断字符串是否为空
     * 
     * @param value
     * @return
     */
	private static String isNull(String value) {
		return value == null || value.equals("") ? null : value;
	}

	/**
	 * Dialog按钮回调接口
	 * 
	 * @author lanyj
	 *
	 */
	public interface OnDialogListener {

		public void onConfirm();//确定

		public void onCancel();//取消

	}

}
