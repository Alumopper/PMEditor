package top.alumopper.pmeditor;

import com.alibaba.fastjson.annotation.JSONField;

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
		 * 曲目长度的读取直接由{@link ChartManager}读取并储存在此
		 */
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

		public String toNBTData(){
			return "{bpm:" + bpm + ",name:\"" + name + "\",time:" + time + "}";
		}

		@JSONField(serialize = false)
		public String getLegalName(){
			StringBuilder n = new StringBuilder("");
			for (char c : name.toCharArray()) {
				if( 97 <= c && c <= 122 || c == 95){
					n.append(c);
				}
			}
			return n.toString();
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

	public String toNBTData(){
		//{lines:[{notes:[{illegalTap:true,key:4,time:0.9593022912740707,type:0}],speed:2.0}],song:{bpm:172.0,name:"Faeth.wav",time:229.53}}
		StringBuilder re = new StringBuilder("{lines:[");
		for (int i = 0; i < lines.size(); i++) {
			re.append(lines.get(i).toNBTData());
			if(i + 1 < lines.size()){
				re.append(",");
			}
		}
		re.append("],song:").append(song.toNBTData()).append("}");
		return re.toString();
	}

}
