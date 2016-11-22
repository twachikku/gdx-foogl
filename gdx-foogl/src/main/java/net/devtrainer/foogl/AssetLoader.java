
package net.devtrainer.foogl;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {
	AssetManager asset;
	HashMap<String, String> keys = new HashMap<>();

	public AssetLoader () {
		this.asset = Game.getAsset();
	}

	public void image (String key, String file) {
		load(key, file, Texture.class);
	}

	public void sound (String key, String file) {
		load(key, file, Sound.class);
	}

	private void load (String key, String file, Class type) {
		keys.put(type.getSimpleName() + ":" + key, file);
		asset.load(file, type);
	}

	public String getFile (String key, Class type) {
		return keys.get(type.getSimpleName() + ":" + key);
	}
}
