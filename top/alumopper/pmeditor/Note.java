package top.alumopper.pmeditor;

import com.alibaba.fastjson.annotation.JSONField;

import javax.media.Player;
import java.util.Objects;

/**
 * 所有note的超类
 * @see Drag
 * @see Tap
 */
public class Note {
    /**
     * 表示此note为tap
     */
    public static final int TAP = 0;
    /**
     * 表示此note为drag
     */
    public static final int DRAG = 1;

    /**
     * note所在的轨道标号
     */
    public int key;
    /**
     * note应当被判定的时间
     */
    public double time;
    /**
     * note的种类
     */
    public int type;
    @JSONField(serialize = false)
    public Player effect;   //打击音效
    @JSONField(serialize = false)
    public boolean judged;  //已经被判定

    /**
     * 创建一个note。此note已确定类型
     * @param key note所在的轨道标号
     * @param time note应当被判定的时间
     * @param type note的种类
     */
    public Note(int key, double time, int type) {
        this.key = key;
        this.time = time;
        this.type = type;
    }

    /**
     * 创建一个不明类型的note
     * @param key note所在的轨道标号
     * @param time note应当被判定的时间
     */
    public Note(int key, double time) {
        this.key = key;
        this.time = time;
    }

    public Note(){}

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

    /**
     * 判断两个note是否相同。不考虑note种类
     * @param o 被比较的note
     * @return 若相同则返回true
     */
    public boolean equals(Note o) {
        if (this == o) return true;
        if (o == null) return false;
        return key == o.key && Double.compare(o.time, time) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, time, type);
    }

    /**
     * 检测这个tap是否非法。
     * 当一个tap处在主轨道以外的轨道上时，此tap非法
     * @return 如果非法，返回true
     */
    @JSONField(serialize = false)
    public boolean isIllegalTap(){
        if(this.type == TAP){
            return this.key % 2 == 0;
        }
        return false;
    }

    /**
     * 将此note转换为nbt文本
     * @return 此note对应的nbt文本
     */
	public String toNBTData(){
        return "{key:"+ key +",time:" + time + ",type:" + type + "}";
    }
}
