package top.alumopper.PMEditor.Component;

import java.awt.*;

public class Animation {

    /**
     * 以秒为单位，表示动画的时间轴的起点
     */
    public double startTime;
    /**
     * 总时间
     */
    public double time;
    /**
     * 渐进时间
     */
    public double inTime;
    /**
     * 渐出时间
     */
    public double outTime;
    /**
     * 稳定时间
     */
    public double durTime;
    /**
     * 动画渲染的位置
     */
    public Point aniPos;

    public Animation(double time,double inTime,double outTime){
        startTime = System.currentTimeMillis()/1000.0;
        this.time = time;
        this.inTime = inTime;
        this.outTime = outTime;
        this.durTime = time - inTime - outTime;
    }

    public void in(double currTime, Graphics2D g){

    }

    public void dur(double currTime, Graphics2D g){

    }

    public void out(double currTime, Graphics2D g){

    }

    public void draw(Graphics2D g) {
        if (System.currentTimeMillis()/1000.0-startTime < time){
            double currTime = System.currentTimeMillis()/1000.0 - startTime;
            if(currTime < inTime){
                //System.out.println(currTime);
                in(currTime,g);
            }else if(currTime > time - outTime){
                //System.out.println(currTime);
                out(currTime,g);
            }else {
                //System.out.println(currTime);
                dur(currTime,g);
            }
        }
    }

    public boolean isOut(){
        double curr = System.currentTimeMillis()/1000.0;
		return curr - startTime > time;
    }

    public boolean isIn(){
        double curr = System.currentTimeMillis()/1000.0;
		return curr - startTime < inTime;
    }
}
