
package net.devtrainer.foogl.action;

public class SequenceAction extends ParallelAction {
	public SequenceAction () {
		// TODO Auto-generated constructor stub
	}

	public SequenceAction (Action action1) {
		super(action1);
		// TODO Auto-generated constructor stub
	}

	public boolean act (float delta) {
		if (actions.size==0) return true;
		if (actions.get(0).act(delta)) {
			actions.removeIndex(0);
		}
		return false;

	}
}
