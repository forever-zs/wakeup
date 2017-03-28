package com.wakeup.forever.wakeup.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.utils.LogUtil;

/**
 * Created by forever on 2016/11/24.
 */

public class LotteryDrawView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Thread thread;
    private boolean isRunning;
    private boolean isShouldStop=true;
    private boolean isDestroy=false;

    private String textDesc[]={"鼠标","抱枕","水杯","热水袋","书签","谢谢参与"};
    private int arcColors[]={R.color.red, R.color.bitRed,R.color.red,R.color.bitRed,R.color.red,R.color.bitRed};
    private int imgSource[]={R.drawable.mouse,R.drawable.bolster,R.drawable.cup,R.drawable.bottle,R.drawable.bookmark,R.drawable.sorry};
    private Bitmap imgBitmaps[]=new Bitmap[6];
    //绘制文字的画笔
    private Paint textPaint;
    //绘制扇形分区的画笔
    private Paint arcPaint;
    //指针旋转的速度
    private float speed=0;
    //圆盘范围
    private RectF range=new RectF();
    //圆盘半径
    private int radius;
    private boolean isEnd;
    private float startAngle=0;
    private int sweepAngle;
    //转盘中心位置
    private int center;
    //转盘的padding
    private int padding;
    //字体大小
    private float textSize= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics());
    // 背景图片
    private Bitmap background= BitmapFactory.decodeResource(getResources(), R.drawable.bg_lottery);


    public LotteryDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);

        //获取屏幕焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    public LotteryDrawView(Context context) {
        this(context,null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=Math.min(getMeasuredWidth(),getMeasuredHeight());
        padding=getPaddingLeft();
        radius=(width-padding*2)/2;
        center=width/2;
        setMeasuredDimension(width,width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtil.e("surfaceCreated");
        //设置转盘画笔属性
        arcPaint=new Paint();
        arcPaint.setAntiAlias(true); //设置抗锯齿
        arcPaint.setDither(true);   //设置防抖动
        //设置绘制文本的画笔
        textPaint=new Paint();
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        textPaint.setTextSize(textSize);
        //初始化背景矩形区域
        range=new RectF(padding,padding,radius*2+padding,radius*2+padding);
        thread=new Thread(this);
        thread.start();
        isRunning=false;

        for(int i=0;i<textDesc.length;i++){
            imgBitmaps[i]=BitmapFactory.decodeResource(getResources(),imgSource[i]);
        }

        sweepAngle=360/textDesc.length;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning=false;
        //surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run() {
        while(!isDestroy){
            long start =System.currentTimeMillis();
            draw();
            long end=System.currentTimeMillis();
            if((end-start)<50){
                try {
                    Thread.sleep(50-(end-start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void draw() {
        try {
            canvas=surfaceHolder.lockCanvas();
            if(canvas!=null){
                //绘制背景
                drawBg();
                drawArc();
                drawPoint();
                if(isShouldStop){
                    speed-=1;
                }
                if(speed<=0){
                    isShouldStop=false;
                    isRunning=false;
                    speed=0;
                }
                startAngle+=speed;
            }
        }
        catch (Exception e){

        }

        finally {
            if(canvas!=null){
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }



    }

    private void drawPoint() {
        Bitmap point=BitmapFactory.decodeResource(getResources(),R.drawable.point);
        int pointWidth=150;

        Rect pointRange=new Rect((center-pointWidth/2),(center-pointWidth/2),(center+pointWidth/2),(center+pointWidth/2));
        canvas.drawBitmap(point,null,pointRange,arcPaint);
    }

    private void drawText(String text,float startAngle,float sweepAngle) {
        Path path=new Path();
        path.addArc(range,startAngle,sweepAngle);
        float hOffset= (float) (Math.PI*radius*2/textDesc.length/2-textPaint.measureText(text)/2);
        float vOffset=radius/6;
        canvas.drawTextOnPath(text,path,hOffset,vOffset,textPaint);
    }

    private void drawArc() {
        float tempAngle=startAngle;
        for(int i=0;i<textDesc.length;i++){
            arcPaint.setColor(ContextCompat.getColor(getContext(),arcColors[i]));
            canvas.drawArc(range,tempAngle,sweepAngle,true,arcPaint);
            drawText(textDesc[i],tempAngle,sweepAngle);
            drawIco(imgBitmaps[i],tempAngle);
            tempAngle+=sweepAngle;
        }
    }

    public void startRotate(int index){
        startAngle=0;
        isRunning=true;
        float start=270-(index)*sweepAngle;  //停止的开始角度
        float end=start+sweepAngle;           //停止的终止角度
        float startTotal=4*360+start;
        float endTotal=4*360+end;             //总共转的角度
        float v1= (float) ((-1+Math.sqrt(1+8*startTotal))/2);
        float v2= (float) ((-1+Math.sqrt(1+8*endTotal))/2);
        speed= (float) (v1+Math.random()*(v2-v1));
    }

    public void stopRotate(){
        startAngle=0;
        isShouldStop=true;
    }

    public boolean isStart(){
        return isRunning;
    }

    public boolean isShouldStop(){
        return speed!=0;
    }

    public void destory(){
        isDestroy=true;
    }

    private void drawIco(Bitmap bitmap,float startAngle) {
        int imgWidth=radius/8;
        int x= (int) (center+(radius/2)*Math.cos((startAngle+sweepAngle/2)*Math.PI/180));
        int y= (int) (center+(radius/2)*Math.sin((startAngle+sweepAngle/2)*Math.PI/180));
        Rect imgRange=new Rect((x-imgWidth),(y-imgWidth/2),(x+imgWidth),(y+imgWidth/2));
        canvas.drawBitmap(bitmap,null,imgRange,arcPaint);
    }

    private void drawBg() {
        canvas.drawColor(ContextCompat.getColor(getContext(),R.color.white));
        canvas.drawBitmap(background,null,new Rect(padding/2,padding/2,getMeasuredWidth()-padding/2,getMeasuredHeight()-padding/2),arcPaint);

    }
}
