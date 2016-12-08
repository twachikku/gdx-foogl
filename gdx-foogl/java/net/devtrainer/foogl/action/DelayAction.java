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
		setNext(next);
	}

	@Override
	public boolean act (float delta) {               
		time += delta;
		if(time<duration) return false;
		
		if(next!=null){
                    if(next.actor==null) next.actor = this.actor;
                    if(!next.act(delta)) return false;
                }		
		return process();
	}

	public Action getNext() {
		return next;
	}

	public void setNext(Action next) {
		if(next.parent!=null){
		   next.parent.removeAction(next);
		}
		if(next.actor==null) next.actor = this.actor;
		this.next = next;
	}

	public boolean process () {
		return true;
	}

	@Override
	public void restart () {
		time=0;
		if(next!=null) next.restart();
		super.restart();
	}
}
