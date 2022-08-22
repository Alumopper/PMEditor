package top.alumopper.PMEditor.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InfoPanel extends JPanel {

    public EditorPanel ep;
    public JLabel lineNoL;    //判定线数量
    public JLabel lineNo;    //判定线数量
    public TextField lineNoTf;  //判定线数量编辑用文本框

    public InfoPanel(EditorPanel ep){
        this.ep = ep;
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255,255)));
        Font f = new Font("TsangerYuMo W02",Font.PLAIN,15);
        lineNoL = new JLabel("判定线：");
        lineNoL.setFont(f);
        lineNoL.setBounds(0,0,60,20);
        lineNoL.setForeground(Color.white);
        add(lineNoL);
        lineNo = new JLabel("");
        lineNo.setFont(f);
        lineNo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lineNoTf.setVisible(true);
                lineNo.setVisible(false);
            }
        });
        lineNo.setBounds(60,0,40,20);
        lineNo.setForeground(Color.white);
        add(lineNo);
        lineNoTf = new TextField();
        lineNoTf.setFont(f);
        lineNoTf.setBackground(Color.black);
        lineNoTf.setForeground(Color.white);
        lineNoTf.setLocation(775,55);
        lineNoTf.setSize(50,25);
        lineNoTf.setText("0");
        lineNoTf.setVisible(false);
        lineNoTf.setBounds(60,0,40,20);
        add(lineNoTf);
    }

    public void draw(Graphics2D g){
//        //判定线数
//        g.setColor(Color.WHITE);
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,20));
//        g.drawString("判定线：",700,75);
//        if(!lineNoTf.isVisible()){
//            g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
//            g.drawString(String.valueOf(ep.curLine),775,73);
//        }
//        //拍数
    }

    public void updateLabel(){
        lineNo.setText(String.valueOf(ep.curLine));
    }
}
