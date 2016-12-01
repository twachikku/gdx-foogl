
package net.devtrainer.foogl.action;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import net.devtrainer.foogl.actor.Actor;

public class ParallelAction extends Action {
	Array<Action> actions = new Array(4);
	private boolean complete;

	public ParallelAction () {
	}

	public ParallelAction (Action action1) {
		addAction(action1);
	}

	public boolean act (float delta) {
		if (complete) return true;
		complete = true;
		for (int i = 0; i < actions.size; i++) {
			Action currentAction = actions.get(i);
			if (!currentAction.act(delta)){
				complete = false;
			}else{
				actions.removeIndex(i);
				i--;
			}
			if (actor == null) return true; // This action was removed.
			
		}
		return complete;
	}

	public void restart () {
		complete = false;
		Array<Action> actions = this.actions;
		for (int i = 0, n = actions.size; i < n; i++)
			actions.get(i).restart();
	}

	public void reset () {
		actions.clear();
	}

	public void addAction (Action action) {
		actions.add(action);
		if (actor != null) action.setActor(actor);
	}

	public void setActor (Actor actor) {
		Array<Action> actions = this.actions;
		for (int i = 0, n = actions.size; i < n; i++)
			actions.get(i).setActor(actor);
		super.setActor(actor);
	}

	public Array<Action> getActions () {
		return actions;
	}

	public String toString () {
		StringBuilder buffer = new StringBuilder(64);
		buffer.append(super.toString());
		buffer.append('(');
		Array<Action> actions = this.actions;
		for (int i = 0, n = actions.size; i < n; i++) {
			if (i > 0) buffer.append(", ");
			buffer.append(actions.get(i));
		}
		buffer.append(')');
		return buffer.toString();
	}
}
