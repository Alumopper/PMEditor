package top.alumopper.pmeditor;

//绘制Editor

public class EditorDrawer implements Runnable {
	private final static int FPS = 240;

	private boolean ifLoop;

	public EditorDrawer(){
		ifLoop = true;
	}

	@Override
	public void run() {
		long lastTime = System.currentTimeMillis();
		long i;
		while (ifLoop){
			i = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			try {
				if(i < 1000/FPS)
					Thread.sleep(1000/FPS - i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Editor.currFrame != null){
				Editor.currFrame.draw();
			}
		}
	}
}
