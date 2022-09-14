package top.alumopper.PMEditor;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import java.io.File;
import java.io.IOException;

/**
 * tap，即蓝键
 */
public class Tap extends Note{

    /**
     * 创建一个tap
     * @param key 轨道序号
     * @param time note被击打的时间
     */
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
