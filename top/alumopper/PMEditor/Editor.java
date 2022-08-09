package top.alumopper.PMEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Editor {


    public static void main(String[] args) throws IOException {
        Frame fr = new JFrame("PMEditor");
        fr.setSize(900,600);
        fr.setBackground(Color.black);
        EditorPanel ep = new EditorPanel(fr);
        fr.add(ep);
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
    }
}
