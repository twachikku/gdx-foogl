
package net.devtrainer.foogl.action;

import java.awt.event.ActionListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

import net.devtrainer.foogl.actor.Actor;

public class ActionBuilder {
	
	static public final int PARALLEL = 1;;
	static public final int SEQUENCE = 1;;
	private Actor actor;
	private int mode = -1;
	private ParallelAction root=new ParallelAction();
	private ParallelAction actions=root;

	public ActionBuilder (Actor owner) {
		super();
		root.actor = owner;
		this.actor = owner;
	}
	public void act(float delta){
		root.act(delta);
	}
   
	public ColorAction color (Color color, float duration, Interpolation tween) {
		ColorAction a=new ColorAction();
		a.setColor(color);
		a.setDuration(duration);
		a.setInterpolation(tween);
		return add(a);
	}

	public SoundAction playsound (String name) {
		return add(new SoundAction(name));
	}
	public BlinkAction blink (float duration, float inteval, Color color) {
		return add(new BlinkAction(duration, inteval, color));
	}

	public BlinkAction blink (float duration, float inteval) {
		return add(new BlinkAction(duration, inteval, Color.RED));
	}

	public AlphaAction alpha (float alpha, float duration) {
		AlphaAction a=new AlphaAction();
		a.setAlpha(alpha);
		a.setDuration(duration);
		return add(a);
	}

	public MoveToAction move (float x, float y, float duration, Interpolation tween) {
		MoveToAction a=new MoveToAction();
		a.setPosition(x, y);
		a.setDuration(duration);
		a.setInterpolation(tween);
		return add(a);
	}

	public ScaleAction scale (float sx, float sy, float duration, Interpolation tween) {
		ScaleAction a=new ScaleAction(duration,tween);
		a.setScale(sx,sy);
		return add(a);
	}

	public RotateToAction rotate (float degree, float duration, Interpolation tween) {
		RotateToAction a=new RotateToAction();
		a.setRotation(degree);
		a.setDuration(duration);
		a.setInterpolation(tween);		
		return add(a);
	}

	public AlphaAction fadeOut (float duration) {
		return add(alpha(duration,0));
	}

	public AlphaAction fadeIn (float duration) {
		return add(alpha(duration,1));
	}

	public KillAction kill (float duration) {		
		return kill(duration,null);
	}
	public KillAction kill (float duration, Actor target) {		
		return add(new KillAction(duration, target));
	}

	public DelayAction delay (float wait) {
		if (mode != SEQUENCE) setMode(SEQUENCE);
		return add(new DelayAction(wait));
	}
	public LoopAction loop (float delay, Action next) {
		return add(new LoopAction(delay,next));
	}
	public LoopAction loop (float delay) {
		return add(new LoopAction(delay));
	}
	public RepeatAction repeat (float delay,int count,Action next) {
		return add(new RepeatAction(delay,count,next));
	}
	public RepeatAction repeat (float delay,int count) {
		return add(new RepeatAction(delay,count,null));
	}
	
	/** add action to actor and return the action.
	 * @param a
	 * @return */
	public <T> T add (Action a) {
		a.setActor(actor);
		a.setScene(actor.scene);
		actions.addAction(a);
		return (T)a;
	}

	public Actor getActor () {
		return actor;
	}

	public int getMode () {
		return mode;
	}

	public SequenceAction sequence () {
		setMode(SEQUENCE);
		return (SequenceAction)actions;
	}

	public ParallelAction paralle () {
		setMode(PARALLEL);
		return actions;
	}

	private void setMode (int mode) {
		if (this.mode != mode) {
			if (mode == SEQUENCE) {
				SequenceAction s = new SequenceAction();
				actions.addAction(s);
				actions=s;
			} else {
				ParallelAction p = new ParallelAction();
				actions.addAction(p);
				actions = p;
			}
		}
		this.mode = mode;
	}

}
