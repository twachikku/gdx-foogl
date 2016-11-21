package com.wt.gdxgame.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class FontScale extends TemporalAction {
    float scaleTo;
    float cx=-1,cy=-1,w,h;
	public FontScale(float scaleTo,float duration) {
		super(duration);
		this.scaleTo = scaleTo;
	}

	@Override
	protected void update(float percent) {
		if(cx<0 && cy<0){
			Actor a=getActor();
			cx=a.getX()+a.getOriginX();
			cy=a.getY()+a.getOriginY();
			w=a.getWidth();
			h=a.getHeight();
		}
		
		if(getActor() instanceof Label){
		  Label lb=(Label)getActor();
		  lb.setFontScale(scaleTo*percent);
		  lb.setX(cx-(w*percent/2));
		  lb.setY(cy-(h*percent/2));
		}
	}

}
