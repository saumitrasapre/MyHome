package com.example.myhome.Helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class RectOverlay extends  GraphicOverlay.Graphic {

    private int mRectColor= Color.parseColor("#0691B8");
    private float mStrokeWidth=4.0f;
    private Paint mRectPaint;
    private GraphicOverlay graphicOverlay;
    private Rect rect;

    public RectOverlay(GraphicOverlay overlay,Rect rect) {
        super(overlay);

        mRectPaint= new Paint();
        mRectPaint.setColor(mRectColor);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(mStrokeWidth);

        this.graphicOverlay=overlay;
        this.rect=rect;

        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {

        RectF rectF=new RectF(rect);
        rectF.left=translateX(rectF.left);
        rectF.right=translateX(rectF.right);
        rectF.top=translateX(rectF.top);
        rectF.bottom=translateX(rectF.bottom);

        canvas.drawRect(rectF,mRectPaint);

    }
}

