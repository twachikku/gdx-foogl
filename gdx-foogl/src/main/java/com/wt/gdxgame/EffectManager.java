package com.wt.gdxgame;

import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.wt.gdxgame.res.Resource;

public class EffectManager implements Disposable {

	private HashMap<String, ParticleEffectPool> effectData = new HashMap<String, ParticleEffectPool>();
	private Array<ParticleEffect> effects=new Array<ParticleEffect>();
	private Texture particleTex;
	private Sprite  particleSprite;
	private Array<ParticleEffect> playingEffects=new Array<ParticleEffect>();
	
	public EffectManager(){
		particleTex = new Texture(Resource.getFile("particle.png"));
		particleSprite=new Sprite(particleTex);
	}
	@Override
	public void dispose() {
		for(ParticleEffectPool p:effectData.values()){
			p.clear();
		}
		for(ParticleEffect p:effects){
			p.dispose();
		}
		effects.clear();
		particleTex.dispose();
	}
	public ParticleEffect load(String name){
		return load(name,Resource.getFile(name+".p"),particleSprite);	
	}
	public ParticleEffect load(String name,FileHandle file, Texture image){
        return load(name,file,new Sprite(image));		
	}
	public ParticleEffect load(String name,FileHandle file,Sprite sprite){
		if(effectData.containsKey(name)) return get(name);
		ParticleEffect p=new ParticleEffect();
		p.loadEmitters(file);
		for (int i = 0, n = p.getEmitters().size; i < n; i++) {
			ParticleEmitter emitter = p.getEmitters().get(i);
			emitter.setSprite(sprite);
		}	
		effectData.put(name,new ParticleEffectPool(p,5,10));
		effects.add(p);
		return p;
	}
	public ParticleEffect load(String name,FileHandle file, FileHandle imagePath){
		if(effectData.containsKey(name)) return get(name);
		ParticleEffect p=new ParticleEffect();
		p.load(file, imagePath);
		effectData.put(name,new ParticleEffectPool(p,5,10));		
		effects.add(p);
		return p;
	}
	public PooledEffect get(String name){
		if(!effectData.containsKey(name)){
			load(name);
		}
		if(effectData.containsKey(name)){
			ParticleEffectPool pool = effectData.get(name);
			return pool.obtain();
		}
		return null;
	}
	public void render(SpriteBatch batch,float delta){
		for (int i = playingEffects.size - 1; i >= 0; i--) {
	        ParticleEffect effect = playingEffects.get(i);
	        effect.draw(batch, delta);
	        if (effect.isComplete()) {
	        	if(effect instanceof PooledEffect)
	              ((PooledEffect)effect).free();	        	
	            playingEffects.removeIndex(i);
	        }
	    }					
	}
	public PooledEffect playEffect(String name, float x, float y) {
		return playEffect(name, x, y,0.5f,null);
    } 
	/**
	 * 
	 * @param name
	 * @param x        position x
	 * @param y        position y
	 * @param duration second
	 * @param onfinish 
	 */
	public PooledEffect playEffect(String name, float x, float y, float duration, EventListener onfinish) {
		PooledEffect p=get(name);
		if(p!=null){
			p.setDuration((int)(duration*1000));
			p.setPosition(x, y);
			playingEffects.add(p);
		}		
		return p;
	}	
}
