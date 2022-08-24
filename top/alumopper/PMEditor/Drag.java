package top.alumopper.PMEditor;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import java.io.File;
import java.io.IOException;

public class Drag extends Note {

    public Drag(int key, double time) {
        super(key, time, Note.DRAG);
        try {
            effect = Manager.createRealizedPlayer(new File("./res/media/drag.wav").toURI().toURL());
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
