package top.alumopper.PMEditor;

import java.awt.*;
import java.util.Objects;

public class Note {
    public static final int TAP = 0;
    public static final int DRAG = 1;

    public int key;
    public double time;
    public int type;

    public Note(int key, double time, int type) {
        this.key = key;
        this.time = time;
        this.type = type;
    }

    public Note(int key, double time) {
        this.key = key;
        this.time = time;
    }

    public Note(){}

    public void draw(Graphics2D g) {}

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return key == note.key && Double.compare(note.time, time) == 0 && type == note.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, time, type);
    }
}
