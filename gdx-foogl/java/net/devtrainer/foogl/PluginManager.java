package net.devtrainer.foogl;

import java.util.function.Consumer;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.ObjectMap;

import net.devtrainer.foogl.actor.Actor;
import net.devtrainer.foogl.plugin.Box2DLightPlugin;
import net.devtrainer.foogl.plugin.Box2DPlugin;
import net.devtrainer.foogl.plugin.IScenePlugin;
import net.devtrainer.foogl.plugin.JavaScriptPlugin;
import net.devtrainer.foogl.plugin.TiledMapPlugin;

public class PluginManager implements IScenePlugin {
	private Scene scene;
	private ObjectMap<Class, IScenePlugin> plugins = new ObjectMap<Class, IScenePlugin>();

	public PluginManager(Scene scene) {
		this.scene = scene;
	}

	public void register(IScenePlugin plugin) {
		plugin.init(scene);
		plugins.put(plugin.getClass(), plugin);
	}

	public <T extends IScenePlugin> T get(Class<T> c) {
		if (plugins.containsKey(c)) {
			return (T) plugins.get(c);
		}
		return null;
	}

	public <T extends IScenePlugin> T uses(Class<T> c) {
		IScenePlugin p = plugins.get(c);
		if (p == null) {
			try {
				p = c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
			register(p);
		}

		return (T) p;
	}

	public TiledMapPlugin tilemap() {
		return uses(TiledMapPlugin.class);
	}

	public Box2DPlugin box2d() {
		return uses(Box2DPlugin.class);
	}

	public JavaScriptPlugin javascript(String file) {
		JavaScriptPlugin p=new JavaScriptPlugin(file);
		register(p);
		return p;
	}
	public JavaScriptPlugin javascript() {
		return uses(JavaScriptPlugin.class);
	}

	public Box2DLightPlugin box2dlight() {
		return uses(Box2DLightPlugin.class);
	}

	@Override
	public void onCreate() {
		// System.out.println(plugins);
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onCreate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onPreload() {

		for (IScenePlugin p : plugins.values()) {
			try {
				p.onPreload();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onLoaded() {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onLoaded();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpdate(float delta) {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onUpdate(delta);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onPreDraw(float delta) {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onPreDraw(delta);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onPostDraw(float delta) {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onPostDraw(delta);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onResume() {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onResume();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onPause() {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onPause();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDestroy() {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onDestroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onLoading(float delta, float progress) {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onLoading(delta, progress);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void onResize() {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onResize();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onActorRemoved(Actor a) {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onActorRemoved(a);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onActorAdded(Actor a) {
		for (IScenePlugin p : plugins.values()) {
			try {
				p.onActorAdded(a);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
