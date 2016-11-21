package com.wt.gdxgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ActorBuilder {
	GameScene scene;
	AssetManager asset;
	
	public ActorBuilder (GameScene scene) {
		this.scene = scene;
		this.asset = GameApp.defaultGameApp.asset;
	}
	public GameActor sprite(float x,float y,String key){
		GameActor a=new GameActor(x, y);
		Texture t = asset.get(key, Texture.class);
		a.setImage(new TextureRegion(t));
		scene.addActor(a);
		return a;
	}

}
