package net.devtrainer.foogl.plugin.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Extends original box2d Body, make more user friendly.
 * 
 * @author twachi
 *
 */
public class Body {
	com.badlogic.gdx.physics.box2d.Body body;
	Vector2 maxVelocity = new Vector2(2f, 2f);
	final Touch touch = new Touch(); 

	public Body(com.badlogic.gdx.physics.box2d.Body body) {
		super();
		this.body = body;
	}

	void setVelocityX(float vx) {
		Vector2 v = body.getLinearVelocity();
		body.setLinearVelocity(vx, v.y);
	}

	void setVelocityY(float vy) {
		Vector2 v = body.getLinearVelocity();
		body.setLinearVelocity(v.x, vy);
	}

	void setVelocity(float vx, float vy) {
		body.setLinearVelocity(vx, vy);
	}

	void addVelocity(float vx, float vy) {
		body.setLinearVelocity(vx, vy);
	}

	public Vector2 getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(Vector2 maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public com.badlogic.gdx.physics.box2d.Body getBody() {
		return body;
	}

	public class Touch {
		boolean up = false, left = false, right = false, bottom = false;
	}
}
