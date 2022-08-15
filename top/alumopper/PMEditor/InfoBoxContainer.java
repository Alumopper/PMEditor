package top.alumopper.PMEditor;

import java.awt.*;
import java.util.ArrayList;


/**
 * 储存了消息弹窗的一个容器。会在垂直方向上依次绘制其中的弹窗。弹窗之间的垂直间距为5
 */
public class InfoBoxContainer {
    private ArrayList<InfoBox> infos;
    private Point pos;  //是左下角的位置

    public InfoBoxContainer(Point pos){
        infos = new ArrayList<>();
        this.pos = pos;
    }

    public InfoBoxContainer(int x,int y){
        infos = new ArrayList<>();
        this.pos = new Point(x,y);
    }

    public void draw(Graphics2D g){
        //绘制所有的消息弹窗
        update();
        if(infos.isEmpty()) return;
        //如果刚刚有新消息加入
        if(infos.get(0).isIn() && infos.size() != 1){
            //第一个正常绘制
            infos.get(0).aniPos = pos;
            infos.get(0).draw(g);
            //整体向上平移
            for (int i = 1; i < infos.size(); i++) {
                Point curPos = new Point(pos.x,(pos.y-(int)(((System.currentTimeMillis()/1000.0-infos.get(0).startTime)/infos.get(0).inTime+i-1)*(InfoBox.height+5))));
                infos.get(i).aniPos = curPos;
                infos.get(i).draw(g);
            }
        }else{
            for (int i = 0; i < infos.size(); i++) {
                //依次绘制
                Point curPos = new Point(pos.x, pos.y-i*(InfoBox.height+5));
                infos.get(i).aniPos = curPos;
                infos.get(i).draw(g);
            }
        }
    }

    public void addInfo(String text, String text2,int type){
        infos.add(0,new InfoBox(type,text,text2));
    }

    public void addInfo(String text,int type){
        infos.add(0,new InfoBox(type,text,""));
    }

    public void update(){
        //清除过期弹窗
        for (int i = 0; i < infos.size(); i++) {
            if(infos.get(i).isOut()){
                infos.remove(i);
            }
        }
    }
}
