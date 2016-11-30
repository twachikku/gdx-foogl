package net.devtrainer.foogl.plugin;

import net.devtrainer.foogl.Scene;

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
	public void onResume (){}
	public void onPause (){}
	public void onLoading (float delta, float progress) { }
	public void onResize(){ }
}
