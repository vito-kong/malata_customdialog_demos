package com.su.testcustomdialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ImageReader;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CustomDialogActivity extends Activity {
	private static final int PERMISSION_CODE = 2;
	private TextView textView;
	ListView device_list;
	ImageView imgScreen;
	MyDialog mdialog;
	Bitmap windowBitmap;
	LinearLayout linl;
	/** Called when the activity is first created. */

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	};
	private MediaProjectionManager mProjectionManager;
	private Object bitmap;
	private Object image;
	private Object mImageReader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final ImageView img = (ImageView) findViewById(R.id.rocket);
		imgScreen = (ImageView) findViewById(R.id.screen);
		final Bitmap bg = ScreenShot.getGlass(null, 123, 150);
		linl = (LinearLayout) findViewById(R.id.main);
		// imgScreen.setBackgroundResource(R.drawable.q4);
		View view = getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		/**
		 * 获取当前窗口快照，相当于截屏
		 */
		Bitmap bmp1 = view.getDrawingCache();
		Drawable draw = new BitmapDrawable(bmp1);
		imgScreen.setBackground(draw);
		imgScreen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				applyBlur(imgScreen);

			}
		});
		img.setBackgroundColor(getResources().getColor(android.R.color.black));
		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AnimationSet animationSet = new AnimationSet(true);
				animationSet.setInterpolator(new AccelerateInterpolator());
				TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -300);
				translateAnimation.setDuration(700);
				AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
				alphaAnimation.setDuration(700);
				animationSet.addAnimation(translateAnimation);
				animationSet.addAnimation(alphaAnimation);
				img.startAnimation(animationSet);

				img.setImageResource(R.drawable.ic_qs_turbokey_rocket_white);
			}
		});
		textView = (TextView) findViewById(R.id.textView11);
		LayoutInflater inflater1 = (LayoutInflater) CustomDialogActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout1 = inflater1.inflate(R.layout.dialog, null);
		device_list = (ListView) layout1.findViewById(R.id.device_list);
		device_list.setAdapter(new ArrayAdapter<String>(CustomDialogActivity.this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.i("xxxxxxxxx", System.currentTimeMillis()+"");
				long startMs = System.currentTimeMillis();
				Bitmap bmp1 = getWindowBitmap(v);
				float scaleFactor = 4;// 图片缩放比例；
				Matrix matrix = new Matrix();

				matrix.postScale(1/scaleFactor, 1/scaleFactor);
				Bitmap overlay = Bitmap.createBitmap(bmp1, 0, 540, 720, bmp1.getHeight() - 540, matrix, true);
				mdialog = new MyDialog(CustomDialogActivity.this, bmp1);
				mdialog.show();
				ByteArrayOutputStream bs = new ByteArrayOutputStream();
				overlay.compress(Bitmap.CompressFormat.JPEG, 100, bs);
				byte[] bitmapByte = bs.toByteArray();
				Intent i = new Intent();
				i.putExtra("bmpcache", bitmapByte);
				i.putExtra("dialog", mdialog);
				i.setAction("laozixinqingzhentemehao");
				CustomDialogActivity.this.sendBroadcast(i);
				Log.i("xxxxxxxxx", System.currentTimeMillis()-startMs +"");
			}
		});
	}

	private Bitmap getWindowBitmap(View v) {
		View view = v.getRootView();// getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		Bitmap bmpcache = view.getDrawingCache();
		int height = getOtherHeight();
		bmpcache = Bitmap.createBitmap(bmpcache, 0,
				height,bmpcache.getWidth(), bmpcache.getHeight() - height);
		
		return bmpcache;
	}
	
	/**
	 * 获取系统状态栏和软件标题栏，部分软件没有标题栏，看自己软件的配置
	 * @return
	 */
	private int getOtherHeight() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		int titleBarHeight = contentTop - statusBarHeight;
		return statusBarHeight + titleBarHeight;
	}

	private List<String> getData() {

		List<String> data = new ArrayList<String>();
		data.add("测试数据1");
		data.add("测试数据2");
		data.add("测试数据3");
		data.add("测试数据4");
		mHandler.sendEmptyMessage(1);
		return data;
	}

	private Bitmap applyBlur(ImageView img) {
		View view = getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		/**
		 * 获取当前窗口快照，相当于截屏
		 */
		Bitmap bmp1 = view.getDrawingCache();
		int height = getOtherHeight();
		// height = dip2px(this, 450);
		/**
		 * 除去状态栏和标题栏
		 */
		Bitmap bmp2 = Bitmap.createBitmap(bmp1, 0, height, bmp1.getWidth(), bmp1.getHeight() - height);

		return blur(bmp2, img);
	}

	private Bitmap blur(Bitmap bkg, View view) {
		long startMs = System.currentTimeMillis();
		float scaleFactor = 4;// 图片缩放比例；
		float radius = 1;// 模糊程度

		Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
				(int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(bkg, 0, 0, paint);

		overlay = FastBlur.doBlur(overlay, (int) radius, true);
//		view.setBackground(new BitmapDrawable(getResources(), overlay));
		/**
		 * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，
		 * 可是将scaleFactor设置大一些。
		 */
		Log.i("xxx", "blur time:" + (System.currentTimeMillis() - startMs));
		return overlay;
	}
	
	
	/**
	 * 剪切压缩tupian
	 * 从屏幕截图中剪切出view控件的bitmap
	 * @param bkg 获取到的屏幕截图
	 * @param view 需要截取的图的大小，这里是截取view的大小，也可以直接定义截取范围
	 * @return
	 */
	private Bitmap cutBitmap(Bitmap bkg, View view) {
		float scaleFactor = 4;// 图片缩放比例；
		Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
				(int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);//重新定义位置
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);//缩放
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(bkg, 0, 0, paint);
		return overlay;
	}

	private static Bitmap blur(Bitmap bkg) {
		long startMs = System.currentTimeMillis();
		float scaleFactor = 16;
		float radius = 2;
		Matrix matrix = new Matrix();
		Log.i("xxx", "blur bkg:" + bkg);

		matrix.postScale(1/scaleFactor, 1/scaleFactor);

		Bitmap bitmapBlur = Bitmap.createBitmap(bkg, 0, 740, 720, bkg.getHeight() - 740, matrix, true);

		bitmapBlur = FastBlur.doBlur(bitmapBlur, (int) radius, true);	
		Log.i("xxx", "blur time:" + (System.currentTimeMillis() - startMs));
		return bitmapBlur;
	}


	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		Log.i("ShortcutDialog", "scale=" + scale);
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return scale == 0 ? 0 : (int) (pxValue / scale + 0.5f);
	}

	private ComponentName getTopActivity() {
		final ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityInfo aInfo = null;
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		if (list.size() != 0) {
			RunningTaskInfo topRunningTask = list.get(0);
			return topRunningTask.topActivity;
		} else {
			return null;
		}
	}
}