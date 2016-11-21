package com.wt.gdxgame.action;

import com.badlogic.gdx.graphics.Color;

public class Actions extends com.badlogic.gdx.scenes.scene2d.actions.Actions {

	static public SoundAction sound(String name){
		return new SoundAction(name);
	}
	static public EffectAction effect(String name){
		return new EffectAction(name);
	}
	static public EffectAction effect(String name,boolean wait){
		return new EffectAction(name,wait);
	}
	static public BlinkAction blink(Color color){
		return new BlinkAction(color);
	}
}
