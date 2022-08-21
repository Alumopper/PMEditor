package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.ChartReader;
import top.alumopper.PMEditor.Editor;
import top.alumopper.PMEditor.Note;
import top.alumopper.PMEditor.Operation.DeleteNote;
import top.alumopper.PMEditor.Operation.OperationManager;
import top.alumopper.PMEditor.Operation.PutNote;

import javax.media.Player;
import javax.media.Time;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class EditorPanel extends PMPanel implements Runnable {

    private Scrollbar t;
    private NotePanel np;   //note显示区域
    private InfoPanel ip;   //信息显示区域
    public ChartReader cr; //读谱器
    public double time;    //时间
    public int curLine;     //当前的判定线
    public InfoBoxContainer info;   //消息提示框
    public OperationManager opm;    //操作管理器

    public boolean pressShift = false;
    public boolean pressCtrl = false;
    public boolean pressSpace = false;
    public boolean pressS = false;
    public boolean pressY = false;
    public boolean pressZ = false;

    public int noteType = Note.TAP;
    public boolean notSaved = false;

    public EditorPanel(Frame fr) throws IOException {
        super(fr);
        this.setBackground(Color.black);
        curLine = 0;
        setLayout(null);
        cr = new ChartReader("./res/charts/"+ Editor.chart +"/chart.json");
        np = new NotePanel(this);
        ip = new InfoPanel(this);
        opm = new OperationManager(this);
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
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
                e -> {
                    //键盘监听
                    //ctrl
                    pressCtrl = e.isControlDown();
                    //shift
                    pressShift = e.isShiftDown();
                    //空格
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
                    //S
                    if(e.getKeyCode() == KeyEvent.VK_S){
                        if(pressS){
                            pressS = false;
                            //保存
                            if(pressCtrl){
                                cr.save();
                                info.addInfo("保存成功",0);
                                notSaved = false;
                            }
                        }else{
                            pressS = true;
                        }
                    }
                    //Y
                    if(e.getKeyCode() == KeyEvent.VK_Y){
                        if(pressY){
                            pressY = false;
                            //重做
                            if(pressCtrl){
                                opm.redo();
                            }
                        }else{
                            pressY = true;
                        }
                    }
                    //Z
                    if(e.getKeyCode() == KeyEvent.VK_Z){
                        if(pressZ){
                            pressZ = false;
                            //撤销
                            if(pressCtrl){
                                opm.revoke();
                            }
                        }else{
                            pressZ = true;
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
                    //在note放置区内
                    if(pressCtrl){
                        //删除键
                        delNote();
                    }else{
                        //放置
                        if(e.getButton() == MouseEvent.BUTTON1){
                            //左键放置tap
                            noteType = Note.TAP;
                            putNote();
                        }else if(e.getButton() == MouseEvent.BUTTON3){
                            //右键放置drag
                            noteType = Note.DRAG;
                            putNote();
                        }
                    }
                    notSaved = true;
                    fr.setTitle(fr.getTitle() + " *");
                }
            }
        });
        time = 0;
        //消息提示框
        info = new InfoBoxContainer(650,500);
    }

    public void paint(Graphics g){
        //note展示区域
        np.draw((Graphics2D) g);
        //信息展示区
        ip.draw((Graphics2D) g);
        //消息框
        info.draw((Graphics2D) g);
        //super.paintChildren(g);
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
        while (fr.isVisible()){
            loop();
        }
    }

    public void putNote(){
        //计算note的时间
        double noteTime = np.eachTime*(np.bar+(double)np.beat/np.lines);
        //添加note
        Note curr = new Note(np.key,noteTime,noteType);
        if(!cr.addNote(curr,curLine)){
            info.addInfo("放置失败，note重叠",1);
        }
        opm.addOp(new PutNote(curr));
    }

    public void putNote(double time,int key){
        //添加note
        Note curr = new Note(key,time,noteType);
        if(!cr.addNote(curr,curLine)){
            info.addInfo("放置失败，note重叠",1);
        }
        opm.addOp(new PutNote(curr));
    }

    public void putNote(Note n){
        if(!cr.addNote(n,curLine)){
            info.addInfo("放置失败，note重叠",1);
        }
        opm.addOp(new PutNote(n));
    }

    public void delNote(){
        //计算note的时间
        double noteTime = np.eachTime*(np.bar+(double)np.beat/np.lines);
        //添加note
        Note curr = new Note(np.key,noteTime,noteType);
        cr.delNote(curr,curLine);
        opm.addOp(new DeleteNote(curr));
    }

    public void delNote(double time,int key){
        //删除note
        Note curr = new Note(key,time,noteType);
        cr.delNote(curr,curLine);
        opm.addOp(new DeleteNote(curr));
    }

    public void delNote(Note n){
        cr.delNote(n,curLine);
        opm.addOp(new DeleteNote(n));
    }

}
