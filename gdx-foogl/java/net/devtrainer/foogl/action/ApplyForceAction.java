package net.devtrainer.foogl.action;

import com.badlogic.gdx.physics.box2d.Body;

public class ApplyForceAction extends TemporalAction {
    float px,py,fx,fy;
    Body body;
	public ApplyForceAction(Body body, float duration,float fx,float fy) {
		this(body,duration,-1,-1,fx,fy);
	}
	public ApplyForceAction(Body body, float duration,float px,float py, float fx,float fy) {
		super(duration);
		this.fx=fx;
		this.fy=fy;
		this.px=px;
		this.py=py;
		this.body = body;
	}

	@Override
	protected void update(float percent) {
		if(px<0 || py<0){
         body.applyForceToCenter(fx,fy, true);
		}else{
			body.applyForce(fx,fy,px,py, true);			
		}
	}
}
