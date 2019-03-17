package com.example.kaleidoscope;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ClickableViewAccessibility")
public class CanvasView extends View {

	public int width;
	public int height;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	Context context;
	private Paint mPaint;
	private float mX, mY;
	private static final float TOLERANCE = 5;
	List<Path> pathList = new ArrayList<Path>();
	int w;
	int h;
	int mir;
	float deg;
	boolean work = false;
	
	public CanvasView(Context c, AttributeSet attrs) {
		super(c, attrs);
		context = c;
		
		mir= MainActivity.GetMirNum();
		deg = (float) ((Math.PI*2)/mir);
		Log.i("Kaleidoscope", ""+mir);
		
		

		// we set a new Path
		
		for (int i=0; i<mir; i++)	pathList.add(new Path());

		// and we set a new Paint with the desired attributes
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
	}

	// override onSizeChanged
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// your Canvas will draw onto the defined Bitmap
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);

	}

	// override onDraw
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int max = h-2;
		if (w<h) max=w-2; 
		mPaint.setStrokeWidth(max/600);
		
		for (int i=0; i < mir; i++) {
		    canvas.drawPath(pathList.get(i), mPaint);
		}
			
	}

	// when ACTION_DOWN start touch according to the x,y values
	private void startTouch(float x, float y) {
		work=true;
		w = mCanvas.getWidth();
		h = mCanvas.getHeight();
		pathList.get(0).moveTo(x+(w/2), (h/2)-y);

		for (int j=1; j < (mir); j++) {	
			

			if ((j & 1) == 0) pathList.get(j).moveTo((float) ((x*Math.cos(j*deg))-(y*Math.sin(j*deg))+(w/2)),(float) ((h/2)-((+1*(x*Math.sin(j*deg)))+(y*Math.cos(j*deg)))));
			else {
			int i=j;
			float y2=+1*y;
			float x2=MainActivity.GetMir()*x;
			pathList.get(i).moveTo((float) ((x2*Math.cos(i*deg))-(y2*Math.sin(i*deg))+(w/2)),(float) ((h/2)-(+1*((x2*Math.sin(i*deg))+(y2*Math.cos(i*deg))))));
			}
		}
		mX = x;
		mY = y;
		work=false;

	}

	// when ACTION_MOVE move touch according to the x,y values
	private void moveTouch(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		w = mCanvas.getWidth();
		h = mCanvas.getHeight();
		float sx = (x+mX)/2;
		float sy = (y+mY)/2;
		
		
		
		if (dx >= TOLERANCE || dy >= TOLERANCE) {
			
			work=true;
			pathList.get(0).quadTo(mX+(w/2), (h/2)-mY, sx+(w/2), (h/2)-sy);

			for (int j=1; j < (mir); j++) {	
				
				if ((j & 1) == 0) pathList.get(j).quadTo((float) ((mX*Math.cos(j*deg))-(mY*Math.sin(j*deg))+(w/2)),(float) (((h/2)-((+1*(mX*Math.sin(j*deg)))+(mY*Math.cos(j*deg))))),
																			(float) ((sx*Math.cos(j*deg))-(sy*Math.sin(j*deg))+(w/2)),(float) ((h/2)-((+1*(sx*Math.sin(j*deg)))+(sy*Math.cos(j*deg)))));
				else{
				int i=j;
				float y2=+1*y;
				float x2=MainActivity.GetMir()*x;
				float mY2=+1*mY;
				float mX2=MainActivity.GetMir()*mX;
				float sy2 = (y2+mY2)/2;
				float sx2 = (x2+mX2)/2;
				pathList.get(i).quadTo((float) ((mX2*Math.cos(i*deg))-(mY2*Math.sin(i*deg))+(w/2)),(float) (((h/2)-(+1*((mX2*Math.sin(i*deg))+(mY2*Math.cos(i*deg)))))),
						(float) ((sx2*Math.cos(i*deg))-(sy2*Math.sin(i*deg))+(w/2)),(float) ((h/2)-(+1*((sx2*Math.sin(i*deg))+(sy2*Math.cos(i*deg))))));
				}
			}
			mX = x;
			mY = y;
			work=false;
		}
	}

	public void clearCanvas() {
		pathList.clear();
		mir= MainActivity.GetMirNum();
		deg = (float) ((Math.PI*2)/mir);
		Log.i("Kaleidoscope", ""+mir);

		for (int i=0; i<mir; i++) pathList.add(new Path());
		invalidate();
	}

	// when ACTION_UP stop touch
	private void upTouch() {
		w = mCanvas.getWidth();
		h = mCanvas.getHeight();
	}

	//override the onTouchEvent
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		w = mCanvas.getWidth();
		h = mCanvas.getHeight();
		
		float x = event.getX()-(w/2);
		float y = (h/2)-event.getY();
		

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startTouch(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			if (!work){
			moveTouch(x, y);
			invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			upTouch();
			invalidate();
			break;
		}
		return true;
	}
}
