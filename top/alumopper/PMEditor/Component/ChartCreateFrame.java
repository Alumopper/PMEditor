package top.alumopper.PMEditor.Component;
import top.alumopper.PMEditor.Editor;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * 用于填写曲目信息的窗口
 */
public class ChartCreateFrame extends InlineFrame{

	private final static long BPM_MAX_TESTTIME = 20000;

	private final JLabel title;
	private final JLabel chartAuthor;
	private final JTextField chartAuthorT;
	private final JLabel musicAuthor;
	private final JTextField musicAuthorT;
	private final JLabel bpm;
	private final JTextField bpmT;
	private final JButton addchart;
	private final JButton bpmGet;
	private final File f;

	private String v = null;	//bpm测量线程返回值
	private long startTime;
	private static Process process;
	private final Thread bpmTest = new Thread(){
		@Override
		public void run(){
			v = getBpm(f.getPath());
			if(v.startsWith("Python Exception")){
				parentPanel.info.addInfo("Python错误",v.substring(16),2, new ClickOp() {
					@Override
					public void afterClick(){
						super.afterClick();
						JOptionPane.showMessageDialog(parentPanel,v);
					}
				});
			}else {
				bpmT.setText(String.valueOf(getBpm(f.getPath())));
			}
		}
	};	//bpm测量线程
	private final Thread bpmTestTimer = new Thread(){
		@Override
		public void run(){
			while (v == null){
				if(System.currentTimeMillis() - startTime > BPM_MAX_TESTTIME){
					//超时
					process.destroy();
					parentPanel.info.addInfo("bpm测量超时(" + BPM_MAX_TESTTIME + "ms)", "点此手动测量", 2, new ClickOp(){
						@Override
						public void afterClick(){
							super.afterClick();
							try {
								Desktop.getDesktop().browse(new URI("https://www.freejishu.com/tools/bpm.html"));
							} catch (IOException | URISyntaxException ex) {
								ex.printStackTrace();
							}
						}
					});
				}
			}
		}
	};

	public SelectPanel parentPanel;

	public ChartCreateFrame(SelectPanel jPanel, File f) {
		super(jPanel);
		parentPanel = jPanel;
		this.f = f;
		this.setBackground(Color.black);
		this.setBounds(250,150,400,300);
		title = new JLabel("曲目信息 - " + f.getName());
		title.setBounds(20,40,380,25);
		title.setForeground(Color.white);
		title.setFont(new Font("TsangerYuMo W02",Font.PLAIN,20));
		add(title);
		chartAuthor = new JLabel("谱面作者");
		chartAuthor.setBounds(50,80,100,25);
		chartAuthor.setForeground(Color.white);
		chartAuthor.setFont(Editor.f);
		add(chartAuthor);
		chartAuthorT = new JTextField();
		chartAuthorT.setBounds(150,80,200,25);
		chartAuthorT.setBackground(Color.black);
		chartAuthorT.setForeground(Color.white);
		chartAuthorT.setFont(Editor.f);
		add(chartAuthorT);
		musicAuthor = new JLabel("曲目作者");
		musicAuthor.setBounds(50,120,100,25);
		musicAuthor.setForeground(Color.white);
		musicAuthor.setFont(Editor.f);
		add(musicAuthor);
		musicAuthorT = new JTextField();
		musicAuthorT.setBounds(150,120,200,25);
		musicAuthorT.setBackground(Color.black);
		musicAuthorT.setForeground(Color.white);
		musicAuthorT.setFont(Editor.f);
		add(musicAuthorT);
		bpm = new JLabel("BPM");
		bpm.setBounds(50,160,100,25);
		bpm.setForeground(Color.white);
		bpm.setFont(Editor.f);
		add(bpm);
		bpmT = new JTextField();
		bpmT.setBounds(150,160,80,25);
		bpmT.setBackground(Color.black);
		bpmT.setForeground(Color.white);
		bpmT.setFont(Editor.f);
		add(bpmT);
		addchart = new JButton("创建谱面");
		addchart.setFont(Editor.f);
		addchart.setBounds(220,250,100,37);
		addchart.setBackground(new Color(60, 167, 211, 255));
		addchart.addActionListener(e -> {
			//检测信息是否完整
			if(chartAuthorT.getText().equals("")){
				parentPanel.info.addInfo("未填写谱面作者",1, new ClickOp());
			}
			if(musicAuthorT.getText().equals("")){
				parentPanel.info.addInfo("未填写曲目作者",1,new ClickOp());
			}
			if(bpmT.getText().equals("")){
				parentPanel.info.addInfo("未填写bpm",2,new ClickOp());
				return;
			}else {
				try {
					Double.parseDouble(bpmT.getText());
				}catch (NumberFormatException ne){
					parentPanel.info.addInfo("请填写合法的数字作为bpm值",2,new ClickOp());
					return;
				}
			}
			//确认生成谱面
			close();
			String[] chartInfo = new String[]{chartAuthorT.getText(),musicAuthorT.getText(),bpmT.getText()};
			parentPanel.createChart(f, chartInfo);
		});
		add(addchart);
		bpmGet = new JButton("测量bpm");
		bpmGet.setFont(Editor.f);
		bpmGet.setBounds(250,160,100,25);
		bpmGet.setBackground(new Color(211, 186, 60, 255));
		bpmGet.addActionListener(e -> {
			//测量bpm喵
			parentPanel.info.addInfo("开始测量Bpm(最长" + BPM_MAX_TESTTIME + "ms)：",f.getName(),0, new ClickOp());
			bpmTest.start();
			bpmTestTimer.start();
			startTime = System.currentTimeMillis();
		});
		add(bpmGet);
	}

	public static String getBpm(String fileName){
		double re = 0;
		File f = new File("res/lib/getbpm.py");
		try {
			String[] args1 = new String[] {"python", f.getPath(), fileName};
			ProcessBuilder proc = new ProcessBuilder(args1);// 执行py文件
			proc.redirectErrorStream(true);
			process = proc.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				if(line.startsWith("Python Exception")){
					return line;
				}
				try {
					re = Double.parseDouble(line);
				}catch (NumberFormatException e){
					return "Python Exception"+line;
				}
				re = Double.parseDouble(line);
			}
			in.close();
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return String.format("%.2f",re);
	}
}
