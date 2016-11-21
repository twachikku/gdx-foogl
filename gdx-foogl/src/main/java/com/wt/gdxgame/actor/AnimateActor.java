package com.wt.gdxgame.actor;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wt.gdxgame.AnimateFrames;
import com.wt.gdxgame.GameActor;

public class AnimateActor extends GameActor {
	AnimateFrames animateFrames = null;
	float delay = 0.3f;
	float delay_tcount = 0f;
	int findex = 0;
	ArrayList<TextureRegion> currentFrames = null;
	boolean animate_loop = true;
	String animate = "";

	public AnimateActor(AnimateFrames animateFrames) {
		this(null,0,0);
	}
	public AnimateActor(float x, float y) {
		this(null,x,y);
	}
	public AnimateActor(AnimateFrames animateFrames, float x, float y) {
		super(x, y);
		setAnimateFrames(animateFrames);
	}

	public AnimateFrames getAnimateFrames() {
		return animateFrames;
	}
	public void setAnimateFrames(AnimateFrames animateFrames) {
		this.animateFrames = animateFrames;
		if(animateFrames!=null){
		   this.currentFrames = animateFrames.getFirstFrames();
		   setImage(animateFrames.getFirstFrame());
		   findex=0;
		}
	}	
	public String getAnimate() {
		return animate;
	}

	public void setAnimate(String name) {
		this.animate = name;
		if(animateFrames==null) return;
		
		ArrayList<TextureRegion> frames = animateFrames.get(name);
		if (frames != null) {
			currentFrames = frames;
			findex = 0;
			delay_tcount = 0;
		}
		
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (getStage() == null)
			return;
		if (getX() < 0)
			setSpeed_x(50);
		if (getX() > getStage().getWidth() - getWidth())
			setSpeed_x(-50);
		if (getY() < 0)
			setSpeed_y(50);
		if (getY() > getStage().getHeight() - getHeight())
			setSpeed_y(-50);

		if (currentFrames == null)
			return;
		if (currentFrames.size() > 1 && delay > 0.0f) {
			delay_tcount += delta;
			if (delay_tcount > delay) {
				delay_tcount = 0;
				findex++;
				if (findex >= currentFrames.size()) {
					if (animate_loop) {
						findex = 0;
					} else {
						findex = currentFrames.size() - 1;
					}
				}
			}
		}
		setImage(currentFrames.get(findex));
	}

	public int getFrameIndex() {
		return findex;
	}

	public void setFrameIndex(int findex) {
		if (findex >= currentFrames.size()) {
			if (animate_loop) {
				findex = 0;
			} else {
				findex = currentFrames.size() - 1;
			}
		}
		this.findex = findex;
	}

	public boolean isAnimate_loop() {
		return animate_loop;
	}

	public void setAnimate_loop(boolean animate_loop) {
		this.animate_loop = animate_loop;
	}

	public float getDelay() {
		return delay;
	}

	public void setDelay(float delay) {
		this.delay = delay;
		if (this.delay < 0.01f)
			this.delay = 0f;
	}
	public void nextFrame() {
		setFrameIndex(findex+1);
	}
}
