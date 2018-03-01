package com.example.holmes.mpointertouchtest;


import android.graphics.PointF;

/**
 * Created by holmes on 18-2-8.
 */

public class PointerProperty {

	private PointF mPointF;
	private int mID;
	private int mColor;
	private String mLastTime;
	private int mLastEvent;

	public PointerProperty(PointF mPointF, int mID, int mColor) {
		this.mPointF = mPointF;
		this.mID = mID;
		this.mColor = mColor;
	}

	public PointerProperty(PointF mPointF, int mID, int mColor, String mLastTime, int mLastEvent) {
		this.mPointF = mPointF;
		this.mID = mID;
		this.mColor = mColor;
		this.mLastTime = mLastTime;
		this.mLastEvent = mLastEvent;
	}

	public void setmPointF(PointF mPointF) {
		this.mPointF = mPointF;
	}

	public PointF getmPointF() {
		return mPointF;
	}

	public int getmID() {
		return mID;
	}

	public int getmColor() {
		return mColor;
	}

	public String getmLastTime() {
		return mLastTime;
	}

	public void setmLastTime(String mLastTime) {
		this.mLastTime = mLastTime;
	}

	public int getmLastEvent() {
		return mLastEvent;
	}

	public void setmLastEvent(int mLastEvent) {
		this.mLastEvent = mLastEvent;
	}
}
