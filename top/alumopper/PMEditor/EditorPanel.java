package top.alumopper.PMEditor;

import javax.media.Control;
import javax.media.Time;
import javax.media.bean.playerbean.MediaPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class EditorPanel extends Panel implements Runnable,MouseListener {

    private NotePanel np;   //note显示区域
    public ChartReader cr; //读谱器
    public double time;    //时间

    public EditorPanel() throws IOException {
        setLayout(null);
        cr = new ChartReader("res/chart.json");
        np = new NotePanel(this);
        //进度条
        Scrollbar t = new Scrollbar(Scrollbar.HORIZONTAL,0,1,0,101);
        t.setLocation(50,15);
        t.setSize(700,15);
        t.addAdjustmentListener(e -> {
            //进度条被拖动
            time = t.getValue()* cr.songTime/100;
        });
        this.add(t);
        this.addMouseWheelListener(e -> {
            //滚轮
            //设置时间
            time += -1*e.getWheelRotation()* np.eachTime/4;
            if(time < 0.0) time = 0;
            if(time > cr.songTime) time = cr.songTime;
            //进度条
            t.setValue((int)(time/cr.songTime*100));
        });
        time = 0;
    }

    public void paint(Graphics g){
        //note展示区域
        np.draw((Graphics2D) g);
    }

    public void repaint(){
        //离屏绘制
        Image im = createImage(getWidth(),getHeight());
        Graphics dbg = im.getGraphics();
        paint(dbg);
        Graphics g = getGraphics();
        g.drawImage(im,0,0,null);
        g.dispose();
    }

    public void loop(){
        repaint();
    }

    @Override
    public void run() {
        while (true){
            loop();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
