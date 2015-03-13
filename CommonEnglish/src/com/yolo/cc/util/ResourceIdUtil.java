package com.yolo.cc.util;

import java.lang.reflect.Field;

import com.yolo.cc.view.R;



public class ResourceIdUtil {

	/**
	 * 获取图片的id
	 * 
	 * @param imageName
	 *            图片的名字
	 * @return
	 */
	public static int getImageResourceId(String imageName) {
		R.drawable drawable = new R.drawable();
		int resId = 0;
		try {
			Field field = R.drawable.class.getField(imageName);
			resId = field.getInt(drawable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resId;
	}

	/**
	 * 获取声音的id
	 * 
	 * @param audioName 声音的名字
	 * @return
	 */
	public static int getAudioResourceId(String audioName) {
		R.raw raw = new R.raw();
		int resId = 0;
		try {
			Field field = R.raw.class.getField(audioName);
			resId = field.getInt(raw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resId;
	}
}
