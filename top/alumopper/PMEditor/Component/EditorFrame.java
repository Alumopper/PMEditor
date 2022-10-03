package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * 制谱器窗口
 * @see EditorPanel
 * @see SelectPanel
 */
public class EditorFrame extends JFrame {

	/**
	 * 一个编辑器面板
	 */
	public EditorPanel ep;
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
			if(ep.opm.isChanged() && !ep.pressCtrl){
				ep.info.addInfo("更改未保存！更改将丢失","按ctrl强行关闭",1, new ClickOp());
			}else{
				ep.cr.song.dispose();
				Editor.main(new String[]{"qwq"});
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(ep.k);
				this.dispose();
				Thread.currentThread().interrupt();
			}
		}else {
			super.processWindowEvent(e);
		}
	}

	public void addInfo(String text1, String text2, int type, ClickOp clickOp){
		if(sp != null){
			sp.info.addInfo(text1, text2, type, clickOp);
		}
		if(ep != null){
			ep.info.addInfo(text1, text2, type, clickOp);
		}
	}

	public void draw(){
		//绘制此窗口的EditorComponent
		if(sp != null){
			sp.draw();
		}
		if(ep != null){
			ep.draw();
		}
	}

	public void addInfoFrom(InfoBoxContainer i){
		if(sp != null){
			sp.info.addInfoFrom(i);
		}
		if(ep != null){
			ep.info.addInfoFrom(i);
		}
	}
}
