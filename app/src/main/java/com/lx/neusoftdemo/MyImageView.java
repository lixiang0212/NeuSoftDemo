package com.lx.neusoftdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {
    private Paint paint;

    public MyImageView(Context context) {
       this(context,null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable(); //获取设置的图片
        if(drawable!=null) {
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap(); //强转成Bitmap
            //新建一个位图和画布
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(output);
            //rectSr需要修建的图片大小,rectDest放置图片位置的大小
            final Rect rectSrc = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
            final Rect rectDest =new Rect(20,20,getWidth()-20,getHeight()-20);
            int x = bitmap.getWidth();
            canvas1.drawCircle(x / 2, x / 2, x / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));   //设置模式
            canvas1.drawBitmap(bitmap, rectSrc, rectSrc, paint);
            paint.reset();paint.setColor(Color.WHITE);paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(20);
            canvas.drawCircle(getWidth()/2, getWidth()/ 2,getWidth()/2-20,paint);
            paint.reset();//重置画笔
            canvas.drawBitmap(output,rectSrc,rectDest,paint);
        }else {
            super.onDraw(canvas);
        }
    }
}
