package com.clan.findmiss.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/** 
 * 比较两张图片的相似度 
 * @author lanyj 
 * 
 */  
public class ImageUtil {
	    // 改变成二进制码  
	    public static String[][] getPX(Bitmap bi) {  
	        int[] rgb = new int[3];  
	  
	        int width = bi.getWidth();  
	        int height = bi.getHeight();  
	        String[][] list = new String[width][height]; 
	        for (int i = 0; i < width; i++) {  
	            for (int j = 0; j < height; j++) {  
	                int pixel = bi.getPixel(i, j);  
	                rgb[0] = (pixel & 0xff0000) >> 16;  
	                rgb[1] = (pixel & 0xff00) >> 8;  
	                rgb[2] = (pixel & 0xff);  
	                list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];    
	            }  
	        }  
	        return list;  
	  
	    }  
	      
	    public static String compareImage(Bitmap bi1, Bitmap bi2){    
	    	String result=null;
	        // 分析图片相似度 begin  
	        String[][] list1 = getPX(bi1);  
	        String[][] list2 = getPX(bi2);  
	        int xiangsi = 0;  
	        int busi = 0;  
	        int i = 0, j = 0;  
	        for (String[] strings : list1) {  
	            if ((i + 1) == list1.length) {  
	                continue;  
	            }  
	            for (int m=0; m<strings.length; m++) {  
	                try {  
	                    String[] value1 = list1[i][j].toString().split(",");  
	                    String[] value2 = list2[i][j].toString().split(",");  
	                    int k = 0;  
	                    for (int n=0; n<value2.length; n++) {  
	                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {  
	                            xiangsi++;  
	                        } else {  
	                            busi++;  
	                        }  
	                    }  
	                } catch (RuntimeException e) {  
	                    continue;  
	                }  
	                j++;  
	            }  
	            i++;  
	        }  
	  
	        list1 = getPX(bi1);  
	        list2 = getPX(bi2);  
	        i = 0;  
	        j = 0;  
	        for (String[] strings : list1) {  
	            if ((i + 1) == list1.length) {  
	                continue;  
	            }  
	            for (int m=0; m<strings.length; m++) {  
	                try {  
	                    String[] value1 = list1[i][j].toString().split(",");  
	                    String[] value2 = list2[i][j].toString().split(",");  
	                    int k = 0;  
	                    for (int n=0; n<value2.length; n++) {  
	                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {  
	                            xiangsi++;  
	                        } else {  
	                            busi++;  
	                        }  
	                    }  
	                } catch (RuntimeException e) {  
	                    continue;  
	                }  
	                j++;  
	            }  
	            i++;  
	        }  
	        String baifen = "";  
	        try {  
	            baifen = ((Double.parseDouble(xiangsi + "") / Double.parseDouble((busi + xiangsi) + "")) + "");  
	            baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);  
	        } catch (Exception e) {  
	            baifen = "0";  
	        }  
	        if (baifen.length() <= 0) {  
	            baifen = "0";  
	        }  
	        if(busi == 0){  
	            baifen="100";  
	        }  
	         result="相似像素数量：" + xiangsi + " 不相似像素数量：" + busi + " 相似率：" + Integer.parseInt(baifen) + "%";
	        Log.e("ImageUtil",result);  
	        return baifen;
	    }
	   

	    /**
	     * 处理图片 放大、缩小到合适位置
	     * 
	     * @param newWidth
	     * @param newHeight
	     * @param bitmap
	     * @return
	     */
	    public Bitmap resizeBitmap(float newWidth, float newHeight, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(newWidth / bitmap.getWidth(), newHeight / bitmap.getHeight());
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return newBitmap;
	    }
	    /**
		 * Bitmap 转 Drawable
		 * 
		 * @param bitmap
		 * @return
		 * @author lanyj
		 */
		public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}
}
