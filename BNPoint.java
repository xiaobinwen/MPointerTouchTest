package com.bn.sample_9_5;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BNPoint{//���ڼ�¼���ص����ꡢ�����ƴ��ص����
   static final float RADIS=80;//���ص���ư뾶
   float x;//���ص�X����
   float y;//���ص�Y����
   int color[];//��ɫAlphaͨ������ɫ����ɫ����ɫֵ
   int countl;//��¼���µ��ǵڼ�����
   
   public BNPoint(float x,float y,int color[],int countl)
   {//������
	   this.x=x;//Ϊ���ص�X���긳ֵ
	   this.y=y;//Ϊ���ص�Y���긳ֵ
	   this.color=color;//Ϊ��ɫ���鸳ֵ
	   this.countl=countl;//Ϊ��¼��������ֵ
   }
   
   public void setLocation(float x,float y)
   {//�޸Ĵ��ص�λ�õķ���
	   this.x=x;//����Ϊ���ص�X���긳ֵ
	   this.y=y;//����Ϊ���ص�Y���긳ֵ
   }
   
   public void drawSelf(Paint p,Paint p1,Canvas c)
   {//���ƴ��ص�ķ���
	    p.setARGB(180, color[1], color[2], color[3]);//���û�����ɫ
	    c.drawCircle(x, y, RADIS, p);//����������Բ��
	    p.setARGB(150, color[1], color[2], color[3]);//���û�����ɫ
	    c.drawCircle(x, y, RADIS-10, p);//�����м��Բ��
	    c.drawCircle(x, y, RADIS-18, p1);//�����������Բ��
	    c.drawText(countl+1+"", x, y-100, p1);//��������
   }
}
