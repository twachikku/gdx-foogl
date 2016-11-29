package net.devtrainer.foogl.action;

import com.badlogic.gdx.graphics.Color;

public class AlphaAction extends ColorAction {

	public AlphaAction () {

	}   
	void setAlpha(float a){
		Color cb=actor.getColor();
		Color ce=getEndColor();
		ce.r=cb.r;
		ce.g=cb.g;
		ce.b=cb.b;
		ce.a=a;		
	}
}
