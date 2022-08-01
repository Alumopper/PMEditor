package top.alumopper.PMEditor;

import java.awt.*;
import java.util.ArrayList;

public class NotePanel {
    public int lines;   //横线数量
    public double scale;    //缩放大小（有多少横线间隔）
    public ArrayList<Tap> taps; //tap音符
    public ArrayList<Drag> drags;   //drag音符
    public double eachTime; //横线之间的时间间隔

    public EditorPanel ep;

    public NotePanel(EditorPanel ep){
        this.ep = ep;
        this.lines = 4;
        //计算时间间隔
        eachTime = 60/ep.cr.song.bpm;
        scale = 5;
    }

    public void draw(Graphics2D g){
        //边框
        g.setColor(Color.white);
        for (int i = 0; i < 10; i++) {
            g.drawLine(50+40*i,50,50+40*i,550);
        }
        g.drawLine(50,50,410,50);
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.YELLOW);
        g.drawLine(50,550,410,550);
        //高亮主轨道
        g.setColor(new Color(208, 220, 255, 69));
        g.fillRect(90,50,40,500);
        g.fillRect(170,50,40,500);
        g.fillRect(250,50,40,500);
        g.fillRect(330,50,40,500);
        //进度条
        //g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        //drawProgressBar(g);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        g.drawString(String.format("%.2f/%.2f",ep.time,ep.cr.song.songPlayer.getDuration().getSeconds()),775,30);
        //粗横线
        double delLine = -ep.time%eachTime/eachTime;
        double delPixel = 500/scale;
        for(double i = 0;i < 6;i += 1.0/lines){
            if(delLine+i > 0 ){
                if(i == (int)i){
                    g.setStroke(new BasicStroke(3));
                    g.setColor(new Color(191,98,10,255));
                    g.drawLine(50,(int)(550-(delLine+i)*delPixel),410,(int)(550-(delLine+i)*delPixel));
                }else{
                    g.setStroke(new BasicStroke(1));
                    g.setColor(new Color(11,45,14,255));
                    g.drawLine(50,(int)(550-(delLine+i)*delPixel),410,(int)(550-(delLine+i)*delPixel));
                }
            }
        }
    }

    private void drawProgressBar(Graphics2D g){
        g.drawLine(100,25,800,25);
        g.fillOval((int)(100+ep.time/ep.cr.song.songPlayer.getDuration().getSeconds()*700),20,10,10);
    }
}
