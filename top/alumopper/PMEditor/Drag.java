package top.alumopper.PMEditor;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import java.io.File;
import java.io.IOException;

/**
 * drag，即黄键
 */
public class Drag extends Note {

    /**
     * 创建一个drag
     * @param key 轨道序号
     * @param time note被击打的时间
     */
    public Drag(int key, double time) {
        super(key, time, Note.DRAG);
        try {
            effect = Manager.createRealizedPlayer(new File("./res/media/drag.wav").toURI().toURL());
            effect.prefetch();
        } catch (IOException | NoPlayerException | CannotRealizeException e) {
            e.printStackTrace();
        }
    }
}
