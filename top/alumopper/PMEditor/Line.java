package top.alumopper.PMEditor;

import java.util.ArrayList;

public class Line {
    public ArrayList<Note> notes;
    public float speed;    //速度：12像素/s

    public Line(float speed){
        this.speed = speed;
        this.notes = new ArrayList<>();
    }

    public Line(){}

    public void addNote(Note n){
        for (int i = 0; i < notes.size()-1; i++) {
            if(n.time > notes.get(i).time && n.time > notes.get(i+1).time){
                notes.add(i+1,n);
                return;
            }
        }
        notes.add(n);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
