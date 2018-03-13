package com.bn.sample_9_5;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BNPoint{//用于记录触控点坐标、及绘制触控点的类
   static final float RADIS=80;//触控点绘制半径
   float x;//触控点X坐标
   float y;//触控点Y坐标
   int color[];//颜色Alpha通道、红色、绿色与蓝色值
   int countl;//记录按下的是第几个点
   
   public BNPoint(float x,float y,int color[],int countl)
   {//构造器
	   this.x=x;//为触控点X坐标赋值
	   this.y=y;//为触控点Y坐标赋值
	   this.color=color;//为颜色数组赋值
	   this.countl=countl;//为记录点数器赋值
   }
   
   public void setLocation(float x,float y)
   {//修改触控点位置的方法
	   this.x=x;//重新为触控点X坐标赋值
	   this.y=y;//重新为触控点Y坐标赋值
   }
   
   public void drawSelf(Paint p,Paint p1,Canvas c)
   {//绘制触控点的方法
	    p.setARGB(180, color[1], color[2], color[3]);//设置画笔颜色
	    c.drawCircle(x, y, RADIS, p);//绘制最外层的圆环
	    p.setARGB(150, color[1], color[2], color[3]);//设置画笔颜色
	    c.drawCircle(x, y, RADIS-10, p);//绘制中间的圆环
	    c.drawCircle(x, y, RADIS-18, p1);//绘制最里面的圆环
	    c.drawText(countl+1+"", x, y-100, p1);//绘制数字
   }
}
