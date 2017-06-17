package com.popularmovies.vpaliy.popularmoviesapp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.popularmovies.vpaliy.popularmoviesapp.R;

public class RatingsView extends View {

    private static final String STATE_PARENT = "parent";
    private static final String STATE_ANGLE = "angle";

    private Paint colorWheelPaint;
    private float wheelStrokeWidth;
    private RectF colorWheelRectangle = new RectF();
    private float translationOffset;
    private float angle;
    private int max = 100;
    private float endWheel=360;
    private Paint mArcColor;
    private int wheelColor;
    private int unactiveWheelColor;
    private float position = -1;
    private float arcFinishRadians = 360;
    private int startArc = 0;
    private RectF colorCenterHaloRectangle = new RectF();
    private OnChangedListener listener;

    public RatingsView(Context context) {
        super(context);
        init(null, 0);
    }

    public RatingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RatingsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.Ratings);
        initAttributes(a);

        //a.recycle();

        colorWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorWheelPaint.setColor(unactiveWheelColor);
        colorWheelPaint.setStyle(Style.STROKE);
        colorWheelPaint.setStrokeWidth(wheelStrokeWidth);

        mArcColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcColor.setColor(wheelColor);
        mArcColor.setStyle(Style.STROKE);
        mArcColor.setStrokeWidth(wheelStrokeWidth);

        arcFinishRadians = calculateAngleFromText(position) - 90;

        if (arcFinishRadians > endWheel)
            arcFinishRadians = endWheel;
        angle = calculateAngleFromRadians(arcFinishRadians > endWheel ? endWheel
                : arcFinishRadians);
        invalidate();
    }

    public void setWheelStrokeWidth(int wheelStrokeWidth) {
        this.wheelStrokeWidth = wheelStrokeWidth;
    }

    private void setText(float angleValue) {
        if(listener!=null) listener.onChanged(angleValue);
    }

    private void initAttributes(TypedArray array) {
        final int N = array.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = array.getIndex(i);
            if(attr==R.styleable.Ratings_ratings_wheel_active_color){
                this.wheelColor=array.getColor(R.styleable.Ratings_ratings_wheel_active_color,Color.BLACK);
            }else if(attr==R.styleable.Ratings_ratings_wheel_unactive_color){
                this.unactiveWheelColor=array.getColor(R.styleable.Ratings_ratings_wheel_unactive_color,Color.BLACK);
            }else if(attr==R.styleable.Ratings_ratings_wheel_size){
                this.wheelStrokeWidth=array.getDimension(R.styleable.Ratings_ratings_wheel_size,-1);
            }else if(attr==R.styleable.Ratings_ratings_max){
                this.max=array.getInteger(R.styleable.Ratings_ratings_max,-1);
            }else if(attr==R.styleable.Ratings_ratings_init_position){
                this.position =array.getFloat(R.styleable.Ratings_ratings_init_position,-1);
            }
        }
        array.recycle();
        setText(position);
    }

    public void setWheelColor(int wheelColor) {
        this.wheelColor = wheelColor;
    }

    public void setUnactiveWheelColor(int unactiveWheelColor) {
        this.unactiveWheelColor = unactiveWheelColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(translationOffset, translationOffset);

        canvas.drawArc(colorWheelRectangle, 0, endWheel
                - (startArc), false, colorWheelPaint);
        canvas.drawArc(colorWheelRectangle, startArc + 270,
                (arcFinishRadians) > (endWheel) ? endWheel - (startArc)
                        : arcFinishRadians - startArc, false, mArcColor);
    }

    public void setUnactiveColor(int color){
        this.unactiveWheelColor=color;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);

        translationOffset = min * 0.5f;
        final float colorWheelRadius = translationOffset-wheelStrokeWidth;

        colorWheelRectangle.set(-colorWheelRadius, -colorWheelRadius,
                colorWheelRadius, colorWheelRadius);

        colorCenterHaloRectangle.set(-colorWheelRadius / 2,
                -colorWheelRadius / 2, colorWheelRadius / 2,
                colorWheelRadius / 2);
    }

    private float calculateValueFromAngle(float angle) {
        float m = angle - startArc;
        float f = (endWheel - startArc) / m;
        return  (max / f);
    }

    private float calculateAngleFromText(double position) {
        if (position == 0 || position >= max)
            return 90;
        float f =  (float)(max/ position);
        float f_r = 360 / f;
        return f_r + 90;
    }

    private int calculateRadiansFromAngle(float angle) {
        float unit = (float) (angle / (2 * Math.PI));
        if (unit < 0) {
            unit += 1;
        }
        int radians = (int) ((unit * 360) - ((360 / 4) * 3));
        if (radians < 0)
            radians += 360;
        return radians;
    }

    private float calculateAngleFromRadians(double radians) {
        return (float) (((radians + 270) * (2 * Math.PI)) / 360);
    }

    public void setValue(float newValue) {
        if (newValue == 0) {
            arcFinishRadians = startArc;
        } else if (newValue == this.max) {
            arcFinishRadians = endWheel;
        } else {
            float newAngle = (float) (360.0 * (newValue / max));
            arcFinishRadians = (int) calculateAngleFromRadians(calculateRadiansFromAngle(newAngle)) + 1;
        }

        angle = calculateAngleFromRadians(arcFinishRadians);
        setText(newValue);
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Bundle state = new Bundle();
        state.putParcelable(STATE_PARENT, superState);
        state.putFloat(STATE_ANGLE, angle);
        return state;
    }

    public void setListener(OnChangedListener listener) {
        this.listener = listener;
        setText(calculateValueFromAngle(arcFinishRadians));
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedState = (Bundle) state;

        Parcelable superState = savedState.getParcelable(STATE_PARENT);
        super.onRestoreInstanceState(superState);

        angle = savedState.getFloat(STATE_ANGLE);
        arcFinishRadians = calculateRadiansFromAngle(angle);
        setText(calculateValueFromAngle(arcFinishRadians));
    }

    interface OnChangedListener{
        void onChanged(float position);
    }
}