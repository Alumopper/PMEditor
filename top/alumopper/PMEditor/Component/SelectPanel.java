package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Editor;
import top.alumopper.PMEditor.FileChooser;
import top.alumopper.PMEditor.Song;

import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class SelectPanel extends PMPanel{
	//选曲面板
	public final SongList songList;

	public SelectPanel(EditorFrame fr){
		super(fr);
		this.setBackground(Color.black);
		setLayout(null);

		songList = new SongList(this,new Point(100,50));
		Editor.chart = songList.songs.get(songList.index)[0];
		songList.setBounds(50,30,400,500);
		songList.updateLabel();
		this.add(songList);
		//确认
		JButton confirm = new JButton("确认");
		confirm.setFont(Editor.f);
		confirm.setBounds(470,500,100,37);
		confirm.setBackground(new Color(95, 201, 101, 255));
		confirm.addActionListener(e -> {
			Editor.chart = songList.songs.get(songList.index)[0];
			info.addInfo("正在打开谱面", Editor.chart,0, new ClickOp());
			Editor.main(new String[]{"edit"});
			Thread.currentThread().interrupt();
		});
		confirm.setFocusable(false);
		this.add(confirm);
		//删除谱面
		JButton delete = new JButton("删除谱面");
		delete.setFont(Editor.f);
		delete.setBounds(470,450,100,37);
		delete.setBackground(new Color(0xE05757));
		delete.addActionListener(e -> {
			//删除选择的谱面
			String selectedChart = songList.songs.get(songList.index)[0];
			JOptionPane warn = new JOptionPane("谱面将会永久删除！（真的很久！）", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
			warn.setFont(Editor.f);
			JDialog dialog = warn.createDialog(Editor.currFrame, "警告");
			dialog.setFont(Editor.f);
			dialog.setVisible(true);
			Object re = warn.getValue();
			if((Integer) warn.getValue() == JOptionPane.YES_OPTION){
				//删除谱面
				for (File f : Objects.requireNonNull(new File("./res/charts/" + selectedChart).listFiles())) {
					if(!f.delete()){
						info.addInfo("删除失败","无法删除" + f.getName(), 2, new ClickOp());
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				new File("./res/charts/" + selectedChart).delete();
				info.addInfo("已删除谱面",selectedChart,0,new ClickOp());
				songList.chartScan();
				songList.updateLabel();
				songList.repaint();
			}
		});
		delete.setFocusable(false);
		add(delete);
		//创建谱面
		JButton addchart = new JButton("创建谱面");
		addchart.setFont(Editor.f);
		addchart.setBounds(470,400,100,37);
		addchart.setBackground(new Color(60, 167, 211, 255));
		addchart.addActionListener(e -> {
			//打开了一个音频文件
			File f = null;
			try {
				String[] args1 = new String[] { "java","-jar", "res/lib/ChooseFiles.jar"};
				Process proc = Runtime.getRuntime().exec(args1);// 执行py文件

				BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					f = new File(line);
				}
				in.close();
				proc.waitFor();
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
			if(f == null){
				return;
			}
			new ChartCreateFrame(SelectPanel.this, f);
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

	static void copyFile(String source, String dest) throws IOException {
		try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		}
	}

	public void createChart(File f, String[] chartInfo){
		File qwq = new File("res/charts/" + f.getName().substring(0, f.getName().lastIndexOf('.')));
		if (!qwq.mkdirs()) {
			info.addInfo("创建文件夹失败", qwq.getAbsolutePath(), 2, new ClickOp());
		}
		File out = new File("res/charts/" + f.getName().substring(0, f.getName().lastIndexOf('.')) + "/" + f.getName());
		try {
			SelectPanel.copyFile(f.getAbsolutePath(), out.getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//尝试读取音频
		try {
			new Song(f.getPath(), 200);
		} catch (IOException | NoPlayerException | CannotRealizeException ex) {
			ex.printStackTrace();
		}
		//创建空白谱面文件
		String emptyChart =
				"{\n" +
						"\t\"song\":{\n" +
						"\t\t\"name\":\"" + f.getName() + "\",\n" +
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
			FileWriter fw = new FileWriter(qwq.getAbsolutePath() + "/chart.json");
			fw.write(emptyChart);
			fw.flush();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		String infos = f.getName().substring(0, f.getName().lastIndexOf('.')) + "\n" +
				"Unknown\n" +
				"Unknown";
		try {
			FileWriter fw = new FileWriter(qwq.getAbsolutePath() + "/info.txt");
			fw.write(infos);
			fw.flush();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		songList.chartScan();
		songList.updateLabel();
		info.addInfo("成功创建谱面", f.getName(), 0, new ClickOp());
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
