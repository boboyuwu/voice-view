package com.boboyuwu.voiceview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build.VERSION_CODES;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.ACTION_MOVE;

/**
 * Created by wubo on 2017/3/18.   音乐播放自定义view
 */

public class VoiceView extends View {
    private final String TAG="VoiceView";
    private int mSpeedLength;
    private Bitmap mPointBitmap;

    private float mXRound;
    private float mYRound;
    private int mVoiceItemWidth;

    private SparseArrayCompat voiceItems = new SparseArrayCompat();
    private int mVoiceItemHeight;
    private OnSpeedClickListener mOnSpeedClickListener;
    private int mMinimumVoiceHeight;
    private int mMinHeight;
    private int mMinWidth;
    private int mCurrVolum;
    private int mVoiceItemMarginLeft;
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private int mPointWidth;

    public VoiceView(Context context) {
        this(context, null);
    }

    public VoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /**
     * 初始化设置自定义控件需要的一系列用到的参数
     */
    @TargetApi(VERSION_CODES.KITKAT)
    private void initView() {
        //音量大小默认为1
        mSpeedLength = 8;
        //矩形弧度minimum
        mXRound = 30;
        mYRound = 30;

        //每个item需要的宽度
        mVoiceItemWidth = 10;
        mVoiceItemHeight = 20;
        mMinimumVoiceHeight = mVoiceItemHeight+mVoiceItemHeight*1/3;
        mPointBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.point));

        mPointWidth = mVoiceItemWidth*2;
        mCurrVolum = 4;

    }


    @TargetApi(VERSION_CODES.KITKAT)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int wResualt;
        int hResualt;

        if (widthMode == MeasureSpec.EXACTLY) {
            wResualt = widthSize;
        } else {
            wResualt = Math.min(widthSize, mMinWidth);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            hResualt = heightSize;
        } else {
            hResualt = Math.min(heightSize, mMinHeight);
        }
        setMeasuredDimension(wResualt, hResualt);
    }


    public void setVolumeAutomation(int speechLength) {
        mSpeedLength = speechLength;
    }


    public void setXRound(float xRound) {
        mXRound = xRound;
    }

    public void setYRound(float yRound) {
        mYRound = yRound;
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count=0;
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
            Paint backgroundPaint = new Paint();
            backgroundPaint.setColor(Color.parseColor("#a4c2f4"));
            canvas.drawRoundRect(0, 0, mMeasuredWidth, mMeasuredHeight, mXRound, mYRound, backgroundPaint);
            //音量栏所需要的上下左右宽距离
            //减去右边控件之后剩余的长度  再减去左右间距  然后平分音量数量+1
            mVoiceItemMarginLeft = (mMeasuredWidth - mMeasuredHeight - (mVoiceItemWidth * mSpeedLength))/8;
            int voiceLeft;
            int voiceRight;

            backgroundPaint.setColor(Color.WHITE);
            //设置初始的7个音量调节位置和大小   上下都是不变的变得是左右位置
            for (int i = 0; i < mCurrVolum; i++) {
                //想让第一个item 和最后一个距离左右mMarginLeft
                //定义每个Item音量的宽度AndRight距离
                voiceLeft = (i + 1) * mVoiceItemMarginLeft + i * mVoiceItemWidth;
                voiceRight = (i + 1) * mVoiceItemMarginLeft + (i +1)* mVoiceItemWidth ;
                canvas.drawRect(voiceLeft, mVoiceItemHeight, voiceRight, mMeasuredHeight - mVoiceItemHeight, backgroundPaint);
                //绘制point
                if(i==(mCurrVolum-1)){
                    int pointLeft = voiceLeft - (mPointWidth-mVoiceItemWidth) / 2;
                    RectF rectF = new RectF();
                    rectF.set(pointLeft,0,pointLeft+mPointWidth,mPointWidth);
                    canvas.drawBitmap(mPointBitmap,null,rectF,null);
                }

            }

             backgroundPaint.setColor(Color.parseColor("#a4c2f4"));
            for (int i = mCurrVolum; i < mSpeedLength; i++) {
                //定义每个Item音量的宽度
                //想让第一个item 和最后一个距离左右mMarginLeftAndRight距离
                voiceLeft = (i + 1) * mVoiceItemMarginLeft + i * mVoiceItemWidth;
                voiceRight = (i + 1) * mVoiceItemMarginLeft + i * mVoiceItemWidth + mVoiceItemWidth;
                canvas.drawRect(voiceLeft, mVoiceItemHeight, voiceRight, mMeasuredHeight - mVoiceItemHeight, backgroundPaint);
            }

    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                return true;

            case ACTION_MOVE:
                getScrollLength(event);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                getScrollLength(event);
                invalidate();
                return true;

        }
        return super.onTouchEvent(event);
    }




    private void getScrollLength(MotionEvent event) {
        int eventX= (int) (event.getX()+0.5f);
        int eventY= (int) event.getY();

        if(eventX>0&&eventY>0&&eventX<mMeasuredWidth-mMeasuredHeight&&eventY<mMeasuredHeight){
            int  marginLeftAndVoiceWidth= (mMeasuredWidth - mMeasuredHeight - mVoiceItemMarginLeft) / mSpeedLength;
            mCurrVolum=eventX/marginLeftAndVoiceWidth;
            if(mOnSpeedClickListener!=null){
                mOnSpeedClickListener.onSpeedChange(mCurrVolum);
            }
        }


    }


    public int getCurrSpeedLength(){
        return mCurrVolum;
    }

    //需要监听速率控件请设置这个监听
    public void setOnSpeedClickListener(OnSpeedClickListener onSpeedClickListener) {

        mOnSpeedClickListener = onSpeedClickListener;
    }

    public void setSpeedLength(int speedLength){
        mSpeedLength=speedLength;
        invalidate();
    }

}
