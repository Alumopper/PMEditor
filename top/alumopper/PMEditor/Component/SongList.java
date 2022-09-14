package top.alumopper.PMEditor.Component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SongList extends JPanel {

	public final Point loc;
	public final ArrayList<String[]> songs = new ArrayList<>();	//曲目名字
	public final ArrayList<JLabel> songsLabel = new ArrayList<>();
	public final SelectPanel sp;
	public int index;

	public SongList(SelectPanel sp,Point p){
		setLayout(null);
		this.sp = sp;
		this.loc = p;
		setBackground(Color.black);
		chartScan();
		index = 0;
		TitledBorder tb = new TitledBorder("谱面列表");
		tb.setTitleColor(Color.white);
		tb.setTitleFont(new Font("TsangerYuMo W02",Font.PLAIN,20));
		this.setBorder(tb);
	}

	public void updateLabel(){
		//确定所有Label的位置
		//从顶部开始
		for (int i = 0; i < songsLabel.size(); i++) {
			songsLabel.get(i).setBounds(20,(this.getHeight()/2-15)+(i-index)*40,500,30);
			songsLabel.get(i).setFont(new Font("TsangerYuMo W02",Font.PLAIN,30));
			if(i == index){
				songsLabel.get(i).setForeground(Color.white);
			}else {
				songsLabel.get(i).setForeground(Color.gray);
			}
		}

	}

	public void chartScan(){
		//扫描曲目
		songs.clear();
		File[] files = new File("./res/charts").listFiles();
		assert files != null;
		for (File f : files) {
			if(f.isDirectory()){
				File info = new File(f.getPath()+"/info.txt");
				if(info.exists()){
					String[] infos = new String[3];	//储存信息
					try {
						Scanner sc = new Scanner(new FileReader(info));
						infos[0] = sc.nextLine();		//音乐文件名
						infos[1] = sc.nextLine();		//谱师
						infos[2] = sc.nextLine();		//曲师
						if(new File(f.getPath()+"/"+infos[0]+".wav").exists() && new File(f.getPath()+"/chart.json").exists()){
							songs.add(infos);
						}else {
							sp.info.addInfo("文件夹缺失文件：",f.getName(),1);
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}else {
					sp.info.addInfo("无效的文件夹",f.getName(),1);
				}
			}
		}
		for (JLabel j : songsLabel) {
			remove(j);
		}
		songsLabel.clear();
		for (String[] s : songs) {
			songsLabel.add(new JLabel(s[0]));
		}
		for (JLabel j : songsLabel) {
			add(j);
		}
	}
}
