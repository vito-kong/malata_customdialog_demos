package com.su.testcustomdialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义带圆角的ImageView
 * Created by ice on 14-8-6.
 */
public class CornersImageView extends ImageView{

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private final RectF mDrawableRect = new RectF();

    private float mDrawableRadius;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 1;

    public CornersImageView(Context context)
    {
        super(context);
    }

    public CornersImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CornersImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (mBitmap == null)
        {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 设置抗锯齿
        mBitmapPaint.setAntiAlias(true);
        // 使用位图平铺的渲染效果
        mBitmapPaint.setShader(mBitmapShader);

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        mDrawableRect.set(0, 0, mBitmapWidth, mBitmapHeight);
        // 获取图片展示的圆形半径
        mDrawableRadius = Math.min(mDrawableRect.width()/2, mDrawableRect.height()/2);

        invalidate();
    }

    @Override
    public void draw(Canvas canvas)
    {
        if (getDrawable() ==  null)
        {
            return;
        }
        canvas.drawCircle(getWidth()/2, getHeight()/2, mDrawableRadius, mBitmapPaint);
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        init();
    }


    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        init();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        init();
    }


    /**
     * 将Drawable转换成Bitmap对象
     * @param drawable
     * @return
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION,
                        BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                        BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
    
}
