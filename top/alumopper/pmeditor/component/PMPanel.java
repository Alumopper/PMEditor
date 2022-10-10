package top.alumopper.pmeditor.component;

import javax.swing.*;
import java.awt.*;

public class PMPanel extends JPanel implements Runnable {

	public final Frame fr;
	public final InfoBoxContainer info;

	public PMPanel(Frame fr){
		this.fr = fr;
		info = new InfoBoxContainer(650,250,250,300);
		this.add(info);
	}

	public void draw(){}

	public void loop(){}

	public void run(){}

	public static boolean pointInRect(Point p,int x1,int y1,int x2,int y2){
		return x1 <= p.x && p.x <= x2 && y1 <= p.y && p.y <= y2;
	}
}
