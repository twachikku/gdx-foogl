package net.devtrainer.foogl.action;

import com.badlogic.gdx.math.Interpolation;

public class ScaleAction extends TemporalAction {

	public ScaleAction () {
		// TODO Auto-generated constructor stub
	}

	public ScaleAction (float duration) {
		super(duration);
		// TODO Auto-generated constructor stub
	}

	public ScaleAction (float duration, Interpolation interpolation) {
		super(duration, interpolation);
		// TODO Auto-generated constructor stub
	}

	private float startX, startY;
	private float endX, endY;

	protected void begin () {
		startX = actor.getScaleX();
		startY = actor.getScaleY();
	}

	protected void update (float percent) {
		actor.setScale(startX + (endX - startX) * percent, startY + (endY - startY) * percent);
	}

	public void setScale (float x, float y) {
		endX = x;
		endY = y;
	}

	public void setScale (float scale) {
		endX = scale;
		endY = scale;
	}

	public float getX () {
		return endX;
	}

	public void setX (float x) {
		this.endX = x;
	}

	public float getY () {
		return endY;
	}

	public void setY (float y) {
		this.endY = y;
	}
}
