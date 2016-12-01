package net.devtrainer.foogl.plugin;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import net.devtrainer.foogl.Scene;

public class Box2DLightPlugin extends ScenePlugin {
	RayHandler rayhandle;
	
	public Box2DLightPlugin () {
		// TODO Auto-generated constructor stub
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
	}
   
	@Override
	public void init (Scene scene) {
		super.init(scene);
		rayhandle = new RayHandler(scene.plugins.box2d().getWorld());
		rayhandle.setAmbientLight(0.1f, 0.1f, 0.1f, 0.5f);
		rayhandle.setBlurNum(3);
	}

	public RayHandler getRayhandle () {
		return rayhandle;
	}

	@Override
	public void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPreload () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoaded () {
		// TODO Auto-generated method stub

	}
	
	float TIME_STEP = 1 / 30f;
	float wdelta=0;
	
	@Override
	public void onUpdate (float delta) {
		if(rayhandle==null) return;
		wdelta += delta;
		if(wdelta < 1/30f) return;
	   rayhandle.update();	
		wdelta = 0;
	}

	@Override
	public void onPreDraw (float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPostDraw (float delta) {
		if(rayhandle==null) return;
		OrthographicCamera cam = (OrthographicCamera)getScene().getCamera();
		rayhandle.setCombinedMatrix(cam);
	   rayhandle.render();
	}
   
	@Override
	public void onDestroy () {
		rayhandle.dispose();
	}
   
	static final Color DefaultColor = new Color(1f, 1f, 1f, 1f);
	
	public PointLight addLight(){
		return new PointLight(rayhandle,32,DefaultColor,200,0,0);
	}
	public PointLight addLight(float x,float y){
		PointLight p = addLight();
		p.setPosition(x, y);
		return p;
	}
	public PointLight addLight(float x,float y, int num){
		PointLight p = new PointLight(rayhandle,num);	
		p.setPosition(x, y);
		return p;
	}
}
