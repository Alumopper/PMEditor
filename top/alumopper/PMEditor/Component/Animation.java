package top.alumopper.PMEditor.Component;

import java.awt.*;

/**
 * 一个动画
 * @see InfoBox
 */
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

    /**
     * 创建一个动画，它拥有确定的时长，淡入时间和淡出时间
     * @param time 动画的总时间，以s为单位
     * @param inTime 动画的淡入时间，以s为单位
     * @param outTime 动画的淡出时间，以s为单位
     */
    public Animation(double time,double inTime,double outTime){
        startTime = System.currentTimeMillis()/1000.0;
        this.time = time;
        this.inTime = inTime;
        this.outTime = outTime;
        this.durTime = time - inTime - outTime;
    }

    /**
     * 绘制淡入动画
     * @param currTime 现在动画轴所处的时间
     * @param g 一个Graphic变量，用于绘制
     */
    public void in(double currTime, Graphics2D g){

    }

    /**
     * 绘制平时动画
     * @param currTime 现在动画轴所处的时间
     * @param g 一个Graphic变量，用于绘制
     */
    public void dur(double currTime, Graphics2D g){

    }

    /**
     * 绘制淡出动画
     * @param currTime 现在动画轴所处的时间
     * @param g 一个Graphic变量，用于绘制
     */
    public void out(double currTime, Graphics2D g){

    }

    /**
     * 绘制动画。将会获取目前系统的时间，与动画开始的时间相减计算出动画轴所处的时间，从而调用对应的函数绘制动画
     * @param g 一个Graphic变量，用于绘制
     */
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

    /**
     * 此动画是否在淡出中
     * @return 如果处于淡出中，返回true
     */
    public boolean isOut(){
        double curr = System.currentTimeMillis()/1000.0;
		return curr - startTime > time;
    }

    /**
     * 此动画是否在淡入中
     * @return 如果处于淡入中，返回true
     */
    public boolean isIn(){
        double curr = System.currentTimeMillis()/1000.0;
		return curr - startTime < inTime;
    }
}
