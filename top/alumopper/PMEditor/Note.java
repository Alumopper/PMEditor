package top.alumopper.PMEditor;

public class Note {
    public static final int TAP = 0;
    public static final int DRAG = 1;

    int key;
    int time;
    int type;
    float position;

    public Note(int key, int time, int type, float position) {
        this.key = key;
        this.time = time;
        this.type = type;
        this.position = position;
    }

    public Note(int key, int time, float position) {
        this.key = key;
        this.time = time;
        this.position = position;
    }
}
