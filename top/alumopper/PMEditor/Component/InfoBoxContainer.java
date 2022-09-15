package top.alumopper.PMEditor.Component;

import top.alumopper.PMEditor.Editor;

import java.awt.*;
import java.util.ArrayList;


/**
 * 储存了消息弹窗的一个容器。会在垂直方向上依次绘制其中的弹窗。弹窗之间的垂直间距为5
 */
public class InfoBoxContainer extends Panel {
    public boolean treadStop = false;
    private final ArrayList<InfoBox> infos;

    public InfoBoxContainer(int x,int y, int width, int height){
        infos = new ArrayList<>();
        this.setLayout(null);
        this.setBounds(x, y, width, height);
    }

    public void addInfo(String text, String text2, int type, ClickOp clickOp){
        infos.add(0,new InfoBox(text,text2,type).addClickOp(clickOp));
        this.add(infos.get(0));
        infos.get(0).container = this;
    }

    public void addInfo(String text,int type, ClickOp clickOp){
        infos.add(0,new InfoBox(text,"",type).addClickOp(clickOp));
        this.add(infos.get(0));
        infos.get(0).container = this;
    }

    public boolean removeInfo(InfoBox infoBox){
        int index = infos.indexOf(infoBox);
        if(index == -1){
            //如果此infoBox不存在于数组中
            return false;
        }else {
            this.remove(infoBox);
            infos.remove(index);
            return true;
        }
    }


    public boolean removeInfo(int index){
        if(index >= infos.size()){
            //超范围
            return false;
        }else {
            this.remove(infos.get(index));
            infos.remove(index);
            return true;
        }
    }

    public void update(){
        repaint();
        //清除过期弹窗
        for (int i = 0; i < infos.size(); i++) {
            if(infos.get(i).isOut()){
                removeInfo(i);
                i --;
            }
        }
        if(infos.isEmpty()) return;
        //如果刚刚有新消息加入
        if(infos.get(0).isIn() && infos.size() != 1){
            //第一个正常绘制
            infos.get(0).aniPos = new Point(0,getHeight()-InfoBox.height);
            infos.get(0).draw(null);
            //整体向上平移
            for (int i = 1; i < infos.size(); i++) {
                infos.get(i).aniPos = new Point(0,(getHeight()-InfoBox.height-(int)(((System.currentTimeMillis()/1000.0-infos.get(0).startTime)/infos.get(0).inTime+i-1)*(InfoBox.height+5))));
                infos.get(i).draw(null);
            }
        }else{
            for (int i = 0; i < infos.size(); i++) {
                //依次绘制
                infos.get(i).aniPos = new Point(0, getHeight()-i*(InfoBox.height+5)-InfoBox.height);
                infos.get(i).draw(null);
            }
        }
    }
}
