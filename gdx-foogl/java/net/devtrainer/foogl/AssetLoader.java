
package net.devtrainer.foogl;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ObjectMap;

import net.devtrainer.foogl.actor.SpriterData;

public class AssetLoader {
	AssetManager asset;
	HashMap<String, ObjectMap<Class, Object>> keys = new HashMap();

	public AssetLoader () {
		this.asset = Game.getAsset();
		this.asset.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));	
		ShaderProgram s;
		
	}
	public void atlas(String key, String file){
		load(key, file, TextureAtlas.class);		
	}
	public void image (String key, String file) {
		load(key, file, Texture.class);
		getkeys(key).remove(TextureRegion.class);
	}

	public void music (String key, String file) {
		load(key, file, Music.class);
	}
	public void sound (String key, String file) {
		load(key, file, Sound.class);
	}
	public void particle (String key, String file) {
		load(key, file, ParticleEffect.class);
	}
	public void particle3D (String key, String file) {
		load(key, file, com.badlogic.gdx.graphics.g3d.particles.ParticleEffect.class);
	}
	public void model3d (String key, String file) {
		load(key, file, Model.class);
	}
	public void region (String key, String file) {
		load(key, file, PolygonRegion.class);		
	}
	public void tiledmap (String key, String file) {
		load(key, file, TiledMap.class);		
	}
	
	
	public void spriteSheet (String key, String file, int frameWidth, int frameHeight, int margin, int spacing, int frameMax) {
		image(key, file);
      SpriteSheet s=new SpriteSheet(key, frameWidth, frameHeight, margin, spacing, frameMax);
      storekey(key,s,SpriteSheet.class);
	}

	public void spriteSheet (String key, String file, int frameWidth, int frameHeight) {
		this.spriteSheet(key, file, frameWidth, frameHeight, 0, 0, -1);
	}
	public void spriter (String key, String file) {
		SpriterData data = new SpriterData(file);
		storekey(key,data,SpriterData.class);
	}

	public void load (String key, String file, Class type) {
		storekey(key, file, type);
		asset.load(file, type);
	}

	protected void storekey (String key, Object value, Class type) {
		ObjectMap<Class, Object> vals;
		if (!keys.containsKey(key)) {
			vals = new ObjectMap();
			keys.put(key,vals);
		} else {
			vals = keys.get(key);
		}
		vals.remove(type);
		vals.put(type, value);
		//System.out.println("  storekey "+key+"  f:"+value+" type:"+type.getName());
		//System.out.println("  getfile  "+key+"  f:"+getFile(key, type));
		
	}

	protected Object getFile (String key, Class type) {
		if (keys.containsKey(key)) {
			//System.out.println("  getFile "+key+" t:"+type.getName());
			return keys.get(key).get(type);
		}
		//System.out.println("  getFile "+key+" t:"+type.getName()+"  null");
		return null;
	}

	protected ObjectMap<Class, Object> getkeys (String key) {
		return keys.get(key);
	}
}
