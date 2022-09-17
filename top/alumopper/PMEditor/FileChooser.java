package top.alumopper.PMEditor;

import com.sun.jna.Library;
import com.sun.jna.Native;
import java.io.File;

/**
 * 用于替代java swing中的{@link javax.swing.JFileChooser}的类<br>
 * 关键函数来自lib中的ChooseFile.dll文件，由C++实现
 * 可能存在内存问题<br>
 * 在创建一个新的FileChooser实例以后，会创建一个窗口用于选择文件。
 * 在文件的选择过程中，程序的执行将被阻塞，直到文件选择完毕。
 * 选择所得的文件将会储存在file变量中，并通过方法getFilePath获取
 */
public class FileChooser {

	private final String file;

	public FileChooser(){
		System.setProperty("jna.encoding","GBK");
		file = DLL.dll.chooseFiles();
	}

	/**
	 * 获取此FileChooser选择的文件的绝对路径
	 * @return 此FileChooser选择的文件的绝对路径
	 */
	public String getFilePath(){
		return file;
	}

	/**
	 * 获取此FileChooser选择的文件实例
	 * @return 此FileChooser选择的文件实例
	 */
	public File getFile(){
		return new File(file);
	}
}

/**
 * JNA调用c++编写的dll动态链接库
 */
interface DLL extends Library {

	DLL dll = Native.load("res/lib/ChooseFiles.dll",DLL.class);
	String chooseFiles();

}