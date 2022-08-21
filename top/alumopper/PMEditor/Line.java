package top.alumopper.PMEditor;

import java.util.ArrayList;

public class Line {
    public ArrayList<Note> notes = new ArrayList<>();
    float speed;    //速度：12像素/s

    public Line(float speed){
        this.speed = speed;
    }
}
