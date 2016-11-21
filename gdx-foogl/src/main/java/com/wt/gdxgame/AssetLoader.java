
package com.wt.gdxgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {
	GameScene scene;
	AssetManager asset;

	public AssetLoader (GameScene scene) {
		this.scene = scene;
		this.asset = GameApp.getAsset();
	}
	public void image(String file){
		asset.load(file, Texture.class);
	}

}
