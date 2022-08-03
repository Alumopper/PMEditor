package top.alumopper.PMEditor;

import java.awt.*;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class InfoPanel {

    public EditorPanel ep;
    public TextField tf;

    public InfoPanel(EditorPanel ep){
        this.ep = ep;
        tf = new TextField();
        tf.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
        tf.setBackground(Color.black);
        tf.setForeground(Color.white);
        tf.setLocation(775,55);
        tf.setSize(50,25);
        tf.setText("0");
        tf.setVisible(false);
        ep.add(tf);
    }

    public void draw(Graphics2D g){
        //判定线数
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,20));
        g.drawString("判定线：",700,75);
        if(!tf.isVisible()){
            g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
            g.drawString(String.valueOf(ep.curLine),775,73);
        }
    }
}
