package top.alumopper.PMEditor.Operation;

import top.alumopper.PMEditor.EditorPanel;

import java.util.ArrayList;

public class OperationManager {
    ArrayList<Operation> opHistory;     //历史操作
    private int index;                  //操作步骤索引
    EditorPanel ep;                     //编辑器

    public OperationManager(EditorPanel ep){
        this.ep = ep;
        opHistory = new ArrayList<>();
        index = -1;
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
}
