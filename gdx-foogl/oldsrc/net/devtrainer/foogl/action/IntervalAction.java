package net.devtrainer.foogl.action;

import com.badlogic.gdx.math.Interpolation;

abstract public class IntervalAction extends TemporalAction {
	protected float delay=0.1f;

	public IntervalAction() {
		this(1.0f,0.1f);
	}
	public IntervalAction(float duration) {
		this(duration,0.1f);
	}

	public IntervalAction(float duration,float delay) {
		super(duration);
		this.delay = delay;
	}

	public IntervalAction(float duration, Interpolation interpolation) {
		super(duration, interpolation);
		this.delay = 0.1f;
	}
	@Override
	protected void update(float percent) { }
	
	private float dtime=0;
		
	@Override
	public boolean act(float deltaTime) {
		boolean state=super.act(deltaTime);
		dtime+=deltaTime;
		if(dtime>delay){
			interval();
			dtime=0;
		}
		return state;
	}
	
	public float getDelay() {
		return delay;
	}
	public void setDelay(float delay) {
		this.delay = delay;
	}
	abstract public void interval();
}
