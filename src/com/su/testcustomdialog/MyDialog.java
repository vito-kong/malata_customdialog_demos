package com.su.testcustomdialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyDialog extends Dialog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bitmap getWindowBitmap() {
		return windowBitmap;
	}

	public void setWindowBitmap(Bitmap windowBitmap) {
		this.windowBitmap = windowBitmap;
	}

	GridView gridView1;
	Context context;
	Button sure;
	TextView textView;
	Window window;
	ListView listView;
	private Bitmap windowBitmap;
	GridViewAdapter adapter;
	private static List<String> data;
	LinearLayout linL;
	Bitmap bmpcache;
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals("laozixinqingzhentemehao")) {
				byte[] byteTemp = intent.getByteArrayExtra("bmpcache");
				bmpcache = BitmapFactory.decodeByteArray(byteTemp, 0, byteTemp.length);
				linL.setBackground(new BitmapDrawable(context.getResources(), bmpcache));
			}
		}
	};

	private Handler switchHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				dismiss();
				break;
			case 2:
				int position = msg.arg1;
				if (adapter != null) {
					data.remove(position);
					adapter.notifyDataSetChanged();
					// gridView1.updateViewLayout(view, params);
					FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(300 * data.size(),
							FrameLayout.LayoutParams.WRAP_CONTENT);
					gridView1.setLayoutParams(params2);
				}
				break;
			}
		}
	};

	public MyDialog(Context context, Bitmap bitmap) {
		super(context, R.style.ShortcutDialog);
		this.context = context;
		this.windowBitmap = bitmap;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		IntentFilter control_filter = new IntentFilter();
		control_filter.addAction("laozixinqingzhentemehao");
		context.registerReceiver(receiver, control_filter);
		data = new ArrayList<String>();
		addData();
		window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homelong_apps_dialog_test);

		window.setType(WindowManager.LayoutParams.TYPE_APPLICATION);
		window.setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		setCanceledOnTouchOutside(true);
		final WindowManager.LayoutParams params = window.getAttributes();
		// params.width =
		// window.getWindowManager().getDefaultDisplay().getWidth();;
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		Log.i("TEST", "params.width = " + params.width);
		window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		window.setAttributes(params);
		window.setFlags(0, WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		linL = (LinearLayout) findViewById(R.id.homelong_dialog_test);
		// applyBlur(linL);
		gridView1 = (GridView) findViewById(R.id.recentapp_container_recyclerview);
		FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(300 * data.size(),
				FrameLayout.LayoutParams.WRAP_CONTENT);
		gridView1.setLayoutParams(params1);
		gridView1.setColumnWidth(300);
		gridView1.setHorizontalSpacing(10);
		gridView1.setStretchMode(GridView.NO_STRETCH);
		gridView1.setNumColumns(data.size());
		// applyBlur(linL);
		adapter = new GridViewAdapter(context, data, switchHandler);

		gridView1.setAdapter(adapter);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(200);
	}

	private List<String> addData() {

		data.add("1");
		data.add("2");
		data.add("3");
		data.add("4");

		return data;
	}

	private void applyBlur(View img) {

		int height = getOtherHeight();
		/**
		 * 除去状态栏和标题栏
		 */
		// Bitmap bmp2 = Bitmap.createBitmap(bmp1, 0, height,bmp1.getWidth(),
		// bmp1.getHeight() - height);
		// Bitmap bmp2 = Bitmap.createBitmap(720, 1280,
		// Bitmap.Config.ARGB_8888);//获取到的背景为透明空？？？

		blur2(windowBitmap, (LinearLayout) img);
	}

	// private void blur(Bitmap bkg,View view) {
	// long startMs = System.currentTimeMillis();
	// float scaleFactor = 4;//图片缩放比例；
	// float radius = 8;//模糊程度
	//
	// Bitmap overlay = Bitmap.createBitmap(
	// (int) (720 / scaleFactor),
	// (int) (1280 / scaleFactor),
	// Bitmap.Config.ARGB_8888);
	// Canvas canvas = new Canvas(overlay);
	// canvas.translate(0, -841/ scaleFactor);
	// canvas.scale(1 / scaleFactor, 1 / scaleFactor);
	// Paint paint = new Paint();
	// paint.setFlags(Paint.FILTER_BITMAP_FLAG);
	// canvas.drawBitmap(bkg, 0, 0, paint);
	//
	//
	// overlay = FastBlur.doBlur(overlay, (int) radius, true);
	// //view.setBackground(new BitmapDrawable(context.getResources(),
	// overlay));
	// window.setBackgroundDrawable(new BitmapDrawable(context.getResources(),
	// overlay));
	// /**
	// *
	// 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。
	// */
	// Log.i("xxx", "blur time:" + (System.currentTimeMillis() - startMs));
	// }

	private void blur(Bitmap bkg, LinearLayout view) {
		long startMs = System.currentTimeMillis();
		float scaleFactor = 6;// 图片缩放比例；
		float radius = 2;// 模糊程度

		Bitmap overlay = Bitmap.createBitmap((int) (720 / scaleFactor), (int) (1280 / scaleFactor),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(0, -800);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(windowBitmap, 0, 0, paint);

		Bitmap test = Bitmap.createBitmap(windowBitmap, 0, 740, 720, windowBitmap.getHeight() - 740);

		test = FastBlur.doBlur(test, (int) radius, true);
		linL.setBackground(new BitmapDrawable(context.getResources(), bmpcache));
		/**
		 * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，
		 * 可是将scaleFactor设置大一些。
		 */
		Log.i("xxx", "blur time:" + (System.currentTimeMillis() - startMs));
	}

	private void blur2(Bitmap bkg, LinearLayout view) {
		long startMs = System.currentTimeMillis();
		float scaleFactor = 3;
		float radius = 1;

		Matrix matrix = new Matrix();
		matrix.postScale(1 / scaleFactor, 1 / scaleFactor);

		Bitmap test = Bitmap.createBitmap(windowBitmap, 0, 740, 720, windowBitmap.getHeight() - 740, matrix, true);

		test = FastBlur.doBlur(test, (int) radius, true);
		// linL.setBackground(new BitmapDrawable(context.getResources(),
		// bmpcache));
		Log.i("xxx", "blur time:" + (System.currentTimeMillis() - startMs));
	}

	/**
	 * 获取系统状态栏和软件标题栏，部分软件没有标题栏，看自己软件的配置；
	 * 
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

}
