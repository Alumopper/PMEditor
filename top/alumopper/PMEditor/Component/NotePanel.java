package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.*;

import javax.media.Time;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NotePanel extends Canvas {
    public int lines;   //横线数量
    public double scale;    //缩放大小（有多少横线间隔）
    public double eachTime; //粗横线之间的时间间隔
    public int bar;         //鼠标对应的小节数
    public int beat;        //鼠标对应的拍数
    public int key;         //鼠标对应的列数

    public final EditorPanel ep;

    public NotePanel(EditorPanel ep){
        this.ep = ep;
        this.lines = 4;
        //计算时间间隔
        eachTime = 60/ep.cr.song.bpm;
        scale = 5;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(ep.pressCtrl){
                    //删除键
                    ep.delNote();
                }else{
                    //放置
                    if(e.getButton() == MouseEvent.BUTTON1){
                        //左键放置tap
                        ep.noteType = Note.TAP;
                        ep.putNote();
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        //右键放置drag
                        ep.noteType = Note.DRAG;
                        ep.putNote();
                    }
                }
                ep.notSaved = true;
                ep.fr.setTitle("PMEditor - " + Editor.chart + " *");
            }
        });
    }

    public void paint(Graphics a){
        Graphics2D g = (Graphics2D) a;
        //边框
        g.setColor(Color.white);
        for (int i = 0; i < 10; i++) {
            g.drawLine(40*i,0,40*i,500);
        }
        g.drawLine(0,0,360,0);
        //高亮主轨道
        g.setColor(new Color(208, 220, 255, 69));
        g.fillRect(40,0,40,500);
        g.fillRect(120,0,40,500);
        g.fillRect(200,0,40,500);
        g.fillRect(280,0,40,500);
        //进度条
        //g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        //drawProgressBar(g);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        //g.drawString(String.format("%.2f/%.2f",ep.time,ep.cr.song.songPlayer.getDuration().getSeconds()),10,40);
        //横线
        double delLine = -ep.time%eachTime/eachTime;
        double delPixel = 500/scale;    //粗横线之间的间距
        boolean owo = true;     //是否是第一次绘制横线
        double baseLoc = 0;     //第一条横线的位置
        int baseBeat = 0;       //第一条线对应的拍数
        for(double i = 0;i < scale+1;i += 1.0/lines){
            if(delLine+i >= 0 && delLine+i <= scale ){
                if(owo){
                    //第一次绘制横线
                    owo = false;
                    //获取第一条线对应的位置
                    baseLoc = (delLine+i)*delPixel;
                    //获取第一条线对应的拍数
                    baseBeat = (int)(i*lines)%lines;
                }
                if(Math.round(i*100)/100.0 == Math.round(i)){
                    g.setStroke(new BasicStroke(3));
                    g.setColor(new Color(19,198,10,255));
                    g.drawLine(0,(int)(500-(delLine+i)*delPixel),360,(int)(500-(delLine+i)*delPixel));
                }else{
                    g.setStroke(new BasicStroke(1));
                    g.setColor(new Color(11,45,14,255));
                    g.drawLine(0,(int)(500-(delLine+i)*delPixel),360,(int)(500-(delLine+i)*delPixel));
                }
            }
        }
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.YELLOW);
        //TODO 底部基准线
        //g.drawLine(80,550,440,550);
        //鼠标相对位置
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        try{
            mousePos.x -= ep.fr.getLocationOnScreen().x;
            mousePos.y -= ep.fr.getLocationOnScreen().y + 30;   //+30为窗口顶部白色区域误差
        }catch (IllegalComponentStateException e){
            System.out.println(e.getClass());
        }
        //此后的mousePos为相对窗口显示区域右上角为原点，坐标轴方向不变
        int beat = 0;
        //如果在范围内，就绘制按键
        if(EditorPanel.pointInRect(mousePos,80,50,440,550)){
            //自动吸附
            key = (mousePos.x-80)/40;
            mousePos.x = (mousePos.x-80)/40*40+20;
            mousePos.y = (int)(
                Math.round(
                    (
                        (mousePos.y-80)/(delPixel/lines)+1  //+1，防止向上超出范围
                    )
                )*(delPixel/lines)-4-baseLoc
            );
            //此后的mousePos为note将要显示的位置
            beat = (int)((500-mousePos.y-baseLoc)/(delPixel/lines));
            if(mousePos.y <= 550){
                if(ep.pressCtrl){
                    g.setColor(new Color(229, 90, 90, 131));
                }else {
                    g.setColor(new Color(90, 229, 97, 131));
                }
                g.fillRect(mousePos.x-20,mousePos.y-4,40,8);
            }
        }
        //节拍显示
        int bar = (int)((ep.time + baseLoc/delPixel*eachTime) / eachTime);
        beat += baseBeat;
        bar += beat/lines;
        beat %= lines;
        //g.drawString("Beat "+bar+":"+beat+"/"+lines,10,20);
        this.bar = bar;
        this.beat = beat;
        //渲染note
        for (int i = 0;i < ep.cr.chart.lines.size();i ++) {
            Line l = ep.cr.chart.lines.get(i);
            for (Note n : l.notes) {
                //是否被判定
                if(n.time < ep.time){
                    if(!n.judged){
                        n.judged = true;
                        if(ep.cr.song.isStarted()){
                            n.effect.setMediaTime(new Time(0));
                            n.effect.start();
                        }
                    }
                    continue;
                }
                n.judged = false;
                //计算y
                double noteY = 492 - (n.time- ep.time)*(delPixel/eachTime);
                if(noteY < 0 || noteY > 492) continue;
                int alpha = i==ep.curLine ? 255:100;
                if(n.type == Note.TAP){
                    g.setColor(new Color(90, 210, 229, alpha));
                }else if(n.type == Note.DRAG){
                    g.setColor(new Color(243, 241, 33, alpha));
                }
                g.fillRect(n.key*40,(int)Math.round(noteY),40,8);
            }
        }
    }

    public void repaint(){
        //离屏绘制
        Image im = createImage(getWidth(),getHeight());
        if(im == null)
            return;  //特性避免
        Graphics dbg = im.getGraphics();
        if(dbg == null) return;
        paint(dbg);
        Graphics g = getGraphics();
        if(g == null) return;
        g.drawImage(im,0,0,null);
        g.dispose();
    }
}
