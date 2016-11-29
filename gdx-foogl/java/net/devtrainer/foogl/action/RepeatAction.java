package net.devtrainer.foogl.action;

public class RepeatAction extends LoopAction {
	int count;
	int n=0;

	public RepeatAction (float duration,int count, Action next) {
		super(duration, next);
		this.count = count;
	}
	public boolean process () {
		time=0;
		n++;
		return n<count;
	}

}
