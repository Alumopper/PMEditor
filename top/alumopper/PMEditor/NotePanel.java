package top.alumopper.PMEditor;

import java.awt.*;
import java.util.ArrayList;

public class NotePanel extends Component {
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
            g.drawLine(80+40*i,50,80+40*i,550);
        }
        g.drawLine(80,50,440,50);
        //高亮主轨道
        g.setColor(new Color(208, 220, 255, 69));
        g.fillRect(120,50,40,500);
        g.fillRect(200,50,40,500);
        g.fillRect(280,50,40,500);
        g.fillRect(360,50,40,500);
        //进度条
        //g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        //drawProgressBar(g);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        g.drawString(String.format("%.2f/%.2f",ep.time,ep.cr.song.songPlayer.getDuration().getSeconds()),10,40);
        //横线
        double delLine = -ep.time%eachTime/eachTime;
        double delPixel = 500/scale;
        for(double i = 0;i < scale+1;i += 1.0/lines){
            if(delLine+i >= 0 && delLine+i <= scale ){
                if(lines == 3){
                    System.out.println();
                }
                if(Math.round(i*100)/100.0 == Math.round(i)){
                    g.setStroke(new BasicStroke(3));
                    g.setColor(new Color(19,198,10,255));
                    g.drawLine(80,(int)(550-(delLine+i)*delPixel),440,(int)(550-(delLine+i)*delPixel));
                }else{
                    g.setStroke(new BasicStroke(1));
                    g.setColor(new Color(11,45,14,255));
                    g.drawLine(80,(int)(550-(delLine+i)*delPixel),440,(int)(550-(delLine+i)*delPixel));
                }
            }
        }
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.YELLOW);
        g.drawLine(80,550,440,550);
        //鼠标相对位置
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        mousePos.x -= ep.fr.getLocationOnScreen().x;
        mousePos.y -= ep.fr.getLocationOnScreen().y + 30;
        //如果在范围内，就绘制按键
        if(EditorPanel.pointInRect(mousePos,80,50,440,550)){
            g.setColor(new Color(90, 210, 229, 131));
            g.fillRect(mousePos.x-20,mousePos.y-4,40,8);
        }
    }

    private void drawProgressBar(Graphics2D g){
        g.drawLine(100,25,800,25);
        g.fillOval((int)(100+ep.time/ep.cr.song.songPlayer.getDuration().getSeconds()*700),20,10,10);
    }
}
