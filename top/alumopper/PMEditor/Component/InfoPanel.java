package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InfoPanel extends JPanel {

    public EditorPanel ep;
    public JLabel lineNoL;    //判定线数量
    public JLabel lineNo;    //判定线数量
    public TextField lineNoTf;  //判定线数量编辑用文本框
    public JButton addLineButton;

    public boolean onLineNo = false;

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
                lineNoTf.setText(String.valueOf(ep.curLine));
                lineNoTf.setVisible(true);
                lineNo.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                onLineNo = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                onLineNo = false;
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
//        addLineButton = new JButton("新建");
//        addLineButton.setFont(f);
//        addLineButton.setBackground(new Color(83, 183, 134, 255));
//        addLineButton.setBounds(110,0,70,20);
//        addLineButton.addActionListener(e -> {
//            //添加判定线
//            ep.cr.chart.lines.add(new Line(1.0f));
//            ep.curLine = ep.cr.chart.lines.size() - 1;
//        });
//        add(addLineButton);
    }

    public void updateLabel(){
        lineNo.setText(String.valueOf(ep.curLine));
    }
}
