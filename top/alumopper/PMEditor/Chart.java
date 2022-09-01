package top.alumopper.PMEditor;

import java.util.ArrayList;

public class Chart {
	public static class SongInfo{
		public String name;
		public float time;
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

	public ArrayList<Line> lines;
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
