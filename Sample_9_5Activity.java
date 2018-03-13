package com.bn.sample_9_5;//������

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class Sample_9_5Activity extends Activity {
	
	static float screenHeight;//��Ļ�߶�
	static float screenWidth;//��Ļ���
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {//��дonCreate����
        super.onCreate(savedInstanceState);
        
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//��ȡ��Ļ�ߴ�
		DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);        
        screenHeight=dm.heightPixels;//��ȡ��ʾ��Ļ�ĸ߶�
        screenWidth=dm.widthPixels;//��ȡ��ʾ��Ļ�Ŀ��
        
        MySurfaceView mySurfaceView=new MySurfaceView(this);//����MySurfaceView����
        this.setContentView(mySurfaceView);//����Ҫ��ʾ��View 
    }
}

