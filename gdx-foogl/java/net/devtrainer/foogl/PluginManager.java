package net.devtrainer.foogl;

import java.util.function.Consumer;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.ObjectMap;

import net.devtrainer.foogl.actor.Actor;
import net.devtrainer.foogl.plugin.Box2DPlugin;
import net.devtrainer.foogl.plugin.IScenePlugin;
import net.devtrainer.foogl.plugin.TiledMapPlugin;

public class PluginManager implements IScenePlugin {
   private Scene scene;
   private ObjectMap<Class, IScenePlugin> plugins=new ObjectMap<Class, IScenePlugin>();
      
	public PluginManager (Scene scene) {
		this.scene = scene;		
	}
   public void register(IScenePlugin plugin){
   	plugin.init(scene);
   	plugins.put(plugin.getClass(), plugin);
   }	
   public <T extends IScenePlugin> T get(Class<T> c){
   	if(plugins.containsKey(c)){
   	  return (T)plugins.get(c);
   	}
   	return null;
   }
   public <T extends IScenePlugin> T uses(Class<T> c){
   	IScenePlugin p = plugins.get(c);
   	if(p==null){
   		try {
				p=c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
   		register(p);
   	}
      
   	return (T)p;   	
   }
   
	public TiledMapPlugin tilemap(){
		return uses(TiledMapPlugin.class);		
	}
	public Box2DPlugin box2d(){
		return uses(Box2DPlugin.class);		
	}

	@Override
	public void onCreate () {
		//System.out.println(plugins);
		for(IScenePlugin p:plugins.values()){ p.onCreate();}
	}

	@Override
	public void onPreload () {
		for(IScenePlugin p:plugins.values()){ p.onPreload();}
	}

	@Override
	public void onLoaded () {
		for(IScenePlugin p:plugins.values()){ p.onLoaded();}
	}

	@Override
	public void onUpdate (float delta) {
		for(IScenePlugin p:plugins.values()){ p.onUpdate(delta);}
	}

	@Override
	public void onPreDraw (float delta) {
		for(IScenePlugin p:plugins.values()){ p.onPreDraw(delta);}
	}

	@Override
	public void onPostDraw (float delta) {
		for(IScenePlugin p:plugins.values()){ p.onPostDraw(delta);}
	}

	@Override
	public void onResume () {
		for(IScenePlugin p:plugins.values()){ p.onResume();}
	}

	@Override
	public void onPause () {
		for(IScenePlugin p:plugins.values()){ p.onPause();}
	}

	@Override
	public void onDestroy () {
		for(IScenePlugin p:plugins.values()){ p.onDestroy();}
	}

	@Override
	public void onLoading (float delta, float progress) {
		for(IScenePlugin p:plugins.values()){ p.onLoading(delta,progress);}
	}
	@Override
	public void init (Scene scene) {
		this.scene = scene;
	}
	@Override
	public void onResize () {
		for(IScenePlugin p:plugins.values()){ p.onResize();}
	}
	@Override
	public void onActorRemoved (Actor a) {
		for(IScenePlugin p:plugins.values()){ p.onActorRemoved(a);}
	}
	@Override
	public void onActorAdded (Actor a) {
		for(IScenePlugin p:plugins.values()){ p.onActorAdded(a);}
	}
  
}
