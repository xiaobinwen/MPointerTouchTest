package com.bn.sample_9_5;//声明包

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class Sample_9_5Activity extends Activity {
	
	static float screenHeight;//屏幕高度
	static float screenWidth;//屏幕宽度
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写onCreate方法
        super.onCreate(savedInstanceState);
        
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//设置为竖屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//获取屏幕尺寸
		DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);        
        screenHeight=dm.heightPixels;//获取显示屏幕的高度
        screenWidth=dm.widthPixels;//获取显示屏幕的宽度
        
        MySurfaceView mySurfaceView=new MySurfaceView(this);//创建MySurfaceView对象
        this.setContentView(mySurfaceView);//设置要显示的View 
    }
}

