package top.alumopper.PMEditor;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.media.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 储存了一个音频的数据，用于播放音乐
 */
public class Song {
    /**
     * 曲子的bpm
     */
    public float bpm;
    /**
     * 曲目的位置
     */
    String file;
    /**
     *  更优的解决方案的播放器，需要依赖库
     */
    private final MediaPlayer mediaplayer;

    /**
     * 创建一个新的曲目实例
     * @param mediaPlayer 一个媒体文件
     * @param bpm 曲子的bpm
     * @throws IOException .
     * @throws NoPlayerException .
     * @throws CannotRealizeException .
     */
    public Song(MediaPlayer mediaPlayer, float bpm) throws IOException, NoPlayerException, CannotRealizeException {
        this.file = mediaPlayer.getMedia().getSource();
        this.mediaplayer = mediaPlayer;
        this.bpm = bpm;
    }

    public void dispose(){
        //释放资源
        mediaplayer.stop();
        mediaplayer.dispose();
    }

    /**
     * 播放音频
     */
    public void start(){
        mediaplayer.play();
    }

    /**
     * 暂停播放
     */
    public void pause(){
        mediaplayer.pause();
    }

    /**
     * 设置音频目前的时间
     * @param time 秒
     */
    public void setTime(float time){
        mediaplayer.setStartTime(new Duration(time * 1000.0));
    }

    /**
     * 设置音频播放速度
     * @param rate 倍率
     */
    public void setRate(float rate){
        mediaplayer.setRate(rate);
    }

    /**
     * 音频是否暂停
     * @return 如果暂停，返回true
     */
    public boolean isPaused(){
        return mediaplayer.getStatus().equals(MediaPlayer.Status.PAUSED);
    }

    /**
     * 音频是否开始播放
     * @return 如果正在播放，返回true
     */
    public boolean isStarted(){
        return mediaplayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    /**
     * 获取音频长度
     * @return 返回音频长度，单位s
     */
    public float getLength(){
        return (float)mediaplayer.getMedia().getDuration().toSeconds();
    }

    /**
     * 获取音频播放的倍率
     * @return 音频目前播放倍率
     */
    public float getRate(){
        return (float)mediaplayer.getRate();
    }

    /**
     * 获取现在音频所在的时间
     * @return 目前音频所在的时间，单位s
     */
    public float getTime(){
        return (float)mediaplayer.getCurrentTime().toSeconds();
    }

    /**
     * 根据所给文件路径创建一个新的Song实例
     * @param filePath 文件路径
     * @return 返回一个Song实例
     */
    public static Song createSong(String filePath, float bpm) throws CannotRealizeException, IOException, NoPlayerException {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File(filePath).toURI().toString()));
        return new Song(mediaPlayer,bpm);
    }
}
