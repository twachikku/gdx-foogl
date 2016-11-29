package net.devtrainer.foogl.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;

import net.devtrainer.foogl.Scene;

public class ParticleActor extends Actor {
   private ParticleEffectPool pool;
   private ParticleEffect effect;
   Array<Effect> effects = new Array();
   
     
	public ParticleActor (Scene scene,ParticleEffect effect,int max) {
		super(scene);
		this.effect = effect;
		this.pool = new ParticleEffectPool(effect,1, max);		
	}
   
	public ParticleEffectPool getPool () {
		return pool;
	}

	public ParticleEffect getEffect () {
		return effect;
	}

	public Array<Effect> getEffects () {
		return effects;
	}
	public void play(){
		if(effects.size==0){
		   play(getX(),getY(),-1f, null);
		}
	}
	public boolean isPlaying(){
		return effects.size>0;
	}
	public void stop(){
		for (int i = effects.size - 1; i >= 0; i--) {
		   Effect effect = effects.get(i);
		   effect.free();
	      effects.removeIndex(i);
		}
		effects.clear(); 
	}

	public Effect play(float x,float y, float duration, CallBack onfinish){
		Effect effect = new Effect();
		effect.fx  = pool.obtain();
		effect.fx.setPosition(x, y);
		effect.duration = duration;
		effect.onfinish = onfinish;
		effects.add(effect);
		return effect;
	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
		for (int i = effects.size - 1; i >= 0; i--) {
		    Effect effect = effects.get(i);
		    effect.draw(batch);
		}
	}

	@Override
	public void update (float delta) {
		for (int i = effects.size - 1; i >= 0; i--) {
		    Effect effect = effects.get(i);
		    effect.update(delta);
		    if (effect.isComplete()) {
		        effect.free();
		        effects.removeIndex(i);
		    }
		}
	}
	
	class Effect{
		PooledEffect fx;
		CallBack onfinish;
		float duration=1f;
		float time=0;
		void draw(Batch batch){
			fx.draw(batch);
		}
		public void free () {
			if(onfinish!=null){									
				onfinish.handle(this,"finish");
			}
			fx.free();
		}
		void update(float delta){
			fx.update(delta);
			time+=delta;
			if(duration<0 && fx.isComplete()){
				fx.reset();				
			}
		}
		boolean isComplete(){
			if(duration>0 && time>=duration){
				return true;
			}
			return fx.isComplete();
		}
	}
}
