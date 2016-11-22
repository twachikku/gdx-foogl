package net.devtrainer.foogl.action;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class BlinkAction extends ToggleAction {
    private Color colorOn=null;
    private Color colorOff=Color.RED;

	public BlinkAction() {
		super();
	}
	public BlinkAction(float duration, float delay) {
		super(duration, delay);
	}
	public BlinkAction(float duration, Interpolation interpolation) {
		super(duration, interpolation);
	}
	public BlinkAction(float duration) {
		super(duration);
	}
	
	public BlinkAction(Color colorOff) {
		super(1.0f,0.1f);
		this.colorOff = colorOff;
	}
	public BlinkAction(float duration, float delay, Color colorOff) {
		super(duration, delay);
		this.colorOff = colorOff;
	}

	@Override
	protected void begin() {
		super.begin();
		colorOn = new Color(getActor().getColor());
	}
	@Override
	protected void end() {
		super.end();
		toggleOn();
	}
	@Override
	void toggleOn() {
		if(colorOn==null) colorOn=Color.WHITE;
		getActor().setColor(colorOn);			
	}
	@Override
	void toggleOff() {
		if(colorOff==null) colorOff=Color.RED;
		getActor().setColor(colorOff);			
	}	
}
