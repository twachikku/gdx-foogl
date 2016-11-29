package net.devtrainer.foogl.action;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.devtrainer.foogl.Scene;

public class EffectAction extends Action {
    PooledEffect effect;
    String name;
    float x=0,y=0;
    boolean wait=true;
	public EffectAction(String effectName) {
		this(effectName,false);
	}
	public EffectAction(String effectName,boolean wait) {
		this.name = effectName;
		this.wait = wait;
	}

	@Override
	public boolean act(float delta) {
		if(effect==null){
			Actor a=getActor();
			if(a==null) return true;
			if(a.getStage() instanceof Scene){
		 	   Scene scene = (Scene) a.getStage();
		 	   if(x==0&&y==0){
		 		   x=a.getX()+a.getOriginX();
		 		   y=a.getY()+a.getOriginY();
		 	   }
		 	  // effect = scene.playEffect(name,x,y);
			}
		}
		if(effect==null) return false;
		if(!wait) return true;
		return effect.isComplete();
	}

}
