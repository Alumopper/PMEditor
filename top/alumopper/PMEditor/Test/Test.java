package top.alumopper.PMEditor.Test;

import top.alumopper.PMEditor.Editor;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Test implements Runnable {

    static boolean qwq = true;

    public static void main(String[] args) {
        System.out.println(qwq);
        new Thread(new Test()).start();
        System.out.println(qwq);
        new Thread(new Test()).start();
    }

    @Override
    public void run() {
        Frame f = new Frame("qwq");
        MyPanel mp = new MyPanel();
        f.setLocation(300, 200);
        f.setSize(300, 300);
        f.setBackground(Color.black);
        f.add(mp);
        f.setVisible(true);
        mp.gameLoop();
    }
}

class MyPanel extends Panel  {
    private int x;
    private int y;
    private int diameter;

    public MyPanel() {
        x = 50;
        y = 50;
        diameter = 100;
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                System.out.println(e);
            }
        });
    }

    public void paint(Graphics g) {
        g.setColor(Color.blue);
        g.fillOval(x, y, diameter, diameter);
    }

    public void gameLoop() {
        while (true) {
            x++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    Image im;

    public void gameRender() {
        im = createImage(getWidth(), getHeight());
        Graphics dbg = im.getGraphics();
        dbg.setColor(Color.blue);
        dbg.fillOval(x, y, diameter, diameter);
    }

    public void gamePaint() {
        Graphics g = this.getGraphics();
        g.drawImage(im, 0, 0, null);
        g.dispose();
    }
}
