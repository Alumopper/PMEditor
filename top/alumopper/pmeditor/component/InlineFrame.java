package top.alumopper.pmeditor.component;

import top.alumopper.pmeditor.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 程序内窗口，用于显示信息，进行提示输入等等。可以实现和普通窗口相近的功能
 */
public class InlineFrame extends JPanel {
	private Point startDragPoint;

	public PMPanel parentPanel;

	private final JLabel closeX;

	public InlineFrame(PMPanel jPanel){
		this.setLayout(null);
		closeX = new JLabel("X");
		closeX.setBounds(this.getBounds().width-20,4,16,16);
		closeX.setFont(Editor.f);
		closeX.setForeground(Color.white);
		closeX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				closeX.setForeground(new Color(224, 179, 68, 255).darker());
				close();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				closeX.setForeground(new Color(224, 179, 68, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseEntered(e);
				closeX.setForeground(Color.white);
			}
		});
		add(closeX);
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				startDragPoint = e.getPoint();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			//窗口的拖动
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				InlineFrame.this.setLocation(InlineFrame.this.getX()+e.getX()-startDragPoint.x, InlineFrame.this.getY()+e.getY()-startDragPoint.y);
			}
		});
		parentPanel = jPanel;
		parentPanel.add(this,0);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				closeX.setBounds(InlineFrame.this.getBounds().width-20,4,16,16);
			}
		});
	}

	public void close(){
		setVisible(false);
		parentPanel.remove(this);
	}
}
