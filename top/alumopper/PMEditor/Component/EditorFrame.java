package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Editor;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class EditorFrame extends JFrame {

	EditorPanel ep;
	SelectPanel sp;

	public EditorFrame(String s){
		super(s);
	}

	public void addEp(EditorPanel ep){
		this.add(ep);
		this.ep = ep;
	}
	public void addSp(SelectPanel sp){
		this.add(sp);
		this.sp = sp;
	}

	@Override
	protected void processWindowEvent(WindowEvent e){
		super.processWindowEvent(e);
//		if(ep.notSaved && !ep.pressCtrl && e.getID() == WindowEvent.WINDOW_CLOSING){
//			ep.info.addInfo("更改未保存！更改将丢失","按住ctrl强行关闭",1);
//		}else{
//		}
	}
}
