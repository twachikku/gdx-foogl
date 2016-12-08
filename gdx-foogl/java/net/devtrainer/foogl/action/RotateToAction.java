package net.devtrainer.foogl.action;

import com.badlogic.gdx.math.Interpolation;

public class RotateToAction extends TemporalAction {

	public RotateToAction () {
		// TODO Auto-generated constructor stub
	}

	public RotateToAction (float duration,float angle) {
		super(duration);
		end = angle;
	}

	public RotateToAction (float duration,float angle, Interpolation interpolation) {
		super(duration, interpolation);
		end = angle;		
	}

	private float start, end;

	protected void begin () {
		start = actor.getRotation();
	}

	protected void update (float percent) {
		//System.out.println(percent);
		actor.setRotation(start + (end - start) * percent);
	}

	public float getRotation () {
		return end;
	}

	public void setRotation (float rotation) {
		this.end = rotation;
	}

}
