package top.alumopper.PMEditor.Operation;

import top.alumopper.PMEditor.EditorPanel;
import top.alumopper.PMEditor.Note;

public class PutNote extends Operation {
	//放置了一个键
	Note n;

	public PutNote(Note n){
		this.n = n;
	}

	@Override
	public void redo(EditorPanel ep) {
		//删除
		super.redo(ep);
		ep.delNote(n);
	}

	@Override
	public void revoke(EditorPanel ep) {
		super.revoke(ep);
		ep.putNote(n);
	}
}
