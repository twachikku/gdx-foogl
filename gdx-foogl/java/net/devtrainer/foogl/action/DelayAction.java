package net.devtrainer.foogl.action;

public class DelayAction extends Action {
	float duration;
	float time=0;
	Action next;

	public DelayAction (float duration) {
		super();
		this.duration = duration;
	}

	public DelayAction (float duration, Action next) {
		super();
		this.duration = duration;
		this.next = next;
	}

	@Override
	public boolean act (float delta) {
		time += delta;
		if(time<duration) return false;
		
		if(next!=null) return next.act(delta);
		
		return process();
	}

	public boolean process () {
		return true;
	}

	@Override
	public void restart () {
		time=0;
		next.restart();
		super.restart();
	}
}
