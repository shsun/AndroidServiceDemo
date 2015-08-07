package com.doepiccoding.cavasexample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class MainActivity extends Activity {
	
	public static final int CAR_SPEED = 10;
	public static final int STOP_CAR = 25;
	
	private SurfaceView mySurfaceView;
	private SurfaceHolder surfaceHolder;
	private boolean renderFlag;
	private Thread renderThread;
	private Bitmap carBmp;
	private float directionAndSpeedX;
	private float directionAndSpeedY;
	private float angle;
	private float carPosX = 100;
	private float carPosY = 100;
	private float tapX;
	private float tapY;
	private Rect bmpRectSrc;
	private Rect bmpRectDst = new Rect();    
	private Paint drawPaint = new Paint();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.mySurfaceView = (SurfaceView) findViewById(R.id.mySurfaceView);
		
		
		
		
		
		this.surfaceHolder = this.mySurfaceView.getHolder();
		this.surfaceHolder.addCallback(surfaceHolderCallback);
		carBmp = BitmapFactory.decodeResource(getResources(), R.drawable.car_sprite);
		bmpRectSrc = new Rect(0, 0, carBmp.getWidth(), carBmp.getHeight());
		drawPaint.setAntiAlias(true);
		drawPaint.setColor(Color.BLUE);
		
		//Start Rendering...
		renderThread = new Thread(renderingLoop);
		renderThread.start();
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				tapX = event.getRawX();
				tapY = event.getRawY();
				float deltaX = tapX - (carPosX + bmpRectSrc.right/2);
				float deltaY = tapY - (carPosY + bmpRectSrc.bottom);
				
				angle = (float)Math.atan2(deltaY, deltaX);
				directionAndSpeedX = (float)Math.cos(angle) * CAR_SPEED;
				directionAndSpeedY = (float)Math.sin(angle) * CAR_SPEED;
				renderFlag = true;
			break;
			default:
		}
		return super.onTouchEvent(event);
	}
	
	private SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			//Now that the holder is ready, lets start refreshing the Image...
			renderFlag = true;
			


		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			
		}
	};
	
	protected void onDestroy() {
		super.onDestroy();
		renderThread.interrupt();
		carBmp.recycle();
		carBmp = null;
	};
	
	private Runnable renderingLoop = new Runnable(){

		@Override
		public void run() {
			boolean interrupedInternally = false;
			while(!renderThread.isInterrupted() && !interrupedInternally){
				try{Thread.sleep(40);}catch(InterruptedException ie){interrupedInternally = true;}
				if(!renderFlag)continue;
				
				//Calculate the next position by adding the direction value...
				carPosX += directionAndSpeedX;
				carPosY += directionAndSpeedY;
				
				//If it's at the tap point stop...
				float dx = tapX - (carPosX + bmpRectSrc.right/2);
				float dy = tapY - (carPosY + bmpRectSrc.bottom);
				int dist = (int)Math.sqrt(dx * dx + dy * dy);
				if(dist <= bmpRectSrc.bottom/2){
					renderFlag = false;
					continue;
				}
						
				//Set the proper dst rectangle for the bmp...
				bmpRectDst.left = (int)carPosX;
				bmpRectDst.top = (int)carPosY;
				bmpRectDst.right = bmpRectDst.left + bmpRectSrc.right;
				bmpRectDst.bottom = bmpRectDst.top + bmpRectSrc.bottom;
				//Drawing logic... 
				try{
					Canvas canvas = surfaceHolder.lockCanvas();
					/*
					canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
					canvas.save();
					canvas.rotate((int)(angle * 180 / Math.PI) + 90, bmpRectDst.exactCenterX(), bmpRectDst.exactCenterY());
					canvas.drawBitmap(carBmp, bmpRectSrc, bmpRectDst, drawPaint);
					canvas.restore();
					
					canvas.drawText("STOP HERE! ", tapX - 30, tapY - 30, drawPaint);
					*/					

                     
                    canvas.drawColor(Color.WHITE);                       
                    Paint p = new Paint();  
                    p.setColor(Color.GREEN);  
                    p.setTextSize(30);  
                    Rect rect = new Rect(100, 50, 380, 330);  
                    canvas.drawRect(rect,p);  
                    canvas.drawText("Interval = seconds.", 100, 410, p);  
                    Thread.sleep(1000); 
					
					
					//Region r = new Region(new Rect(60, 60, 70, 70));
					//mySurfaceView.gatherTransparentRegion(r);
					
					
					((ViewGroup)MainActivity.this.findViewById(R.layout.activity_main)).gatherTransparentRegion(new Region(new Rect(110, 60, 400, 400)));		

					
					surfaceHolder.unlockCanvasAndPost(canvas);
				}catch(Exception e){interrupedInternally = true;}
			}
		}
	};
}
