package com.example.administrator.baidumusic.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/10/29.
 */

public class CustomPoint extends View {
    private boolean isSelected = false;


    public void setSelected(boolean selected) {
        this.isSelected = selected;
        invalidate();
    }

    public CustomPoint(Context context) {
        super(context);
    }

    public CustomPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);// 开启抗锯齿
        if(!isSelected){
            Log.d("CustomPoint", "蓝色");
            paint.setColor(0xff838B8B);
        } else {
            Log.d("CustomPoint", "黑色");
            paint.setColor(0xff000000);
        }
        canvas.drawCircle(10,10,10,paint);
    }
}
