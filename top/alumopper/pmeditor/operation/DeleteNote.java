package top.alumopper.pmeditor.operation;

import top.alumopper.pmeditor.*;
import top.alumopper.pmeditor.component.EditorPanel;

public class DeleteNote extends Operation {
    //删除了一个键
    final Note n;

    public DeleteNote(Note n){
        this.n = n;
    }

    @Override
    public void redo(EditorPanel ep) {
        super.redo(ep);
        //删掉
        ep.cr.delNote(n,ep.curLine);
    }

    @Override
    public void revoke(EditorPanel ep) {
        //重新放回去
        super.revoke(ep);
        ep.cr.addNote(n,ep.curLine);
    }
}
