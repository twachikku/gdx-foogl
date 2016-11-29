package net.devtrainer.foogl.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

import net.devtrainer.foogl.actor.Box2DActor;

public class ApplyForceAction extends TemporalAction {
    float px,py,fx,fy;
	public ApplyForceAction(float duration,float fx,float fy) {
		this(duration,-1,-1,fx,fy);
	}
	public ApplyForceAction(float duration,float px,float py, float fx,float fy) {
		super(duration);
		this.fx=fx;
		this.fy=fy;
		this.px=px;
		this.py=py;
	}

	@Override
	protected void update(float percent) {
		if(!(getActor() instanceof Box2DActor)) return;
		Box2DActor b = (Box2DActor) getActor();
		if(px<0 || py<0){
          b.getBody().applyForceToCenter(fx,fy, true);
		}else{
	      b.getBody().applyForce(fx,fy,px,py, true);			
		}
	}
}
