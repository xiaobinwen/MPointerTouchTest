package com.bn.sample_9_5;//������

import java.util.HashMap;
import java.util.Set;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback  //ʵ���������ڻص��ӿ�
{
	Sample_9_5Activity activity;//����Sample_9_5Activity����
	Paint paint;//����Paint���������paint
	Paint paint1;//����Paint���������paint1
	HashMap<Integer,BNPoint> hm=new HashMap<Integer,BNPoint>();//����HashMap����
	static int countl=0;//������
	BNPoint bp;//����BNPoint����
	
	public MySurfaceView(Sample_9_5Activity activity){//������
		super(activity);
		this.activity = activity;//��ʼ��Sample_9_5Activity����
		this.getHolder().addCallback(this);//�����������ڻص��ӿڵ�ʵ����
		paint = new Paint();//��������paint
		paint.setAntiAlias(true);//�򿪿����
		
		paint1=new Paint();//��������paint1
		paint1.setAntiAlias(true);//�򿪿����
		paint1.setTextSize(35);//���û����ֵĴ�С
	} 

	public void onDraw(Canvas canvas)
	{//���Ʒ���	
		canvas.drawColor(Color.BLACK);//���Ʊ�����ɫ
		paint.setStrokeWidth(10);//���û���paint�Ĵ�ϸ��
		paint.setStyle(Style.STROKE);//���û���paint�ķ��
		
		paint1.setColor(Color.WHITE);//���û���paint1����ɫ
		paint1.setStrokeWidth(5);//���û���paint1�Ĵ�ϸ��
		paint1.setStyle(Style.STROKE);//���û���paint1�ķ��
					
		Set<Integer> ks=hm.keySet();//��ȡHashMap����hm���ļ���
		for(int i:ks)
		{//�������ص�Map,�Դ��ص�һһ���л���
				bp=hm.get(i);//ȡ����i��Ԫ��
			    bp.drawSelf(paint,paint1, canvas);//���ƴ��ص�
		}	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{//���ط���
		int action=e.getAction()&MotionEvent.ACTION_MASK;//��ȡ���صĶ������
		//��ȡ��������id��downʱ������id����ȷ��upʱ����id��ȷ������idҪ��ѯMap��ʣ�µ�һ�����id����>>>����˼���޷�������
		@SuppressWarnings("deprecation")
		int id=(e.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>>MotionEvent.ACTION_POINTER_ID_SHIFT;	
		switch(action)	
		{
			case MotionEvent.ACTION_DOWN: //����down 	
				//��Map�м�¼һ���µ�
				hm.put(id, new BNPoint(e.getX(id),e.getY(id),getColor(),countl++));
			break;
			case MotionEvent.ACTION_POINTER_DOWN: //����down	
				if(id<e.getPointerCount()-1)
				{
					//���������˳���൱�ڸ�������
					HashMap<Integer,BNPoint> hmTemp=new HashMap<Integer,BNPoint>();
					Set<Integer> ks=hm.keySet();//��ȡHashMap����hm���ļ���
					for(int i:ks)
					{//�������ص�Map,�Ե��������
						if(i<id)
						{//��ǰ���ص����i
							hmTemp.put(i, hm.get(i));//�㱣�ֲ���
						}
						else
						{//��ǰ���ص�С�ڵ���i
							hmTemp.put(i+1, hm.get(i));//�������һλ
						}
					}
					hm=hmTemp;//����Ϊhm��ֵ					
				}
				//��Map�м�¼һ���µ�
                hm.put(id, new BNPoint(e.getX(id),e.getY(id),getColor(),countl++)); 
			break;
			case MotionEvent.ACTION_MOVE: //��/����move  
				//������/����Move��������λ��
				Set<Integer> ks=hm.keySet();//��ȡHashMap����hm���ļ���
				for(int i:ks)
				{//�������ص�Map��������λ��
					hm.get(i).setLocation(e.getX(i), e.getY(i));//���µ��λ��					
				}
			break;
			case MotionEvent.ACTION_UP: //����up
				//�ڱ�Ӧ��������UP��ֻ��Ҫ���Map���ɣ�������һЩӦ������Ҫ������
				//��ȡ��Map��Ψһʣ�µĵ��������
				hm.clear();//���hm
				countl=0;//������Ϊ0
			break;
			case MotionEvent.ACTION_POINTER_UP: //����up	
				hm.remove(id);//��Map��ɾ����Ӧid�ĸ���
				//�������ǰ˳��������
				HashMap<Integer,BNPoint> hmTemp=new HashMap<Integer,BNPoint>();
				ks=hm.keySet();//��ȡHashMap����hm���ļ���
				for(int i:ks)
				{//�������ص�Map���������ǰ˳��������
					if(i>id)
					{//��ǰ���ص�С��i
						hmTemp.put(i-1, hm.get(i));//����ǰ��һλ
					}
					else
					{//��ǰ���ص���ڵ���i
						hmTemp.put(i, hm.get(i));//��λ�ò���
					}
				}
				hm=hmTemp;//����Ϊhm��ֵ
			break;
		} 
		repaint();//�ػ滭��
		return true;//����true
	}	
	
	public int[] getColor()
	{//��ȡ��ɫ��ŵķ���
		int[] result=new int[4];//���������ɫRGBA������
		result[0]=(int)(Math.random()*255);//�����ȡ��ɫ��Rֵ
		result[1]=(int)(Math.random()*255);//�����ȡ��ɫ��Gֵ
		result[2]=(int)(Math.random()*255);//�����ȡ��ɫ��Bֵ
		result[3]=(int)(Math.random()*255);//�����ȡ��ɫ��Aֵ
		return result;//��������
	}
	
	public void surfaceChanged(SurfaceHolder arg0,int arg1,int arg2,int arg3){//�ı�ʱ������
		this.repaint();//�ػ滭��
	}

	public void surfaceCreated(SurfaceHolder holder){//����ʱ������
		
	}
	
	//�Լ�ΪSurfaceViewд���ػ淽��
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas = holder.lockCanvas();//��ȡ����
		try{
			synchronized(holder){//����
				onDraw(canvas);//����
			}			
		}
		catch(Exception e){//�����쳣
			e.printStackTrace();//��ӡ�쳣��Ϣ
		}
		finally{
			if(canvas != null){//������Ϊnullʱ
				holder.unlockCanvasAndPost(canvas);//����
			}
		}
	}

	public void surfaceDestroyed(SurfaceHolder arg0){//����ʱ������

	}
}