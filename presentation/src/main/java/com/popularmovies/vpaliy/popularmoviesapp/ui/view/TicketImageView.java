package com.popularmovies.vpaliy.popularmoviesapp.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;

import com.popularmovies.vpaliy.popularmoviesapp.R;

public class TicketImageView extends RoundedImageView{

    private float circleGap=10;
    private Paint paint;
    private float radius=10 ;
    private int circleNum=2;
    private float remain;
    private Paint eraser;


    public TicketImageView (Context context) {
        this(context,null,0);
    }

    public TicketImageView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TicketImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if(attrs!=null){
            TypedArray array = getContext().obtainStyledAttributes(attrs,
                    R.styleable.TicketImageView);
            final int N = array.getIndexCount();
            for (int i = 0; i < N; ++i) {
                int attr = array.getIndex(i);
                if(attr==R.styleable.TicketImageView_ticket_radius) {
                    radius = array.getFloat(R.styleable.TicketImageView_ticket_radius, 18);
                }else if(attr==R.styleable.TicketImageView_ticket_gap){
                    circleGap=array.getFloat(R.styleable.TicketImageView_ticket_gap,20);
                }
            }
            array.recycle();
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        eraser=new Paint();
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (remain==0){
            remain = (int)(w-circleGap)%(2*radius+circleGap);
        }
        circleNum = (int) ((w-circleGap)/(2*radius+circleGap));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayerAlpha(0, 0, canvas.getWidth(), canvas.getHeight(), 255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++){
            float x = circleGap+radius+remain/2+((circleGap+radius*2)*i);
            canvas.drawCircle(x, 0, radius, paint);
            canvas.drawCircle(x, getHeight(), radius, paint);
        }
    }
}
