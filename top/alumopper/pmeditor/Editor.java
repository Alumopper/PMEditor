package top.alumopper.pmeditor;

import javafx.embed.swing.JFXPanel;
import top.alumopper.pmeditor.component.EditorFrame;
import top.alumopper.pmeditor.component.EditorPanel;
import top.alumopper.pmeditor.component.InfoBoxContainer;
import top.alumopper.pmeditor.component.SelectPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    public static EditorFrame currFrame;

    public final static Font f = new Font("TsangerYuMo W02",Font.PLAIN,15);

    /**
     * 暂存的信息
     */
    public static InfoBoxContainer transmittedInfos = new InfoBoxContainer();

    static {
        new JFXPanel(); // initializes JavaFX environment
        Thread drawThread = new Thread(new EditorDrawer());
        drawThread.start();
    }

    public static void main(String[] args) {
        qwq = args[0];
        new Thread(new Editor()).start();
    }

    /**
     * 开启一个新的线程，根据qwq字段确定打开的窗口
     */
    @Override
    public void run() {
        if(qwq.equals("edit")){
            EditorFrame fr = new EditorFrame("pmeditor - "+chart);
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
            currFrame.dispose();
            currFrame = fr;
            currFrame.addInfoFrom(transmittedInfos);
            new Thread(ep).start();
        }else{
            EditorFrame fr = new EditorFrame("pmeditor");
            fr.setSize(900,620);
            fr.setBackground(Color.black);
            fr.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()-900)/2,((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-600)/2);
            fr.setResizable(false);
            SelectPanel sp = new SelectPanel(fr);
            fr.addSp(sp);
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
            currFrame = fr;
            currFrame.addInfoFrom(transmittedInfos);
            new Thread(sp).start();
        }
    }
}
