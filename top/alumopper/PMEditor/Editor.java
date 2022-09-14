package top.alumopper.PMEditor;

import top.alumopper.PMEditor.Component.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * 制谱器主类，包含了main方法
 */
public class Editor implements Runnable {

    /**
     * 当前编辑器选定的谱面
     */
    public static String chart;   //选定的谱面
    /**
     * 传入main函数的参数值
     * edit - 编辑器
     * 其他 - 选曲界面
     */
    private static String qwq;
    public final static Font f = new Font("TsangerYuMo W02",Font.PLAIN,15);

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl","true");
        qwq = args[0];
        new Thread(new Editor()).start();
    }

    /**
     * 开启一个新的线程，根据qwq字段确定打开的窗口
     */
    @Override
    public void run() {
        if(qwq.equals("edit")){
            EditorFrame fr = new EditorFrame("PMEditor - "+chart);
            fr.setSize(900,620);
            fr.setBackground(Color.black);
            fr.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()-900)/2,((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-600)/2);
            fr.setResizable(false);
            EditorPanel ep = null;
            try {
                ep = new EditorPanel(fr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fr.addEp(ep);
            fr.setIconImage(new ImageIcon("./res/icon.png").getImage());
            fr.setVisible(true);
            fr.addWindowListener(
                    new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    }
            );
            new Thread(ep).start();
        }else{
            EditorFrame fr = new EditorFrame("PMEditor");
            fr.setSize(900,620);
            fr.setBackground(Color.black);
            fr.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()-900)/2,((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-600)/2);
            fr.setResizable(false);
            SelectPanel sp = new SelectPanel(fr);
            fr.addSp(sp);
            fr.setIconImage(new ImageIcon("./res/icon.ico").getImage());
            fr.setVisible(true);
            fr.addWindowListener(
                    new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    }
            );
            new Thread(sp).start();
        }
    }
}
