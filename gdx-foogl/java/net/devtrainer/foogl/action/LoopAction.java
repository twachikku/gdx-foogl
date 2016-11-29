package net.devtrainer.foogl.action;

public class LoopAction extends DelayAction {
   boolean stopped=false;
   
	public LoopAction (float duration) {
		super(duration);
	}
	public LoopAction (float duration, Action next) {
		super(duration, next);		
	}
	public void stop(){
		stopped=true;
	}
	public boolean process () {
		time=0;
		if(next!=null) next.restart();
		return stopped;
	}
}
