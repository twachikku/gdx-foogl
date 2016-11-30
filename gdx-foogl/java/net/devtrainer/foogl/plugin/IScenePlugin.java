package net.devtrainer.foogl.plugin;

import com.badlogic.gdx.maps.MapObject;

import net.devtrainer.foogl.Scene;

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
}
