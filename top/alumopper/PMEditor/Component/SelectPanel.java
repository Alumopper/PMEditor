package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Editor;
import top.alumopper.PMEditor.Song;

import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SelectPanel extends PMPanel{
	//选曲面板
	public final SongList songList;
	public final InfoBoxContainer info;
	public JFileChooser fc;

	public SelectPanel(EditorFrame fr){
		super(fr);
		this.setBackground(Color.black);
		setLayout(null);

		info = new InfoBoxContainer(650,250,250,300);
		this.add(info);
		songList = new SongList(this,new Point(100,50));
		Editor.chart = songList.songs.get(songList.index)[0];
		songList.setBounds(50,30,400,500);
		songList.updateLabel();
		this.add(songList);
		JButton confirm = new JButton("确认");
		confirm.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
		confirm.setBounds(470,500,100,37);
		confirm.setBackground(new Color(95, 201, 101, 255));
		confirm.addActionListener(e -> {
			Editor.chart = songList.songs.get(songList.index)[0];
			Editor.main(new String[]{"edit"});
			fr.dispose();
		});
		confirm.setFocusable(false);
		this.add(confirm);
		JButton addchart = new JButton("创建谱面");
		addchart.setFont(new Font("TsangerYuMo W02",Font.PLAIN,15));
		addchart.setBounds(470,400,100,37);
		addchart.setBackground(new Color(60, 167, 211, 255));
		addchart.addActionListener(e -> {
			//打开了一个音频文件
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			fc = new JFileChooser("D:/");
			fc.setFileFilter(new FileNameExtensionFilter("音频文件(.wav)","wav"));

			int returnVal = fc.showOpenDialog(SelectPanel.this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				//复制文件
				File f = fc.getSelectedFile();
				File qwq = new File("./res/charts/" + f.getName().substring(0,f.getName().lastIndexOf('.')));
				if(qwq.mkdirs()){
					info.addInfo("创建文件夹失败",qwq.getPath(),2);
				}
				File out = new File("./res/charts/" +f.getName().substring(0,f.getName().lastIndexOf('.'))+"/"+f.getName());
				try {
					copyFile(f,out);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				//尝试读取音频
				try {
					new Song(f.getPath(),200);
				} catch (IOException | NoPlayerException | CannotRealizeException ex) {
					ex.printStackTrace();
				}
				//创建空白谱面文件
				String emptyChart =
						"{\n" +
						"\t\"song\":{\n" +
						"\t\t\"name\":\""+f.getName()+"\",\n" +
						"\t\t\"time\":195,\n" +
						"\t\t\"bpm\":172\n" +
						"\t},\n" +
						"\t\"lines\":[\n" +
						"\t\t{\n" +
						"\t\t\t\"notes\":[],\n" +
						"\t\t\t\"speed\":2.0\n" +
						"\t\t}\n" +
						"\t]\n" +
						"}";
				try {
					FileWriter fw = new FileWriter("./res/charts/" +f.getName().substring(0,f.getName().lastIndexOf('.'))+"/chart.json");
					fw.write(emptyChart);
					fw.flush();
					fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				String infos = f.getName().substring(0,f.getName().lastIndexOf('.'))+"\n" +
						"Unknown\n" +
						"Unknown";
				try {
					FileWriter fw = new FileWriter("./res/charts/" +f.getName().substring(0,f.getName().lastIndexOf('.'))+"/info.txt");
					fw.write(infos);
					fw.flush();
					fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				songList.chartScan();
				songList.updateLabel();
				info.addInfo("成功创建谱面",f.getName(),0);
				try {
					UIManager.setLookAndFeel(new MetalLookAndFeel());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		addchart.setFocusable(false);
		this.add(addchart);
		this.addMouseWheelListener(e -> {
			//滚轮
			songList.index += e.getWheelRotation();
			if(songList.index < 0) songList.index = 0;
			if(songList.index > songList.songs.size() - 1) songList.index = songList.songs.size()-1;
			songList.updateLabel();
		});
	}

	public void draw() {
		info.update();
	}

	public void loop(){
		draw();
	}

	@Override
	public void run() {
		while(fr.isVisible()){
			loop();
		}
	}

	private static void copyFile(File source, File dest) throws IOException {
		try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		}
	}
}
