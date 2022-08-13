package top.alumopper.PMEditor;

import java.awt.*;

/**
 * InfoBox是一种有动画效果的信息框，可以用三种颜色标记不同等级的信息
 * 信息框的大小通常是100*40
 */
public class InfoBox extends Animation{
    /**
     * 颜色
     * 0-绿色
     * 1-黄色
     * 2-红色
     */
    private int colorType;
    public static int width = 200;
    public static int height = 40;
    public String text;

    public InfoBox(int colorType) {
        super(10, 0.3, 1);
        this.colorType = colorType;
        text = "";
    }

    public InfoBox(int colorType, String text) {
        super(10, 0.3, 1);
        this.colorType = colorType;
        this.text = text;
    }

    @Override
    public void in(double currTime, Graphics2D g) {
        //从右侧平移而入
        double progress = currTime/inTime;
        g.fillRect((int)(aniPos.x+(width - easeOutCubic(currTime,0,width,inTime))),aniPos.y,width,height);
        //绘制文字
        g.setColor(Color.black);
        g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        g.drawString(text,(int)(10+aniPos.x+(width - easeOutCubic(currTime,0,width,inTime))),aniPos.y+20);
        //System.out.println(easeOutCubic(currTime,0,width,inTime));
    }

    @Override
    public void dur(double currTime, Graphics2D g) {
        double progress = currTime/inTime;
        g.fillRect(aniPos.x,aniPos.y,width,height);
        //绘制文字
        g.setColor(Color.black);
        g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        g.drawString(text,aniPos.x+10,aniPos.y+20);
    }

    @Override
    public void out(double currTime, Graphics2D g) {
        double progress = currTime/inTime;
        //渐渐淡出
        Color pre = g.getColor();
        g.setColor(new Color(pre.getRed(),pre.getGreen(),pre.getBlue(),(int)((time-currTime)/outTime*255)));
        g.fillRect(aniPos.x,aniPos.y,width,height);
        //绘制文字
        g.setColor(Color.black);
        g.setColor(new Color(pre.getRed(),pre.getGreen(),pre.getBlue(),(int)((time-currTime)/outTime*255)));
        g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        g.drawString(text,aniPos.x+10,aniPos.y+20);
    }

    public void draw(Graphics2D g){
        if(colorType == 0){
            g.setColor(new Color(75, 232, 143, 255));
        }else if(colorType == 1){
            g.setColor(new Color(236, 195, 74, 255));
        }else if(colorType == 2){
            g.setColor(new Color(255, 72, 72, 255));
        }
        super.draw(g);
    }

    /**
     *
     * @param t 时间
     * @param b 起点
     * @param c 终点
     * @param d 持续时间
     * @return 目前时间对应的
     */
    static double easeOutCubic (double t, double b, double c, double d) {
        return c * ((t = t / d - 1) * t * t + 1) + b;
    }
}
