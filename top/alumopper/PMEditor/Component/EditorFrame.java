package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Editor;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * 制谱器窗口
 * @see EditorPanel
 * @see SelectPanel
 */
public class EditorFrame extends JFrame {

	/**
	 * 一个编辑器面板
	 */
	EditorPanel ep;
	/**
	 * 一个选曲页面面板
	 */
	SelectPanel sp;

	/**
	 * 创建一个空的指定标题的窗口
	 * @param s 窗口标题
	 */
	public EditorFrame(String s){
		super(s);
	}

	/**
	 * 向此窗口添加一个编辑器
	 * @param ep 一个编辑器
	 */
	public void addEp(EditorPanel ep){
		this.add(ep);
		this.ep = ep;
	}

	/**
	 * 向此窗口添加一个选曲器
	 * @param sp 一个选曲器
	 */
	public void addSp(SelectPanel sp){
		this.add(sp);
		this.sp = sp;
	}

	@Override
	protected void processWindowEvent(WindowEvent e){
		if(sp == null && e.getID() == WindowEvent.WINDOW_CLOSING){
			//为editor
			ep.cr.song.songPlayer.close();
			Editor.main(new String[]{"qwq"});
			this.dispose();
		}else {
			super.processWindowEvent(e);
		}
//		if(ep.notSaved && !ep.pressCtrl && e.getID() == WindowEvent.WINDOW_CLOSING){
//			ep.info.addInfo("更改未保存！更改将丢失","按住ctrl强行关闭",1);
//		}else{
//		}
	}
}
