package com.tingshuo.tool.view.imageshower;

/**  
 * GalleryAdapter.java
 * @version 1.0
 * @author Haven
 * @createTime 2011-12-9 涓����05:04:34
 */

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import com.tingshuo.tool.L;
import com.tingshuo.web.http.HttpJsonTool;
import com.tingshuo.web.img.ImageDownloader;
import com.tingshuo.web.img.fetcher.ImageCache;
import com.xianzhi.baichengtraining.R;

public class GalleryAdapter extends BaseAdapter {

	private Context context;
	private List<Integer> images;
	// ImageLoaders imageLoaders;
	private LayoutInflater mInflater;
	private View parentView;
	private Point mPoint = new Point(0, 0);
	//private ImageCache mImageCache;
	//private ImageDownloader downloader;
	public GalleryAdapter(Context context, List<Integer> imgs, View parentView) {
		this.context = context;
		this.images = imgs;
		this.parentView = parentView;
		//mImageCache=new ImageCache(context,"tingshuo");
		//downloader=new ImageDownloader(context, mImageCache);
		
		mInflater = LayoutInflater.from(context);
	}

	public void clearImg() {
		// imageLoaders.mapBitmaps.clear();
		System.gc();
	}

	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return images.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		ShowerImageView view;
		View cell=mInflater.inflate(R.layout.galler_cell_pic, null);
		try {
			int resource = images.get(position);
//			view = new ShowerImageView(context, 0, 0);
			cell.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			
			view=(ShowerImageView) cell.findViewById(R.id.shower_img);
			view.setTag(resource);
			Bitmap bit = BitmapFactory.decodeResource(
					context.getResources(),resource);
			view.setImageBitmap(bit);
			return cell;
		} catch (OutOfMemoryError e) {
			//mImageCache.clearMemorCaches();
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
		}
		return cell;
	}

}
