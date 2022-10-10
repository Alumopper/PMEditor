package top.alumopper.pmeditor;

import java.util.ArrayList;

/**
 * 判定线
 */
public class Line {
    /**
     * 此判定线上的note
     */
    public ArrayList<Note> notes;
    /**
     * 判定线上note的移动速度
     */
    public float speed;    //速度：12像素/s

    /**
     * 创建一条判定线
     * @param speed note速度
     */
    public Line(float speed){
        this.speed = speed;
        this.notes = new ArrayList<>();
    }

    public Line(){}

    /**
     * 向此判定线添加一个note。此方法不会检测note是否重叠
     * @param n 要添加的note
     */
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

    /**
     * 将此line转换为nbt文本
     * @return 此line对应的nbt文本
     */
    public String toNBTData(){
        //{notes:[{illegalTap:true,key:4,time:0.9593022912740707,type:0}],speed:2.0}
        StringBuilder re = new StringBuilder("{notes:[");
        for (int i = 0; i < notes.size(); i++) {
            re.append(notes.get(i).toNBTData());
            if(i + 1 < notes.size()){
                re.append(",");
            }
        }
        re.append("],speed:").append(speed).append("}");
        return re.toString();
    }
}
