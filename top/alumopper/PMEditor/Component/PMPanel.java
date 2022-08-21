package top.alumopper.PMEditor.Component;

import javax.swing.*;
import java.awt.*;

public class PMPanel extends JPanel implements Runnable {

	public Frame fr;

	public PMPanel(Frame fr){
		this.fr = fr;
	}

	public void paint(Graphics g){}

	public void repaint(){
		//离屏绘制
		Image im = createImage(getWidth(),getHeight());
		if(im == null)
			return;  //特性避免
		Graphics dbg = im.getGraphics();
		paint(dbg);
		Graphics g = getGraphics();
		g.drawImage(im,0,0,null);
		g.dispose();
	}

	public void loop(){}

	public void run(){}

	public static boolean pointInRect(Point p,int x1,int y1,int x2,int y2){
		if(x1 <= p.x && p.x <= x2 && y1 <= p.y && p.y <= y2){
			return true;
		}else {
			return false;
		}
	}
}
