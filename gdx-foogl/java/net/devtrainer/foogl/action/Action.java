
package net.devtrainer.foogl.action;

import net.devtrainer.foogl.Scene;
import net.devtrainer.foogl.actor.Actor;
import net.devtrainer.foogl.actor.ParticleActor;

abstract public class Action {
	Actor actor;
    ParallelAction parent;

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

	/**
	 *
	 * @param delta
     * @return  true: finish, false: running
     */
	abstract public boolean act (float delta);

	public void restart () {

	}
	
}
