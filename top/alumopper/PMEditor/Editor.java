package top.alumopper.PMEditor;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Editor implements KeyListener {


    public static void main(String[] args) throws IOException {
        Frame fr = new Frame("PMEditor");
        fr.setSize(900,600);
        fr.setBackground(Color.black);
        EditorPanel ep = new EditorPanel();
        fr.add(ep);
        fr.setVisible(true);
        fr.addWindowListener(
                new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {

                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {

                    }

                    @Override
                    public void windowIconified(WindowEvent e) {

                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {

                    }

                    @Override
                    public void windowActivated(WindowEvent e) {

                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {

                    }
                }
        );
        new Thread(ep).run();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
