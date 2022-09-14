package top.alumopper.PMEditor;

import java.util.ArrayList;

/**
 * 	谱面文件数据储存，与json格式相匹配
 */
public class Chart {
	/**
	 *谱面所用音乐信息
	 */
	public static class SongInfo{
		/**
		 * 谱面使用的wav文件的名字
		 */
		public String name;
		/**
		 * wav文件的长度<br>
		 * 曲目长度的读取直接由{@link ChartReader}读取
		 * @deprecated
		 */
		@Deprecated
		public float time;
		/**
		 * 曲目的bpm
		 */
		public float bpm;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getTime() {
			return time;
		}

		public void setTime(float time) {
			this.time = time;
		}

		public float getBpm() {
			return bpm;
		}

		public void setBpm(float bpm) {
			this.bpm = bpm;
		}
	}

	/**
	 * 判定线
	 */
	public ArrayList<Line> lines;
	/**
	 * 一个储存了曲目信息的实例
	 */
	public SongInfo song;

	public ArrayList<Line> getLines() {
		return lines;
	}

	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}

	public SongInfo getSong() {
		return song;
	}

	public void setSong(SongInfo song) {
		this.song = song;
	}

}
