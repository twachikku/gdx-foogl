package net.devtrainer.foogl.plugin;

import com.badlogic.gdx.maps.MapObject;

import net.devtrainer.foogl.Scene;
import net.devtrainer.foogl.actor.Actor;

public interface IScenePlugin {
	void init(Scene scene);
	void onCreate ();
	void onPreload ();
	void onLoaded ();
	void onUpdate (float delta);
	void onPreDraw (float delta);
	void onPostDraw (float delta);
	void onResume ();
	void onPause ();
	void onDestroy ();
	void onLoading (float delta, float progress);
	void onResize ();
	
	/**
	 * Called when actor was removed out of the scene
	 * @param a
	 */
	void onActorRemoved(Actor a);
	/**
	 * Called when actor was added to the scene
	 * @param a
	 */
	void onActorAdded(Actor a);
	
}
