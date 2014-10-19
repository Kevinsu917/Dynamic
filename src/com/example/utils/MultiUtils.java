package com.example.utils;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.example.dynamicdemo.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @ClassName MultiUtils.java
 * @author KevinSu kevinsu917@126.com
 * @version 创建时间：2014-10-18 下午11:27:18
 * @Description: 多功能工具类
 */

public class MultiUtils {

	private static String CurrentUserName;

	private final static String[] nameArray = { "随便一个用户名", "用户名难取啊", "再来一个",
			"用户2号", "lastUser" };

	// 头像资源数组
	private final static int[] avaterArray = { R.drawable.avater_boy_1,
			R.drawable.avater_boy_2, R.drawable.avater_girl_1,
			R.drawable.avater_girl_2, R.drawable.avater_girl_3 };

	// 图片资源数组
	private final static int[] imageArray = { R.drawable.scarlett_1,
			R.drawable.scarlett_2, R.drawable.scarlett_3,
			R.drawable.scarlett_4, R.drawable.scarlett_5,
			R.drawable.scarlett_6, R.drawable.scarlett_7, R.drawable.scarlett_8 };

	
	public static String getRandomUserName() {

		String radomName = new String();
		radomName = nameArray[(int) (Math.random() * 5)];
		return radomName;
	}

	public static String getCurrentUserName() {
		if (TextUtils.isEmpty(CurrentUserName)) {
			CurrentUserName = getRandomUserName();
		}
		return CurrentUserName;
	}

	//弹出键盘
	public static void showInputMethod( Context context , View view )
	{
		InputMethodManager imm = ( InputMethodManager ) context
				.getSystemService( Context.INPUT_METHOD_SERVICE );
		if ( imm != null )
		{
			imm.showSoftInput( view , 0 );
		}
	}
	
	/** dip转px */
	public static int dipToPx(Context context, int dip) {
		float density = context.getResources().getDisplayMetrics().density;

		return (int) (dip * density + 0.5f);
	}

	/** 文件路径转Bitmap */
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;

		BitmapFactory.decodeFile(path, opts);

		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0f;
		float scaleHeight = 0f;

		if (width > w || height > h) {
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = (int) Math.max(scaleWidth, scaleHeight);

		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

	/** 根据用户名得出头像的资源文件 */
	public static int getAvaterResource(String userName) {
		int res = avaterArray[(int) (Math.abs(userName.hashCode()) % 5)];
		return res;
	}

	/** 根据需要图片的数量,随机取固定图片的资源id */
	public static ArrayList<Integer> getRandomImageResList(int imageCount) {
		ArrayList<Integer> imageList = new ArrayList<Integer>();
		for (int i = 0; i < imageCount; i++) {
			int index = (int) (Math.random() * 8);
			imageList.add(imageArray[index]);
		}
		return imageList;
	}

	/** 转化时间格式 */
	public static String convertTime(long time) {
		String format = "M-d k:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}
}
