package top.alumopper.pmeditor.component;

import top.alumopper.pmeditor.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InfoPanel extends JPanel {

    public final EditorPanel ep;
    public final JLabel lineNoL;    //判定线数量
    public final JLabel lineNo;    //判定线数量
    public TextField lineNoTf;  //判定线数量编辑用文本框
    public final JLabel playRateL;    //播放速度
    public final JLabel playRate;    //播放速度
    public TextField playRateTF;  //播放速度编辑用文本框
//    public JButton addLineButton;

    public boolean onLineNo = false;

    public InfoPanel(EditorPanel ep){
        this.ep = ep;
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255,255)));
        lineNoL = new JLabel("判定线：");
        lineNoL.setFont(Editor.f);
        lineNoL.setBounds(0,0,60,20);
        lineNoL.setForeground(Color.white);
        add(lineNoL);
        lineNo = new JLabel("");
        lineNo.setFont(Editor.f);
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
        lineNoTf.setFont(Editor.f);
        lineNoTf.setBackground(Color.black);
        lineNoTf.setForeground(Color.white);
        lineNoTf.setLocation(775,55);
        lineNoTf.setSize(50,25);
        lineNoTf.setText("0");
        lineNoTf.setVisible(false);
        lineNoTf.setBounds(60,0,40,20);
        add(lineNoTf);
        //谱面播放速度
        playRateL = new JLabel("速度：");
        playRateL.setFont(Editor.f);
        playRateL.setBounds(0,20,60,20);
        playRateL.setForeground(Color.white);
        add(playRateL);
        playRate = new JLabel("");
        playRate.setFont(Editor.f);
        playRate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                playRateTF.setText(String.valueOf(ep.curRate));
                playRateTF.setVisible(true);
                playRate.setVisible(false);
            }

        });
        playRate.setBounds(60,20,40,20);
        playRate.setForeground(Color.white);
        add(playRate);
        playRateTF = new TextField();
        playRateTF.setFont(Editor.f);
        playRateTF.setBackground(Color.black);
        playRateTF.setForeground(Color.white);
        playRateTF.setText("1.0");
        playRateTF.setVisible(false);
        playRateTF.setBounds(60,20,40,20);
        add(playRateTF);
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
        playRate.setText(String.valueOf(ep.curRate));
    }
}
