package com.jovial.floatingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;


/**
 * Created by:[ Jovial ]
 * Created date:[ 2018/1/22 0022]
 * About Class:[ 制作一个类似ios可以左右悬浮的球 ]
 */

public class FloatingView extends AppCompatButton {

    /** 画笔  */
    private Paint mPaint , mPaint1 , mPaint2 , mRectPaint ;

    /** 控件 宽高  中心同心圆是根据宽高比例算出来的半径 */
    private int width , height;

    /** 控件的初始位置坐标  */
    private float x , y ;

    /** 同心圆  圆心位置  */
    private float rx , ry;

    /** 同心圆  半径  */
    private int c1,c2,c3;

    /** 控件的状态  移动&焦点（0）  静止状态（-1） 点击打开状态（2） */
    public final static int STATE_MOVE = 0;
    public final static int STATE_NOMOR = -1;
    public final static int STATE_OPEN = 2;
    private static int STATE = STATE_NOMOR;

    /** 屏幕宽高  */
    private int screenWidth;
    private int screenHeight;

    private WindowManager mWindowManager;
    /** 悬浮按钮  */
    private LayoutParams mParams;
    /** 打开状态的界面布局 */
    private LayoutParams mParamsOpen;

    public FloatingView(Context context){
        this(context, null , 0);
    }
    public FloatingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public FloatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray a = context.obtainStyledAttributes(attrs , R.styleable.CircleView);
//        mColor = a.getColor(R.styleable.CircleView_circle_color,Color.RED);
//        a.recycle();
        init();
        initPaint();
    }

    /**
     * 初始化 参数
     */
    private void init(){
        STATE = STATE_NOMOR;

        mWindowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        mParams = new LayoutParams(100,100,0,0, PixelFormat.TRANSPARENT);
        mParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mParams.width = 100;
        mParams.height = 100;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = 0;
        mParams.y = 300;
        mWindowManager.addView(this , mParams);

        screenWidth = mWindowManager.getDefaultDisplay().getWidth();
        screenHeight = mWindowManager.getDefaultDisplay().getHeight();
    }

    /**
     * 初始化状态
     */
    public void setState(int state){
        STATE = state;
    }

    /**
     * 状态类型：（-1）
     * 控件透明度增加，属于无任何状态的情况
     */
    public void changeState2_NOMOR(){
        mPaint.setAlpha(70);
        mPaint1.setAlpha(50);
        mPaint2.setAlpha(40);
        mRectPaint.setAlpha(60);
        invalidate();
    }

    /**
     * 状态类型（1）
     * 移动或者获取到焦点，透明度降低，是控件看起来颜色更亮
     */
    public void changeState2_MOVE(){
        mPaint.setAlpha(90);
        mPaint1.setAlpha(70);
        mPaint2.setAlpha(60);
        mRectPaint.setAlpha(100);
        invalidate();
    }

    /**
     * 类型状态（2）
     * 当点击打开时，调用此方法重新绘制控件
     */
    private void changeSate2_OPEN(){
    }
    /**
     * 初始化画笔
     */
    public void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAlpha(70);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mPaint1 = new Paint();
        mPaint1.setColor(Color.WHITE);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);
        mPaint1.setAlpha(50);
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.WHITE);
        mPaint2.setStrokeCap(Paint.Cap.ROUND);
        mPaint2.setAlpha(40);
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.FILL);

        mRectPaint = new Paint();
        mRectPaint.setColor(Color.BLACK);
        mRectPaint.setStrokeCap(Paint.Cap.ROUND);
        mRectPaint.setAntiAlias(true);
        mRectPaint.setAlpha(60);
        mRectPaint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();
        x = getX();
        y = getY();
        initNumber();
    }

    /**
     * 计算控件绘制的参数
     */
    public void initNumber(){
        rx = x + width/2;
        ry = y + height/2;

        c1 = width/5+14;
        c2 = width/5+8;
        c3 = width/5;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (STATE){
            case STATE_MOVE:
                changeState2_MOVE();
                drawD(canvas);
                break;
            case STATE_NOMOR:
                changeState2_NOMOR();
                drawD(canvas);
                break;
            case STATE_OPEN:
                changeSate2_OPEN();
                drawOpen(canvas);
                break;
            default:
                break;
        }
    }

    /**
     * 绘制未点击状态的控件形态
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawD(Canvas canvas){
        canvas.drawRoundRect(x,y,width,height,20f,20f,mRectPaint);
        canvas.drawCircle(rx,ry,c1,mPaint1);
        canvas.drawCircle(rx,ry,c2,mPaint2);
        canvas.drawCircle(rx,ry,c3,mPaint);
    }
    /**
     * 绘制点击打开状态（2）的效果
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawOpen(Canvas canvas){
        canvas.drawRoundRect(x,y,screenWidth/2,screenHeight/2,20f,20f,mRectPaint);
    }
    /**
     * 计算手指停留的位置，让控件贴边
     * @param rawX 手指停留位置相对于屏幕的x轴坐标
     * @param rawY 手指停留位置相对于屏幕的y轴坐标
     */
    public void cal(float rawX , float rawY){
        //左上   &&    右下（因为两者比例都是大于1/2所以可以直接用比例来算）
        float X = rawX/screenWidth;
        float Y = rawY/screenHeight;

        //右上
        float R = screenWidth - rawX;
        //左下
        float B = screenHeight - rawY;

        if(X <= 0.5 && Y <= 0.5){//左上
            if(rawX - rawY >0){
                setParamsXY((int)rawX , 0);
            }else{
                setParamsXY(0 , (int)rawY);
            }

        }else if(X > 0.5 && Y > 0.5){//右下
            if(X - Y > 0){
                setParamsXY(screenWidth , (int)rawY);
            }else{
                setParamsXY((int)rawX , screenHeight);
            }

        }else if(X > 0.5 && Y <=0.5){//右上
            if(R - rawY > 0){
                setParamsXY((int)rawX , 0);
            }else{
                setParamsXY(screenWidth , (int)rawY);
            }
        }else{//左下
            if(rawX - B > 0){
                setParamsXY((int)rawX , screenHeight);
            }else{
                setParamsXY(0 , (int)rawY);
            }
        }
    }

    /**
     * 设置控件相对于屏幕位置坐标
     * @param x x轴坐标
     * @param y y轴坐标
     */
    public void setParamsXY(int x,int y){
        mParams.x = x;
        mParams.y = y;
    }

    /**
     * 更新控件位置，此处调用WindowManager的方法来更新
     */
    public void updateViewLayout(){
        mWindowManager.updateViewLayout(this , mParams);
    }

    /**
     * 添加onTouch监听后，手指抬起时需要调用的方法
     */
    public void onActionUp(float rawX , float rawY){
        setState(FloatingView.STATE_NOMOR);
        //计算控件距离上左下右边框的距离，让其贴边
        cal(rawX , rawY);
        updateViewLayout();
    }
    /**
     * 添加onTouch监听后，手指移动时需要调用的方法
     */
    public void onActionMove(float rawX , float rawY){
        setParamsXY((int)rawX , (int)rawY);
        updateViewLayout();
    }
}
