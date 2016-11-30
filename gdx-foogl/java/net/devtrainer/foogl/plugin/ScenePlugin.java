package net.devtrainer.foogl.plugin;

import net.devtrainer.foogl.Scene;
import net.devtrainer.foogl.actor.Actor;

abstract public class ScenePlugin implements IScenePlugin {
   private Scene scene;
	public ScenePlugin () {
		
	}

	@Override
	public void init (Scene scene) {
		this.scene = scene;
	}

	public Scene getScene () {
		return scene;
	}
	public void onPreload () {	}
	public void onLoaded () {	}
	public void onResume (){}
	public void onPause (){}
	public void onLoading (float delta, float progress) { }
	public void onResize(){ }
	public void onPreDraw (float delta) {  }
	public void onPostDraw (float delta) {  }
	
	/**
	 * Called when actor was removed out of the scene
	 * @param a
	 */
	public void onActorRemoved(Actor a){  }
	/**
	 * Called when actor was added to the scene
	 * @param a
	 */
	public void onActorAdded(Actor a){  }
}
