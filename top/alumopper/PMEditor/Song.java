package top.alumopper.PMEditor;


import javax.media.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Song {
    public Player songPlayer;
    public float bpm;

    public Song(String file,float bpm) throws IOException, NoPlayerException, CannotRealizeException {
        this.songPlayer = Manager.createRealizedPlayer(new File(file).toURI().toURL());
        this.bpm = bpm;
    }
}
