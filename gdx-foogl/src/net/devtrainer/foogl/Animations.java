package net.devtrainer.foogl;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class Animations extends ObjectMap<String, Animation>{
	private float  defaultDuration=0.1f;
	private String defaultKey = null;
	private Animation defaultAnimation = null;
	
	public Animations () {
		
	}
	public Animations (float defaultDuration) {
		super();
		this.defaultDuration = defaultDuration;
	}
	
	public float getDefaultDuration () {
		return defaultDuration;
	}
	public void setDefaultDuration (float defaultDuration) {
		this.defaultDuration = defaultDuration;
		if(this.defaultDuration<=0.01f){
			this.defaultDuration = 0.01f;
		}		
	}
	public void add(String key,TextureRegion frames[]){
		Animation a = new Animation(defaultDuration,frames);
		a.setPlayMode(PlayMode.LOOP);
		if(defaultAnimation==null){
			defaultAnimation=a;
			defaultKey=key;
		}
		put(key,a);
	}
	public void add(String key,Array<? extends TextureRegion> frames){
		Animation a = new Animation(defaultDuration,frames);
		a.setPlayMode(PlayMode.LOOP);
		if(defaultAnimation==null){
			defaultAnimation=a;
			defaultKey=key;
		}
		put(key,a);
	}
	public void add(String spriteSheet){
      add(spriteSheet, spriteSheet);		
	}
	public void add(String key, String spriteSheet){
		SpriteSheet sheet=Game.builder.spritesheet(spriteSheet);
		if(sheet!=null){
			add(key,sheet.getFrames());
		}
	}
	public void add(String key, String spriteSheet, int ... frames){
		SpriteSheet sheet=Game.builder.spritesheet(spriteSheet);
		if(sheet!=null){			
			add(key,sheet.getFrames(frames));
		}
	}
	
	public void add(String key,TextureAtlas atlas, String name){		
		add(key,atlas.findRegions(name));
	}
	public void addAll(TextureAtlas atlas){
		final Array<String> list = new Array<String>();
		for (TextureAtlas.AtlasRegion atlasRegion : atlas.getRegions()) {
			if (atlasRegion.index == -1) continue;
			if (list.contains(atlasRegion.name, false)) continue;
			list.add(atlasRegion.name);
		}
		for(String key : list){
			//System.out.println(key);
			add(key,atlas,key);
		}
	}
	public Animation getDefault () {
		return defaultAnimation;
	}
	
	
}
