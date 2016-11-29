package net.devtrainer.foogl.action;

import com.badlogic.gdx.math.Interpolation;

abstract public class ToggleAction extends IntervalAction {
	private boolean state=true;

	public ToggleAction() {
		super();
	}

	public ToggleAction(float duration) {
		super(duration);
	}

	public ToggleAction(float duration, float delay) {
		super(duration, delay);
	}

	public ToggleAction(float duration, Interpolation interpolation) {
		super(duration, interpolation);
	}

	@Override
	public void interval() {
		if(state) toggleOn(); 
		else toggleOff();
		state = !state;
	}
	abstract void toggleOn();
	abstract void toggleOff();

	public boolean isOn() {
		return state;
	}
}
