package top.alumopper.PMEditor.Operation;

import top.alumopper.PMEditor.Component.EditorPanel;

import java.util.ArrayList;

public class OperationManager {
    final ArrayList<Operation> opHistory;     //历史操作
    private int index;                  //操作步骤索引
    final EditorPanel ep;                     //编辑器
    private int orgIndex;               //未修改处的操作步骤索引

    public OperationManager(EditorPanel ep){
        this.ep = ep;
        opHistory = new ArrayList<>();
        index = -1;
        orgIndex = index;
    }

    public void addOp(Operation o){
        //去除不需要的历史操作
        int i = index+1;
        while (i < opHistory.size()){
            opHistory.remove(i);
            i++;
        }
        opHistory.add(o);
        index ++;
    }

    public void redo(){
        if(index+1<opHistory.size()){
            opHistory.get(index+1).redo(ep);
            index ++;
        }else {
            ep.info.addInfo("无更多操作可重做",1);
        }
    }

    public void revoke(){
        if(index >= 0){
            opHistory.get(index).revoke(ep);
            index --;
        }else {
            ep.info.addInfo("无更多操作可撤销",1);
        }
    }

    public boolean isChanged(){
        return orgIndex != index;
    }

    public void save(){
        orgIndex = index;
    }
}
