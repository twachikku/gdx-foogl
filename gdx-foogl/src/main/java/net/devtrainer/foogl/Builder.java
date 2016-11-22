
package net.devtrainer.foogl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import net.devtrainer.foogl.actor.GameActor;

public class Builder {
	Scene scene;
	AssetManager asset;
	AssetLoader loader;

	public Builder (Scene scene) {
		this.scene = scene;
		this.asset = Game.asset;
		this.loader = Game.loader;
	}

	public GameActor sprite (float x, float y, String key) {
		GameActor a = new GameActor(x, y);
		Texture t = get(key, Texture.class);
		a.setImage(new TextureRegion(t));
		scene.addActor(a);
		return a;
	}

	public Label label (float x, float y, String txt) {
		Label lb = new Label(txt, scene.getSkin());
		lb.setPosition(x, y);
		scene.addActor(lb);
		return lb;
	}

	public Texture texture (String key) {
		return get(key, Texture.class);
	}

	public Sound sound (String name) {
		return get(name, Sound.class);
	}

	private synchronized <T> T get (String key, Class<T> type) {
		String f = loader.getFile(key, type);
		if (f == null) f = key;
		return asset.get(f, type);
	}
}
