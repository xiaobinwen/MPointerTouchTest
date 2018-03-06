package com.holmes.mpointertouchtest;

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

	private String[] eventMotion = {"Down", "PointerDown", "Move"};

	private SparseArray<PointerProperty> mActivePointers;
	private Paint mPaint;
	private Paint textPaint;


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

				PointF f = new PointF();
				f.x = event.getX(pointerIndex);
				f.y = event.getY(pointerIndex);

				int color = ContantUtils.colors[pointerId % 9];
				String time = getCurrentDate(ContantUtils.FORMAT_DATA);
				int eventType = MotionEvent.ACTION_DOWN;

				PointerProperty pointerProperty = new PointerProperty(f, pointerId, color, time, eventType);

				mActivePointers.put(pointerId, pointerProperty);

				HLog.d("[" + f.x + " " + f.y + "]" + " currentTime:" + System.currentTimeMillis());

				break;
			}
			case MotionEvent.ACTION_POINTER_DOWN: {
				// We have a new pointer. Lets add it to the list of pointers
				PointF f = new PointF();
				f.x = event.getX(pointerIndex);
				f.y = event.getY(pointerIndex);

				int color = ContantUtils.colors[pointerId % 9];
				String time = getCurrentDate(ContantUtils.FORMAT_DATA);
				int eventType = MotionEvent.ACTION_POINTER_DOWN;

				PointerProperty pointerProperty = new PointerProperty(f, pointerId, color, time, eventType);

				mActivePointers.put(pointerId, pointerProperty);

				HLog.d("[" + f.x + " " + f.y + "]" + " currentTime:" + System.currentTimeMillis());

				break;
			}
			case MotionEvent.ACTION_MOVE: { // a pointer was moved
				PointF point = null;
				PointerProperty pointerProperty = null;

				String time = getCurrentDate(ContantUtils.FORMAT_DATA);
				int eventType = MotionEvent.ACTION_MOVE;

				pointerProperty = mActivePointers.get(event.getPointerId(pointerIndex));
				pointerProperty.setmMoveStatus(ContantUtils.MOVED);

				point = pointerProperty.getmPointF();
				point.x = event.getX(pointerIndex);
				point.y = event.getY(pointerIndex);

				pointerProperty.setmLastTime(time);
				pointerProperty.setmLastEvent(eventType);

				mActivePointers.setValueAt(pointerId, pointerProperty);

				break;
			}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				mActivePointers.remove(pointerId);
				break;
			default:
				break;
		}

		invalidate();

		return true;
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		//Mouse Hover Event
		return super.onGenericMotionEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// draw all pointers
		drawAllPointer(canvas);
	}

	/**
	 *
	 * @param canvas
	 */
	private void drawAllPointer(Canvas canvas) {
		for (int size = mActivePointers.size(), i = 0; i < size; i++) {
			PointerProperty pointerProperty = null;
			pointerProperty = mActivePointers.valueAt(i);
			PointF point = pointerProperty.getmPointF();
			int id = pointerProperty.getmID();
			int color = pointerProperty.getmColor();

			if (point != null) mPaint.setColor(color);

			drawCircle(canvas, point);
			drawAssisText(canvas, pointerProperty);
		}
	}

	/**
	 *
	 * @param canvas
	 * @param pointerProperty
	 */
	private void drawAssisText(Canvas canvas, PointerProperty pointerProperty) {

		drawAssisTextID(canvas, pointerProperty);

//		canvas.drawText("LastTime: " + pointerProperty.getmLastTime(), point.x+60, point.y-10, textPaint);
//		canvas.drawText("LastEventType: " + eventMotion[pointerProperty.getmLastEvent()], point.x+60, point.y+20, textPaint);
	}

	/**
	 * drew Pointer Index
	 * @param canvas
	 * @param pointerProperty
	 */
	private void drawAssisTextID(Canvas canvas, PointerProperty pointerProperty) {
		int id = pointerProperty.getmID();
		float x = pointerProperty.getmPointF().x + ContantUtils.DISTANCE_X;
		float y = pointerProperty.getmPointF().y - ContantUtils.ID_DISTANCE_Y;

		canvas.drawText(ContantUtils.STRING_TEXT_ID + id, x, y, textPaint);
	}

	/**
	 *
	 * @param canvas
	 * @param point
	 */
	private void drawCircle(Canvas canvas, PointF point) {
		float x, y;
		x = point.x;
		y = point.y;
		canvas.drawCircle(point.x, point.y, ContantUtils.CICLE_SIZE, mPaint);
	}
}