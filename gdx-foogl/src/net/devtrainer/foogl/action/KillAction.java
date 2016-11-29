package net.devtrainer.foogl.action;

import net.devtrainer.foogl.actor.Actor;

public class KillAction extends DelayAction {
   Actor target;

	public KillAction (float duration, Actor target) {
		super(duration);
		this.target = target;
	}

	@Override
	public boolean process () {
		if(target==null) target=actor;
		target.kill();
		return true;
	}
	
}
