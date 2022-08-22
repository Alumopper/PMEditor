package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Editor;

import javax.media.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class SelectPanel extends PMPanel{
	//选曲面板
	public SongList songList;
	public InfoBoxContainer info;

	public SelectPanel(EditorFrame fr){
		super(fr);
		this.setBackground(Color.black);
		setLayout(null);
		info = new InfoBoxContainer(650,300,250,300);
		songList = new SongList(this,new Point(100,50));
		Editor.chart = songList.songs.get(songList.index)[0];
		JButton confirm = new JButton("确认");
		confirm.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
		confirm.setBounds(600,400,100,37);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Editor.main(new String[]{"edit"});
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				fr.setVisible(false);
			}
		});
		this.add(confirm);
		this.addMouseWheelListener(e -> {
			//滚轮
			songList.index += -1*e.getWheelRotation();
			if(songList.index < 0) songList.index = 0;
			if(songList.index > songList.songs.size() - 1) songList.index = songList.songs.size()-1;
		});
	}

	@Override
	public void paint(Graphics g) {
		info.repaint();
		songList.draw((Graphics2D) g);
	}

	public void loop(){
		repaint();
	}

	@Override
	public void run() {
		while(fr.isVisible()){
			loop();
		}
	}
}
