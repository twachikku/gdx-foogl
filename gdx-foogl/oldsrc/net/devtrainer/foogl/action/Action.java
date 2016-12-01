
package net.devtrainer.foogl.action;

import net.devtrainer.foogl.Scene;
import net.devtrainer.foogl.actor.Actor;

abstract public class Action {
	Actor actor;
	protected Scene scene;

	public Action () {
	}

	public Actor getActor () {
		return actor;
	}

	public Scene getScene () {
		return scene;
	}

	public void setScene (Scene scene) {
		this.scene = scene;
	}

	public void setActor (Actor actor) {
		this.actor = actor;
	}

	abstract public boolean act (float delta);

	public void restart () {

	}
	
}
