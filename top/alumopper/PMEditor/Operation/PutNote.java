package top.alumopper.PMEditor.Operation;

import top.alumopper.PMEditor.Component.EditorPanel;
import top.alumopper.PMEditor.Note;

public class PutNote extends Operation {
	//放置了一个键
	final Note n;

	public PutNote(Note n){
		this.n = n;
	}

	@Override
	public void redo(EditorPanel ep) {
		super.redo(ep);
		ep.cr.addNote(n,ep.curLine);
	}

	@Override
	public void revoke(EditorPanel ep) {
		//删除
		super.revoke(ep);
		ep.cr.delNote(n,ep.curLine);
	}
}
