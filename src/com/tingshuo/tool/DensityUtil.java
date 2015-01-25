package com.tingshuo.tool;
import java.lang.reflect.Field;

import android.content.Context;  
  
public class DensityUtil {  
	public static int LISTWIDTH=260;
	public static int MHEADHEIGHT=60;
	public static int MFOOTHEIGHT=50;
    /** 
     * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
   public static int sp2px(Context context, float spValue) {  
       final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
       return (int) (spValue * fontScale + 0.5f);  
   }
   public static int getStatusBarHeight(Context context){
       Class<?> c = null;
       Object obj = null;
       Field field = null;
       int x = 0, statusBarHeight = 0;
       try {
           c = Class.forName("com.android.internal.R$dimen");
           obj = c.newInstance();
           field = c.getField("status_bar_height");
           x = Integer.parseInt(field.get(obj).toString());
           statusBarHeight = context.getResources().getDimensionPixelSize(x);
       } catch (Exception e1) {
           e1.printStackTrace();
       } 
       return statusBarHeight;
   }
} 