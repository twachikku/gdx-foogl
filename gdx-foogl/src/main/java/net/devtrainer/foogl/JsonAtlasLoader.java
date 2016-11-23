package net.devtrainer.foogl;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class JsonAtlasLoader extends SynchronousAssetLoader<TextureAtlas>{
//	public TextureAtlasLoader (FileHandleResolver resolver) {
		super(resolver);
	}
}
