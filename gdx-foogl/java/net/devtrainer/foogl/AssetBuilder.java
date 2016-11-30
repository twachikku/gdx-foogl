
package net.devtrainer.foogl;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import net.devtrainer.foogl.actor.Group;
import net.devtrainer.foogl.actor.ParticleActor;
import net.devtrainer.foogl.actor.SpriteActor;
import net.devtrainer.foogl.actor.SpriteActor.AssetType;
import net.devtrainer.foogl.actor.SpriterActor;
import net.devtrainer.foogl.actor.SpriterData;

public class AssetBuilder {
	AssetManager asset;
	AssetLoader loader;
	Group defaultGroup;
	Scene scene;

	public AssetBuilder () {
		this.asset = Game.asset;
		this.loader = Game.loader;
	}

	public Group getDefaultGroup () {
		if (defaultGroup == null) {
			if (getScene() != null)
				defaultGroup = getScene().getRootGroup();
			else
				defaultGroup = group();
		}
		return defaultGroup;
	}

	public void setDefaultGroup (Group group) {
		this.defaultGroup = group;
	}

	public SpriteActor sprite (float x, float y, String key) {
		return sprite(x, y, key, -1);
	}

	public SpriteActor animator (float x, float y, String animationName) {
		return animator(x, y, animationName, null);
	}

	public SpriteActor animator (float x, float y, String animationName, String action) {
		SpriteActor a = new SpriteActor(getScene(), animationName, action, AssetType.AsAnimations);
		a.setPosition(x, y);
		getDefaultGroup().add(a);
		return a;
	}

	public SpriteActor animator (float x, float y, Animations animations) {
		SpriteActor a = new SpriteActor(getScene());
		a.setTextureRegion(animations.getDefault().getKeyFrame(0));
		a.setAnimations(animations);
		a.setPosition(x, y);
		getDefaultGroup().add(a);
		a.play();
		return a;
	}

	public SpriteActor sprite (float x, float y, String spriteSheet, int frame) {
		SpriteActor a = new SpriteActor(getScene(), spriteSheet, frame, AssetType.AsSpriteSheet);
		a.setPosition(x, y);
		getDefaultGroup().add(a);
		return a;
	}

	public SpriterActor spriter (float x, float y, String key, String entity) {
		SpriterData data = get(key, SpriterData.class);
		if (data == null) return null;

		SpriterActor a = new SpriterActor(getScene(), data, entity);
		a.setName(key);
		a.setPosition(x, y);
		getDefaultGroup().add(a);
		return a;
	}

	public SpriterActor spriter (float x, float y, String key, int entity) {
		SpriterData data = get(key, SpriterData.class);
		if (data == null) return null;

		SpriterActor a = new SpriterActor(getScene(), data, entity);
		a.setPosition(x, y);
		getDefaultGroup().add(a);
		return a;
	}

	public Group group () {
		Group g = new Group(getScene());
		getDefaultGroup().add(g);
		return g;
	}

	public TiledMap tiledmap (String key) {
		TiledMap map = get(key, TiledMap.class);
		return map;
	}

	public ParticleActor particle (float x, float y, String key) {
		return particle(x, y, key, 2);
	}

	public ParticleActor particle (float x, float y, String key, int max) {
		ParticleEffect fx = get(key, ParticleEffect.class);
		if (fx == null) return null;
		ParticleActor a = new ParticleActor(getScene(), fx, max);
		a.setPosition(x, y);
		getDefaultGroup().add(a);
		return a;
	}

	public Scene getScene () {
		if (scene == null) scene = Game.defaultGameApp.getActiveScenes().first();
		return scene;
	}

	public TextureRegion texture (String key) {
		return texture(key, -1);
	}

	public TextureRegion texture (String key, int frame) {
		// System.out.println("texture "+key+" f:"+frame);
		TextureRegion r = get(key, TextureRegion.class);
		if (r == null) {
			Texture t = get(key, Texture.class);
			if (t != null) {
				r = new TextureRegion(t);
				// r.flip(false,true);
				loader.storekey(key, r, TextureRegion.class);
			}
			if (r == null) return null;
		}

		if (frame < 0) {
			return r;
		}
		SpriteSheet s = spritesheet(key);
		if (s != null) return s.getFrame(frame);
		return null;
	}

	public SpriteSheet spritesheet (String key) {
		return get(key, SpriteSheet.class);
	}

	public Sound sound (String name) {
		return get(name, Sound.class);
	}

	public Music music (String name) {
		return get(name, Music.class);
	}

	public Animations animations (String spriteSheetKey) {
		Animations a = get(spriteSheetKey, Animations.class);
		if (a == null) {
			a = new Animations();
			a.add(spriteSheetKey);
			loader.storekey(spriteSheetKey, a, Animations.class);
		}
		return a;
	}

	public synchronized <T> T get (String key, Class<T> type) {
		Object f = loader.getFile(key, type);
		// System.out.println(" get "+key+" t:"+type.getName()+" f:"+f);
		if (f == null) return null;
		if (f instanceof String) {
			if (asset.isLoaded(f.toString(), type)) {
				return asset.get(f.toString(), type);
			}
			return null;
		} else {
			return (T)f;
		}
	}

	public TextureAtlas atlas (String key) {
		return get(key, TextureAtlas.class);
	}

	public void setScene (Scene scene) {
		this.scene = scene;
		setDefaultGroup(scene.getRootGroup());
	}

}
