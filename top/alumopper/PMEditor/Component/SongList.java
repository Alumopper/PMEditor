package top.alumopper.PMEditor.Component;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SongList {

	public Point loc;
	public ArrayList<String[]> songs = new ArrayList<>();	//曲目名字
	public SelectPanel sp;
	public int index;

	public SongList(SelectPanel sp,Point p){
		this.sp = sp;
		this.loc = p;
		chartScan();
		index = 0;
	}

	public void draw(Graphics2D g){
		for(int i = 0;i < songs.size();i ++){
			if(i == index){
				g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,35));
				g.setColor(Color.white);
			}else {
				g.setFont(new Font("TsangerYuMo W02",Font.PLAIN,28));
				g.setColor(Color.gray);
			}
			if(loc.y+300+40*(i-index) >= loc.y && loc.y+300+40*(i-index) <= loc.y+500){
				g.drawString(songs.get(i)[0],loc.x+50, loc.y+300);
			}
		}

	}

	public void chartScan(){
		//扫描曲目
		File[] files = new File("./res/charts").listFiles();
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
	}
}
