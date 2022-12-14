package top.alumopper.pmeditor.component;

import top.alumopper.pmeditor.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * InfoBox是一种有动画效果的信息框，可以用三种颜色标记不同等级的信息
 * 信息框的大小通常是100*40
 */
public class InfoBox extends Animation  {
    public static final int width = 200;
    public static final int height = 40;

    /**
     * 颜色<br>
     * 0-绿色<br>
     * 1-黄色<br>
     * 2-红色
     */
    private final int colorType;

    public final JLabel text;
    public final JLabel text2;
    public final ArrayList<ClickOp> clickOps;
    public InfoBoxContainer container;

    private Point currLoc;

    private String data;

    public InfoBox(String text, String text2, int colorType) {
        super(5, 0.3, 1);
        this.colorType = colorType;
        this.setOpaque(true);
        this.setLayout(null);
        this.text = new JLabel(text);
        this.text.setBounds(10,1,200,18);
        this.text.setFont(Editor.f);
        this.text2 = new JLabel(text2);
        this.text2.setBounds(10,19,200,18);
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
        clickOps = new ArrayList<>();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                whenClicked();
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

    public InfoBox setClickOp(ClickOp clickOp){
        clickOp.infoBox = this;
        clickOps.add(clickOp);
        return this;
    }

    public InfoBox setData(String data){
        this.data = data;
        return this;
    }

    private void whenClicked(){
        for (ClickOp clickOp: clickOps) {
            clickOp.afterClick();
        }
    }

    public String toString(){
        if(colorType == 0){
            return "[INFO]" + text.getText() + " -> " + text2.getText();
        }
        if(colorType == 1){
            return "[WARN]" + text.getText() + " -> " + text2.getText();
        }
        if(colorType == 2){
            return "[ERROR]" + text.getText() + " -> " + text2.getText();
        }
        return "[UNKNOWN]" + text.getText() + " -> " + text2.getText();
    }

    public static class ClickOperations{
        public final static ClickOp DEFAULT = new ClickOp(){
            @Override
            public void afterClick(){
                super.afterClick();
            }
        };
        public final static ClickOp OPEN_FILE = new ClickOp(){
            @Override
            public void afterClick(){
                super.afterClick();
                try {
                    Desktop.getDesktop().open(new File(infoBox.data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
