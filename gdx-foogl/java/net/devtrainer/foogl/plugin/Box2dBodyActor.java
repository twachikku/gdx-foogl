package net.devtrainer.foogl.plugin;

import com.badlogic.gdx.physics.box2d.Body;

import net.devtrainer.foogl.action.ApplyForceAction;
import net.devtrainer.foogl.actor.Actor;

public class Box2dBodyActor {
   Body body;
   Actor actor;
	
	
	public Box2dBodyActor (Body body, Actor actor) {
		super();
		this.body = body;
		this.actor = actor;
		this.body.getPosition().x = this.actor.getX();
		this.body.getPosition().y = this.actor.getY();
	}

	public void update(){
		this.actor.setPosition(this.body.getPosition());
		float rotation = (float)(this.body.getAngle()*180f/Math.PI);
		this.actor.setRotation(rotation);
	}
	public void applyForce(float duration,float fx,float fy){
		ApplyForceAction action=new ApplyForceAction(body,duration,fx,fy);
		this.actor.actions.add(action);
	}
	public void applyAcceleration(float duration,float ax,float ay){
		float fx=body.getMass()*ax;
		float fy=body.getMass()*ay;
		applyForce(duration,fx, fy);
	}
	public void applyForce(float duration,float py,float px,float fx,float fy){
		ApplyForceAction action=new ApplyForceAction(body,duration,px,py,fx,fy);
		this.actor.actions.add(action);
   }
	public void applyAcceleration(float duration,float px,float py,float ax,float ay){
		float fx=body.getMass()*ax;
		float fy=body.getMass()*ay;
		applyForce(duration,px,py,fx, fy);
	}

}
