package com.wt.gdxgame.action;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wt.gdxgame.GameScene;

public class SoundAction extends Action {
    String name;
    Sound  sound;	
    float duration;
    float time=0;
	public SoundAction(String soundname) {
		this(soundname,0);
	}
    
	public SoundAction(String soundname,float duration) {
		this.name = soundname;
		this.duration = duration;
	}
	
	@Override
	public boolean act(float delta) {
		if(sound==null){
			Actor a=getActor();
			if(a==null) return true;
			if(a.getStage() instanceof GameScene){
		 	   GameScene scene = (GameScene) a.getStage();
		 	   sound = scene.playSound(name);
			}
		} 
		time+=delta;
		return time>duration;
	}
}
