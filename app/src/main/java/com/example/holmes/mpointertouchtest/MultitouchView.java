package com.example.holmes.mpointertouchtest;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by holmes on 16-10-17.
 */
public class MultitouchView extends View {

	private static final int SIZE = 60;

	private SparseArray<PointerProperty> mActivePointers;
	private int motion;
	private int down;
	private static int num, down_count, pdown_count, move;
	private Paint mPaint;
	private int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA,
			Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
			Color.LTGRAY, Color.YELLOW };

	private String[] eventMotion = {"Down", "PointerDown", "Move"};

	private Paint textPaint;
	private long downCurrentTime;
	private ArrayList moveCurrentTime = new ArrayList();
	private final String FORMAT_DATA = "HH:mm:ss:SSS";


	public MultitouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		mActivePointers = new SparseArray<PointerProperty>();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// set painter color to a color you like
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(20);
//		setBackgroundColor(0xff000000);
	}

	public static String getCurrentDate(String pattern) {

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String timestamp = formatter.format(curDate);

		return timestamp;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);

		// get masked (not specific to a pointer) action
		int maskedAction = event.getActionMasked();
		switch (maskedAction) {

			case MotionEvent.ACTION_DOWN: {
				down_count++;
				motion = MotionEvent.ACTION_DOWN;
				PointF f = new PointF();

				f.x = event.getX(pointerIndex);
				f.y = event.getY(pointerIndex);

				int color = colors[pointerId % 9];
				String time = getCurrentDate(FORMAT_DATA);
				int eventType = 0;
				PointerProperty pointerProperty = new PointerProperty(f, pointerId, color, time, eventType);
				mActivePointers.put(pointerId, pointerProperty);
				downCurrentTime = event.getEventTime();
				//System.out.println("===>" + getCurrentDate("yyyy-MM-dd HH:mm:ss:SSSSSS"));
				//System.out.println("[" + f.x + " " + f.y + "]" + " currentTime:" + System.currentTimeMillis());
				break;
			}
			case MotionEvent.ACTION_POINTER_DOWN: {
				// We have a new pointer. Lets add it to the list of pointers
				//System.out.println("---->ACTION_POINTER_DOWN");
				pdown_count++;
				down = 2;
				motion = MotionEvent.ACTION_POINTER_DOWN;
				PointF f = new PointF();
				f.x = event.getX(pointerIndex);
				f.y = event.getY(pointerIndex);

				int color = colors[pointerId % 9];
				String time = getCurrentDate(FORMAT_DATA);
				int eventType = 1;
				PointerProperty pointerProperty = new PointerProperty(f, pointerId, color, time, eventType);
				//PointerProperty pointerProperty = new PointerProperty(f, pointerId, color);
				mActivePointers.put(pointerId, pointerProperty);

				mActivePointers.put(pointerId, pointerProperty);
				break;
			}
			case MotionEvent.ACTION_MOVE: { // a pointer was moved
				motion = MotionEvent.ACTION_MOVE;
				move++;
				down = 3;
				PointF point = null;
				PointerProperty pointerProperty = null;

				String time = getCurrentDate(FORMAT_DATA);
				int eventType = 2;

				//System.out.println("===>move " + getCurrentDate("yyyy-MM-dd HH:mm:ss:SSS"));
//				mActivePointers.removeAt(pointerId);
//				pointerProperty.setmLastTime(time);
//				pointerProperty.setmLastEvent(eventType);
//				point = pointerProperty.getmPointF();
//				point.x = event.getX(pointerId);
//				point.y = event.getY(pointerId);
//				pointerProperty.setmPointF(point);
//				mActivePointers.setValueAt(pointerId, pointerProperty);
				for (int size = event.getPointerCount(), i = 0; i < size; i++) {
					pointerProperty = mActivePointers.get(event.getPointerId(i));
					if (pointerProperty != null) {
						point = pointerProperty.getmPointF();
						point.x = event.getX(i);
						point.y = event.getY(i);

						pointerProperty.setmLastTime(time);
						pointerProperty.setmLastEvent(eventType);
					} else {
						System.out.println(" point null");
						break;
					}
				}
				moveCurrentTime.add(event.getEventTime());
				//System.out.println("[" + point.x + " " + point.y + "]" + " currentTime:" + System.currentTimeMillis());
				break;
			}
			case MotionEvent.ACTION_UP:
				num=0;
				down_count = 0;
				move = 0;
				motion = MotionEvent.ACTION_UP;
				moveCurrentTime.clear();
				downCurrentTime = 0;
				mActivePointers.remove(pointerId);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				num=0;
				pdown_count = 0;
				move = 0;
				moveCurrentTime.clear();
				motion = MotionEvent.ACTION_POINTER_UP;
				mActivePointers.remove(pointerId);
				break;
			case MotionEvent.ACTION_CANCEL: {
				//System.out.println("---->Action_up");
				num=0;
				down = 6;
				move = 0;
				downCurrentTime = 0;
				moveCurrentTime.clear();
				motion = MotionEvent.ACTION_CANCEL;
				mActivePointers.remove(pointerId);
				break;
			}
		}

		invalidate();

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// draw all pointers
		for (int size = mActivePointers.size(), i = 0; i < size; i++) {
			PointerProperty pointerProperty = null;
			pointerProperty = mActivePointers.valueAt(i);
			PointF point = pointerProperty.getmPointF();
			int id = pointerProperty.getmID();
			int color = pointerProperty.getmColor();

			if (point != null) mPaint.setColor(color);

			canvas.drawCircle(point.x, point.y, SIZE, mPaint);
//			mPaint.setColor(colors[i % 5]);
			//canvas.drawCircle(point.x+70, point.y-60, 30, mPaint);
			canvas.drawText("ID: " + id, point.x+60, point.y-40, textPaint);
			canvas.drawText("LastTime: " + pointerProperty.getmLastTime(), point.x+60, point.y-10, textPaint);
			canvas.drawText("LastEventType: " + eventMotion[pointerProperty.getmLastEvent()], point.x+60, point.y+20, textPaint);
		}
	}

}