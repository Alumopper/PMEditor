package top.alumopper.PMEditor.Operation;

import top.alumopper.PMEditor.*;

public class DeleteNote extends Operation {
    //删除了一个键
    Note n;

    public DeleteNote(Note n){
        this.n = n;
    }

    @Override
    public void redo(EditorPanel ep) {
        //重新放回去
        super.redo(ep);
        ep.putNote(n);
    }

    @Override
    public void revoke(EditorPanel ep) {
        super.revoke(ep);
        //删掉
        ep.delNote(n);
    }
}
