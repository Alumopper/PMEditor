package top.alumopper.PMEditor;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import java.io.File;
import java.io.IOException;

public class Tap extends Note{

    public Tap(int key, double time) {
        super(key, time, Note.TAP);
        try {
            effect = Manager.createRealizedPlayer(new File("./res/media/tap.wav").toURI().toURL());
            effect.prefetch();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoPlayerException e) {
            e.printStackTrace();
        } catch (CannotRealizeException e) {
            e.printStackTrace();
        }
    }
}
