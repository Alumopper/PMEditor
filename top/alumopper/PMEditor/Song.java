package top.alumopper.PMEditor;


import javax.media.*;
import java.io.File;
import java.io.IOException;

/**
 * 储存了一个音频的数据，用于播放音乐
 */
public class Song {
    /**
     * 音乐播放器
     */
    public Player songPlayer;
    /**
     * 曲子的bpm
     */
    public float bpm;

    /**
     * 创建一个新的曲目实例
     * @param file wav文件路径
     * @param bpm 曲子的bpm
     * @throws IOException .
     * @throws NoPlayerException .
     * @throws CannotRealizeException .
     */
    public Song(String file,float bpm) throws IOException, NoPlayerException, CannotRealizeException {
        this.songPlayer = Manager.createRealizedPlayer(new File(file).toURI().toURL());
        songPlayer.prefetch();
        //this.songPlayer.setRate(1f);
        this.bpm = bpm;
    }
}
