package top.alumopper.PMEditor;


import uk.co.caprica.vlcj.player.base.State;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.media.*;
import java.io.File;
import java.io.IOException;


/**
 * 储存了一个音频的数据，用于播放音乐
 */
public class Song {
    /**
     * 默认音乐播放器
     * @deprecated
     */
    @Deprecated
    public Player songPlayer;
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
     *  别用这个，拉
     */
    private AudioPlayerComponent mediaplayer;
    /**
     * 是否采用VLCJ方法播放音频
     */
    private boolean ifVLCJ;

    /**
     * 创建一个新的曲目实例
     * @param file wav文件路径
     * @param bpm 曲子的bpm
     * @throws IOException .
     * @throws NoPlayerException .
     * @throws CannotRealizeException .
     */
    public Song(String file,float bpm) throws IOException, NoPlayerException, CannotRealizeException {
        this.file = file;
        if(!true){
            try{
                mediaplayer = new AudioPlayerComponent();
                mediaplayer.mediaPlayer().media().startPaused(file);
                mediaplayer.mediaPlayer().media().parsing().parse();
                ifVLCJ = false;
            }catch (Exception e){
                this.songPlayer = Manager.createRealizedPlayer(new File(file).toURI().toURL());
                songPlayer.prefetch();
                ifVLCJ = false;
            }
        }else {
            this.songPlayer = Manager.createRealizedPlayer(new File(file).toURI().toURL());
            songPlayer.prefetch();
            ifVLCJ = false;
        }
        this.bpm = bpm;
    }

    protected void finalize(){
        //释放资源
        if(songPlayer != null){
            songPlayer.close();
        }
        if(mediaplayer != null){
            pause();
            mediaplayer.release();
        }
    }

    /**
     * 播放音频
     */
    public void start(){
        if(ifVLCJ){
            mediaplayer.mediaPlayer().controls().play();
        }else {
            songPlayer.start();
        }
    }

    /**
     * 暂停播放
     */
    public void pause(){
        if(ifVLCJ){
            if(isStarted()){
                mediaplayer.mediaPlayer().controls().pause();
            }
        }else {
            songPlayer.stop();
        }
    }

    /**
     * 设置音频目前的时间
     * @param time 秒
     */
    public void setTime(float time){
        if(ifVLCJ){
            mediaplayer.mediaPlayer().controls().setTime((int)time* 1000L);
        }else {
            songPlayer.setMediaTime(new Time(time));
        }
    }

    /**
     * 设置音频播放速度
     * @param rate 倍率
     */
    public void setRate(float rate){
        if(ifVLCJ){
            mediaplayer.mediaPlayer().controls().setRate(rate);
        }else {
            songPlayer.setRate(rate);
        }
    }

    /**
     * 音频是否暂停
     * @return 如果暂停，返回true
     */
    public boolean isPaused(){
        if(ifVLCJ){
            return mediaplayer.mediaPlayer().status().state().equals(State.PAUSED);
        }else {
            return songPlayer.getState() != Player.Started;
        }
    }

    /**
     * 音频是否开始播放
     * @return 如果正在播放，返回true
     */
    public boolean isStarted(){
        if(ifVLCJ){
            return !mediaplayer.mediaPlayer().status().state().equals(State.PAUSED);
        }else {
            return songPlayer.getState() == Player.Started;
        }
    }

    /**
     * 获取音频长度
     * @return 返回音频长度，单位s
     */
    public float getLength(){
        if(ifVLCJ){
            return mediaplayer.mediaPlayer().status().length()/1000.0f;
        }else {
            return (float) songPlayer.getDuration().getSeconds();
        }
    }

    /**
     * 获取音频播放的倍率
     * @return 音频目前播放倍率
     */
    public float getRate(){
        if(ifVLCJ){
            return mediaplayer.mediaPlayer().status().rate();
        }else {
            return songPlayer.getRate();
        }
    }

    /**
     * 获取现在音频所在的时间
     * @return 目前音频所在的时间，单位s
     */
    public float getTime(){
        if(ifVLCJ){
            return mediaplayer.mediaPlayer().status().time()/1000.0f;
        }else {
            return (float)songPlayer.getMediaTime().getSeconds();
        }
    }
}
