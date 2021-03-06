
package net.devtrainer.foogl.action;

import com.badlogic.gdx.math.Interpolation;

public class MoveToAction extends TemporalAction {
	private float startX, startY;
	private float endX, endY;

	public MoveToAction(float endX, float endY, float duration, Interpolation interpolation) {
		super(duration, interpolation);
		this.endX = endX;
        this.endY = endY;
	}

	protected void begin () {
		startX = actor.getX();
		startY = actor.getY();
	}

	protected void update (float percent) {
		actor.setPosition(startX + (endX - startX) * percent, startY + (endY - startY) * percent);
	}

	public void setPosition (float x, float y) {
		endX = x;
		endY = y;
	}

	public float getX () {
		return endX;
	}

	public void setX (float x) {
		endX = x;
	}

	public float getY () {
		return endY;
	}

	public void setY (float y) {
		endY = y;
	}
}
