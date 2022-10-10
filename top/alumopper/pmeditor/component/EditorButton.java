package top.alumopper.pmeditor.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 编辑器用的按钮
 * @deprecated 此方案未被采用
 */
@Deprecated
public class EditorButton extends JButton {

	private final Color c1;	//鼠标放上去前
	private final Color c2;	//鼠标放上去后
	private final Color c3;	//鼠标按下去后
	private boolean hover;	//鼠标有没有放上去

	public EditorButton(String text){
		super.setText(text);
		c1 = new Color(229, 229, 229, 255);
		c2 = c1.brighter();
		c3 = c1.darker();
		setContentAreaFilled(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();

		super.paintComponent(g);
	}
}
