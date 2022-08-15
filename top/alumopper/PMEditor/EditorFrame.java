package top.alumopper.PMEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class EditorFrame extends JFrame {

	EditorPanel ep;

	public EditorFrame(String s){
		super(s);
	}

	public void addEp(EditorPanel ep){
		this.add(ep);
		this.ep = ep;
	}

	@Override
	protected void processWindowEvent(WindowEvent e){
		if(ep.notSaved && !ep.pressCtrl && e.getID() == WindowEvent.WINDOW_CLOSING){
			ep.info.addInfo("更改未保存！更改将丢失","按住ctrl强行关闭",1);
		}else{
			super.processWindowEvent(e);
		}
	}
}
