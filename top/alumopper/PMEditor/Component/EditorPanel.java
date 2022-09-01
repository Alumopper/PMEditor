package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.*;
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

    private final Scrollbar t;
    private final NotePanel np;   //note显示区域
    private final InfoPanel ip;   //信息显示区域
    public ChartReader cr; //读谱器
    public double time;    //时间
    public int curLine;     //当前的判定线
    public InfoBoxContainer info;   //消息提示框
    public OperationManager opm;    //操作管理器

    public JLabel beat;
    public JLabel timeDis;
    public JMenuBar menuBar;    //菜单

    public boolean pressShift = false;
    public boolean pressCtrl = false;
    public boolean pressSpace = false;
    public boolean pressN = false;
    public boolean pressS = false;
    public boolean pressY = false;
    public boolean pressZ = false;

    public int noteType = Note.TAP;
    public boolean notSaved = false;

    public EditorPanel(JFrame fr) throws IOException {


        super(fr);
        this.setBackground(Color.black);
        curLine = 0;
        setLayout(null);
        time = 0;

        //UI
        //读谱
        cr = new ChartReader("./res/charts/"+ Editor.chart +"/chart.json");
        //note编辑
        np = new NotePanel(this);
        np.setBounds(80,50,440,500);
        np.setBackground(Color.black);
        this.add(np);
        //信息
        ip = new InfoPanel(this);
        ip.setBounds(140,5,300,40);
        this.add(ip);
        //操作历史记录
        opm = new OperationManager(this);
        //消息提示框
        info = new InfoBoxContainer(650,250,250,300);
        this.add(info);
        //进度条
        t = new Scrollbar(Scrollbar.VERTICAL,0,1,0,101);
        t.setLocation(50,50);
        t.setSize(20,500);
        t.addAdjustmentListener(e -> {
            //进度条被拖动
            time = (100-t.getValue())* cr.chart.song.time/100;
        });
        this.add(t);
        //文字
        beat = new JLabel("");
        beat.setBounds(10,0,120,20);
        beat.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        beat.setForeground(Color.white);
        this.add(beat); //节拍
        timeDis = new JLabel("");
        timeDis.setBounds(10,20,120,20);
        timeDis.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        timeDis.setForeground(Color.white);
        this.add(timeDis);  //时间

        //菜单
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
            JMenuItem saveFile = new JMenuItem("保存 (Ctrl+S)");
//            JMenuItem exitWithoutSave = new JMenuItem("不保存退出");
            fileMenu.add(saveFile);
//            fileMenu.add(exitWithoutSave);
        JMenu editMenu = new JMenu("编辑");
            JMenuItem newLine = new JMenuItem("新建判定线 (Ctrl+N)");
            editMenu.add(newLine);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        //菜单事件
        saveFile.addActionListener(e -> {
            //保存
            save();
        });
        newLine.addActionListener(e -> {
            //新建判定线
            newLine();
        });
        fr.setJMenuBar(menuBar);
        menuBar.setVisible(true);

        //事件
        this.addMouseWheelListener(e -> {
            //滚轮
            if(!pressCtrl && !pressShift){
                if(ip.onLineNo){
                    //调整判定线序号
                    setCurLine(curLine + e.getWheelRotation());
                }else{
                    //设置时间
                    time += -1*e.getWheelRotation()* np.eachTime/4;
                    if(time < 0.0) time = 0;
                    if(time > cr.chart.song.time) time = cr.chart.song.time;
                    //进度条
                    t.setValue(100-(int)(time/cr.chart.song.time*100));
                    //强制暂停
                    cr.song.songPlayer.stop();
                }
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
                    //N
                    if(e.getKeyCode() == KeyEvent.VK_N){
                        if(pressN){
                            pressN = false;
                            //保存
                            if(pressCtrl){
                                newLine();
                            }
                        }else{
                            pressN = true;
                        }
                    }
                    //S
                    if(e.getKeyCode() == KeyEvent.VK_S){
                        if(pressS){
                            pressS = false;
                            //保存
                            if(pressCtrl){
                                save();
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
                //点击了文本框外面
                ip.lineNoTf.setVisible(false);
                ip.lineNo.setVisible(true);
                setCurLine(ip.lineNoTf.getText());
            }
        });
    }

    public void draw(){
        //note展示区域
        np.repaint();
        //信息展示区更新
        ip.updateLabel();
        //消息框
        info.repaint();

        //更新自己的Label
        beat.setText("Beat "+np.bar+":"+np.beat+"/"+np.lines);
        timeDis.setText(String.format("%.2f/%.2f",time,cr.song.songPlayer.getDuration().getSeconds()));
    }

    public void loop(){
        if(cr.song.songPlayer.getState() == Player.Started){
            time = cr.song.songPlayer.getMediaTime().getSeconds();
            t.setValue(100-(int)(time/cr.chart.song.time*100));
        }
        draw();
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
        Note curr;
        if(noteType == Note.TAP){
            curr = new Tap(np.key,noteTime);
        }else{
            curr = new Drag(np.key,noteTime);
        }
        if(!cr.addNote(curr,curLine)){
            info.addInfo("放置失败，note重叠",1);
            return;
        }
        opm.addOp(new PutNote(curr));
    }

    public void putNote(double time,int key){
        //添加note
        Note curr = new Note(key,time,noteType);
        if(!cr.addNote(curr,curLine)){
            info.addInfo("放置失败，note重叠",1);
            return;
        }
        opm.addOp(new PutNote(curr));
    }

    public void putNote(Note n){
        if(!cr.addNote(n,curLine)){
            info.addInfo("放置失败，note重叠",1);
            return;
        }
        opm.addOp(new PutNote(n));
    }

    public void delNote(){
        //计算note的时间
        double noteTime = np.eachTime*(np.bar+(double)np.beat/np.lines);
        //删除note
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

    public void setCurLine(int l){
        int qwq = curLine;
        curLine = l;
        try{
            if(curLine > cr.chart.lines.size()-1 || curLine < 0){
                //超出范围
                throw new NumberFormatException();
            }
        }catch (NumberFormatException awa){
            curLine = qwq;
            ip.lineNoTf.setText(String.valueOf(curLine));
        }
    }

    public void setCurLine(String l){
        int qwq = curLine;
        try{
            curLine = Integer.parseInt(l);
            if(curLine > cr.chart.lines.size()-1 || curLine < 0){
                //超出范围
                throw new NumberFormatException();
            }
        }catch (NumberFormatException awa){
            curLine = qwq;
            ip.lineNoTf.setText(String.valueOf(curLine));
        }
    }

    public void save(){
        cr.save();
        info.addInfo("保存成功",0);
        notSaved = false;
        fr.setTitle("PMEditor - " + Editor.chart);
    }

    public void newLine(){
            //添加判定线
            cr.chart.lines.add(new Line(1.0f));
            curLine = cr.chart.lines.size() - 1;
            info.addInfo("成功添加新判定线",0);
    }
}
