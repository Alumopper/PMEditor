package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * InfoBox是一种有动画效果的信息框，可以用三种颜色标记不同等级的信息
 * 信息框的大小通常是100*40
 */
public class InfoBox extends Animation {
    public static final int width = 200;
    public static final int height = 40;

    /**
     * 颜色<br>
     * 0-绿色<br>
     * 1-黄色<br>
     * 2-红色
     */
    public final JLabel text;
    public final JLabel text2;
    private Point currLoc;

    public InfoBox(int colorType, String text, String text2) {
        super(5, 0.3, 1);
        this.setOpaque(true);
        this.setLayout(null);
        this.text = new JLabel(text);
        this.text.setBounds(10,1,100,18);
        this.text.setFont(Editor.f);
        this.text2 = new JLabel(text2);
        this.text2.setBounds(10,19,100,18);
        this.text2.setFont(Editor.f);
        this.setLayout(null);
        this.setBounds(114514,1919810,width,height);
        this.add(this.text);
        this.add(this.text2);
        if(colorType == 0){
            this.setBackground(new Color(75, 232, 143, 255));
        }else if(colorType == 1){
            this.setBackground(new Color(236, 195, 74, 255));
        }else if(colorType == 2){
            this.setBackground(new Color(255, 72, 72, 255));
        }
        //如果点击，显示详细信息
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    @Override
    public void in(double currTime, Graphics2D g) {
        //从右侧平移而入
        currLoc = new Point((int)(aniPos.x+(width - easeOutCubic(currTime , 0, width,inTime))),aniPos.y);
        this.setLocation(currLoc);
        text.setLocation(10, 0);
        text2.setLocation(10, 18);
    }

    @Override
    public void dur(double currTime, Graphics2D g) {
        currLoc = aniPos;
        this.setLocation(currLoc);
        text.setLocation(10, 0);
        text2.setLocation(10, 18);
    }

    @Override
    public void out(double currTime, Graphics2D g) {
        //向右侧右侧平移而出
        currLoc = new Point((int)(aniPos.x + easeInCubic(currTime - time + outTime ,0, width,outTime)),aniPos.y);
        this.setLocation(currLoc);
        text.setLocation(10, 0);
        text2.setLocation(10, 18);
    }

    /**
     *
     * @param t 时间
     * @param b 起点
     * @param c 终点
     * @param d 持续时间
     * @return 目前时间对应的值
     */
    static double easeOutCubic(double t, double b, double c, double d) {
        return c * ((t = t / d - 1) * t * t + 1) + b;
    }

    /**
     *
     * @param t 时间
     * @param b 起点
     * @param c 终点
     * @param d 持续时间
     * @return 目前时间对应的值
     */
    static double easeInCubic(double t, double b, double c, double d) {
        return c * (t /= d) * t * t + b;
    }
}
