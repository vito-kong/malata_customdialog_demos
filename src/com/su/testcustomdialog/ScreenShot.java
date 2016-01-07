package com.su.testcustomdialog;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Size;
import android.view.Display;
import android.view.View;

public class ScreenShot {
	public Bitmap myShot(Activity activity) {
		// 获取windows中最顶层的view
		View view = activity.getWindow().getDecorView();
		view.buildDrawingCache();

		// 获取状态栏高度
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		Display display = activity.getWindowManager().getDefaultDisplay();

		// 获取屏幕宽和高
		int widths = display.getWidth();
		int heights = display.getHeight();

		// 允许当前窗口保存缓存信息
		view.setDrawingCacheEnabled(true);

		// 去掉状态栏
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeights, widths,
				heights - statusBarHeights);

		// 销毁缓存信息
		view.destroyDrawingCache();

		return bmp;
	}

	/**
	 * 获取一块半透明背景 *
	 * 
	 * @param rect
	 *            背景大小 *
	 * @param color
	 *            背景颜色 *
	 * @param aplha
	 *            背景透明度
	 */
	public static Bitmap getGlass(Size rect, int color, int aplha) {
		Bitmap nb = Bitmap.createBitmap(190, 200, Config.ARGB_8888);
		Canvas canvas = new Canvas(nb);
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(color);
		paint.setAlpha(aplha);
		canvas.drawRect(0, 0, 200, 200, paint);
		return nb;
	}
}
