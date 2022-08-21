package top.alumopper.PMEditor;

import top.alumopper.PMEditor.Component.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Editor implements Runnable {

    public static String chart = "Pluto";   //选定的谱面
    private static String qwq;

    public static void main(String[] args) throws IOException {
        qwq = args[0];
        new Thread(new Editor()).start();
    }

    @Override
    public void run() {
        if(qwq.equals("edit")){
            EditorFrame fr = new EditorFrame("PMEditor - "+chart);
            fr.setSize(900,600);
            fr.setBackground(Color.black);
            fr.setResizable(false);
            EditorPanel ep = null;
            try {
                ep = new EditorPanel(fr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fr.addEp(ep);
            fr.setVisible(true);
            fr.addWindowListener(
                    new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    }
            );
            new Thread(ep).run();
        }else{
            EditorFrame fr = new EditorFrame("PMEditor");
            fr.setSize(900,600);
            fr.setBackground(Color.black);
            fr.setResizable(false);
            SelectPanel sp = new SelectPanel(fr);
            fr.addSp(sp);
            fr.setVisible(true);
            fr.addWindowListener(
                    new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    }
            );
            new Thread(sp).run();
        }
    }
}
