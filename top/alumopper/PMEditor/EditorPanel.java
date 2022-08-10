package top.alumopper.PMEditor;

import javax.media.Control;
import javax.media.Player;
import javax.media.Time;
import javax.media.bean.playerbean.MediaPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditorPanel extends Panel implements Runnable {

    public Frame fr;
    private Scrollbar t;
    private NotePanel np;   //note显示区域
    private InfoPanel ip;   //信息显示区域
    public ChartReader cr; //读谱器
    public double time;    //时间
    public int curLine;     //当前的判定线

    public boolean pressShift = false;
    public boolean pressCtrl = false;
    public boolean pressSpace = false;
    public boolean pressS = false;

    private int noteType = Note.TAP;

    public EditorPanel(Frame fr) throws IOException {
        this.fr = fr;
        this.setBackground(Color.black);
        curLine = 0;
        setLayout(null);
        cr = new ChartReader("res/chart.json");
        np = new NotePanel(this);
        ip = new InfoPanel(this);
        //进度条
        t = new Scrollbar(Scrollbar.VERTICAL,0,1,0,101);
        t.setLocation(50,50);
        t.setSize(20,500);
        t.addAdjustmentListener(e -> {
            //进度条被拖动
            time = (100-t.getValue())* cr.songTime/100;
        });
        this.add(t);
        this.addMouseWheelListener(e -> {
            //滚轮
            if(!pressCtrl && !pressShift){
                //设置时间
                time += -1*e.getWheelRotation()* np.eachTime/4;
                if(time < 0.0) time = 0;
                if(time > cr.songTime) time = cr.songTime;
                //进度条
                t.setValue(100-(int)(time/cr.songTime*100));
            }else if(pressCtrl && !pressShift){
                //调整缩放
                np.scale += -1*e.getWheelRotation();
                if(np.scale < 1) np.scale = 1;
                if(np.scale > 16) np.scale = 16;
            }else{
                //调整横线数量
                np.lines += -1*e.getWheelRotation();
                if(np.lines < 1) np.lines = 1;
                if(np.lines > 16) np.lines = 16;
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                System.out.println(e);
            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
                e -> {
                    pressCtrl = e.isControlDown();
                    pressShift = e.isShiftDown();
                    if(e.getKeyCode() == KeyEvent.VK_SPACE){
                        if(pressSpace){
                            pressSpace = false;
                            //播放
                            if(cr.song.songPlayer.getState() != Player.Started){
                                cr.song.songPlayer.setMediaTime(new Time(time));
                                cr.song.songPlayer.start();
                            }else{
                                cr.song.songPlayer.stop();
                            }
                        }else{
                            pressSpace = true;
                        }
                    }
                    if(e.getKeyCode() == KeyEvent.VK_S){
                        if(pressS){
                            pressS = false;
                            //保存
                            cr.save();
                            System.out.println("awa");
                        }else{
                            pressS = true;
                        }
                    }
                    return false;
                }
        );
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
                if(pointInRect(clickPoint,755,55,825,80)){
                    //点击了文本框内
                    ip.tf.setVisible(true);
                }else{
                    //点击了文本框外面
                    ip.tf.setVisible(false);
                    int qwq = curLine;
                    try{
                        curLine = Integer.parseInt(ip.tf.getText());
                    }catch (NumberFormatException awa){
                        curLine = qwq;
                        ip.tf.setText(String.valueOf(curLine));
                    }
                }
                if(pointInRect(clickPoint,80,50,430,550)){
                    //在note放置区内，放置note
                    putNote();
                }
            }
        });
        time = 0;
    }

    public void paint(Graphics g){
        //note展示区域
        np.draw((Graphics2D) g);
        ip.draw((Graphics2D) g);
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
        if(cr.song.songPlayer.getState() == Player.Started){
            time = cr.song.songPlayer.getMediaTime().getSeconds();
            t.setValue(100-(int)(time/cr.songTime*100));
        }
        repaint();
    }

    @Override
    public void run() {
        while (true){
            loop();
        }
    }

    public static boolean pointInRect(Point p,int x1,int y1,int x2,int y2){
        if(x1 <= p.x && p.x <= x2 && y1 <= p.y && p.y <= y2){
            return true;
        }else {
            return false;
        }
    }

    public void putNote(){
        //计算note的时间
        double noteTime = np.eachTime*(np.bar+(double)np.beat/np.lines);
        //添加note
        Note curr = new Note(np.key,noteTime,noteType);
        cr.addNote(curr,curLine);
    }
}
