package net.devtrainer.foogl.action;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.devtrainer.foogl.Scene;

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
			if(a.getStage() instanceof Scene){
		 	   Scene scene = (Scene) a.getStage();
		 	   sound = scene.add.sound(name);
		 	   if(sound!=null) sound.play();
			}
		} 
		time+=delta;
		return time>duration;
	}
}
