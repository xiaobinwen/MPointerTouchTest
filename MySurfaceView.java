package com.bn.sample_9_5;//声明包

import java.util.HashMap;
import java.util.Set;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback  //实现生命周期回调接口
{
	Sample_9_5Activity activity;//声明Sample_9_5Activity对象
	Paint paint;//声明Paint对象的引用paint
	Paint paint1;//声明Paint对象的引用paint1
	HashMap<Integer,BNPoint> hm=new HashMap<Integer,BNPoint>();//创建HashMap对象
	static int countl=0;//计数器
	BNPoint bp;//声明BNPoint对象
	
	public MySurfaceView(Sample_9_5Activity activity){//构造器
		super(activity);
		this.activity = activity;//初始化Sample_9_5Activity对象
		this.getHolder().addCallback(this);//设置生命周期回调接口的实现者
		paint = new Paint();//创建画笔paint
		paint.setAntiAlias(true);//打开抗锯齿
		
		paint1=new Paint();//创建画笔paint1
		paint1.setAntiAlias(true);//打开抗锯齿
		paint1.setTextSize(35);//设置绘制字的大小
	} 

	public void onDraw(Canvas canvas)
	{//绘制方法	
		canvas.drawColor(Color.BLACK);//绘制背景颜色
		paint.setStrokeWidth(10);//设置画笔paint的粗细度
		paint.setStyle(Style.STROKE);//设置画笔paint的风格
		
		paint1.setColor(Color.WHITE);//设置画笔paint1的颜色
		paint1.setStrokeWidth(5);//设置画笔paint1的粗细度
		paint1.setStyle(Style.STROKE);//设置画笔paint1的风格
					
		Set<Integer> ks=hm.keySet();//获取HashMap对象hm键的集合
		for(int i:ks)
		{//遍历触控点Map,对触控点一一进行绘制
				bp=hm.get(i);//取出第i个元素
			    bp.drawSelf(paint,paint1, canvas);//绘制触控点
		}	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{//触控方法
		int action=e.getAction()&MotionEvent.ACTION_MASK;//获取触控的动作编号
		//获取主、辅点id（down时主辅点id皆正确，up时辅点id正确，主点id要查询Map中剩下的一个点的id），>>>的意思是无符号右移
		@SuppressWarnings("deprecation")
		int id=(e.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>>MotionEvent.ACTION_POINTER_ID_SHIFT;	
		switch(action)	
		{
			case MotionEvent.ACTION_DOWN: //主点down 	
				//向Map中记录一个新点
				hm.put(id, new BNPoint(e.getX(id),e.getY(id),getColor(),countl++));
			break;
			case MotionEvent.ACTION_POINTER_DOWN: //辅点down	
				if(id<e.getPointerCount()-1)
				{
					//将编号往后顺（相当于给点排序）
					HashMap<Integer,BNPoint> hmTemp=new HashMap<Integer,BNPoint>();
					Set<Integer> ks=hm.keySet();//获取HashMap对象hm键的集合
					for(int i:ks)
					{//遍历触控点Map,对点进行排序
						if(i<id)
						{//当前触控点大于i
							hmTemp.put(i, hm.get(i));//点保持不变
						}
						else
						{//当前触控点小于等于i
							hmTemp.put(i+1, hm.get(i));//点向后移一位
						}
					}
					hm=hmTemp;//重新为hm赋值					
				}
				//向Map中记录一个新点
                hm.put(id, new BNPoint(e.getX(id),e.getY(id),getColor(),countl++)); 
			break;
			case MotionEvent.ACTION_MOVE: //主/辅点move  
				//不论主/辅点Move都更新其位置
				Set<Integer> ks=hm.keySet();//获取HashMap对象hm键的集合
				for(int i:ks)
				{//遍历触控点Map，更新其位置
					hm.get(i).setLocation(e.getX(i), e.getY(i));//更新点的位置					
				}
			break;
			case MotionEvent.ACTION_UP: //主点up
				//在本应用中主点UP则只需要清空Map即可，在其他一些应用中需要操作的
				//则取出Map中唯一剩下的点操作即可
				hm.clear();//清空hm
				countl=0;//计数器为0
			break;
			case MotionEvent.ACTION_POINTER_UP: //辅点up	
				hm.remove(id);//从Map中删除对应id的辅点
				//将编号往前顺，不空着
				HashMap<Integer,BNPoint> hmTemp=new HashMap<Integer,BNPoint>();
				ks=hm.keySet();//获取HashMap对象hm键的集合
				for(int i:ks)
				{//遍历触控点Map，将编号往前顺，不空着
					if(i>id)
					{//当前触控点小于i
						hmTemp.put(i-1, hm.get(i));//点向前移一位
					}
					else
					{//当前触控点大于等于i
						hmTemp.put(i, hm.get(i));//点位置不变
					}
				}
				hm=hmTemp;//重新为hm赋值
			break;
		} 
		repaint();//重绘画面
		return true;//返回true
	}	
	
	public int[] getColor()
	{//获取颜色编号的方法
		int[] result=new int[4];//创建存放颜色RGBA的数组
		result[0]=(int)(Math.random()*255);//随机获取颜色的R值
		result[1]=(int)(Math.random()*255);//随机获取颜色的G值
		result[2]=(int)(Math.random()*255);//随机获取颜色的B值
		result[3]=(int)(Math.random()*255);//随机获取颜色的A值
		return result;//返回数组
	}
	
	public void surfaceChanged(SurfaceHolder arg0,int arg1,int arg2,int arg3){//改变时被调用
		this.repaint();//重绘画面
	}

	public void surfaceCreated(SurfaceHolder holder){//创建时被调用
		
	}
	
	//自己为SurfaceView写的重绘方法
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas = holder.lockCanvas();//获取画布
		try{
			synchronized(holder){//上锁
				onDraw(canvas);//绘制
			}			
		}
		catch(Exception e){//捕获异常
			e.printStackTrace();//打印异常信息
		}
		finally{
			if(canvas != null){//画布不为null时
				holder.unlockCanvasAndPost(canvas);//解锁
			}
		}
	}

	public void surfaceDestroyed(SurfaceHolder arg0){//销毁时被调用

	}
}