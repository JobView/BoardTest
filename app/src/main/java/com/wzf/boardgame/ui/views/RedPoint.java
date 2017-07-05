package com.wzf.boardgame.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhenfei on 2015/12/28.
 * 小红点
 */
public class RedPoint extends View{
    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 红点半径
     */
    private float radius = 10.0f;

    //在java代码中的new中调用
    public RedPoint(Context context) {
        super(context);
    }

    //在xml中定义的调用
    public RedPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    //有自定义的样式的时候调用
    public RedPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    //计算控件的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)(radius*2), (int)(radius*2));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
    }
}
