package top.alumopper.PMEditor.Component;

/**
 * 当一个信息框被按下后，需要执行的操作<br>
 * 继承此类并复写方法以实现功能
 * @see InfoBox
 */
public class ClickOp {
	/**
	 * 此ClickOp实例绑定的信息框
	 */
	public InfoBox infoBox;

	/**
	 * 在信息框被点击后，将会执行的某一个操作。重写此方法以用于实现自己的功能。默认点击后使信息框消失
	 */
	public void afterClick(){
		if(!infoBox.isOut()){
			infoBox.time -= infoBox.time - infoBox.outTime - infoBox.currTime;
		}
	}
}
