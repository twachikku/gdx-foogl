
package net.devtrainer.foogl.plugin;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.ObjectMap;

import net.devtrainer.foogl.actor.Actor;

public class Box2DPlugin extends ScenePlugin {
	private World world;
	Box2DDebugRenderer debugRenderer;
	private boolean debug = false;
	private ObjectMap<Actor, Body> bodies=new ObjectMap<Actor, Body>();

	float TIME_STEP = 1 / 60f;
	int VELOCITY_ITERATIONS=6;
	int POSITION_ITERATIONS=2;
	float pixelPerUnit = 100f;
   public Box2DPlugin () {
		
	}
	public float getPixelPerUnit () {
		return pixelPerUnit;
	}

	public void setPixelPerUnit (float pixelPerUnit) {
		this.pixelPerUnit = pixelPerUnit;
	}

	float wdelta = 0;

	
	
	public Body enableBody(Actor a, PolygonShape shape,BodyDef.BodyType btype){
		if(bodies.containsKey(a)){
			return bodies.get(a);
		}
		BodyDef bdef=new BodyDef();
		bdef.position.set(a.getX()/pixelPerUnit,a.getY()/pixelPerUnit);
		bdef.type = btype;
		Body body = world.createBody(bdef);
		if(shape==null){
		  shape=new PolygonShape();
		  shape.setAsBox(a.getBound().width/(2*pixelPerUnit), a.getBound().height/(2*pixelPerUnit));
		}
		if(bdef.type == BodyType.StaticBody){
		  	
		}else{
			
		}	
		
		bodies.put(a,body);
		return body;
	}

	public World getWorld () {
		return world;
	}

	public boolean isDebug () {
		return debug;
	}

	@Override
	public void onCreate () {
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void onDestroy () {
		world.dispose();
	}

	@Override
	public void onLoaded () {
		// TODO Auto-generated method stub

	}
	@Override
	public void onPostDraw (float delta) {
		// TODO Auto-generated method stub
		if(debug){
		   debugRenderer.render(world, getScene().getCamera().combined);
		}
	}

	@Override
	public void onPreDraw (float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPreload () {
		// TODO Auto-generated method stub

	}
  
	@Override
	public void onUpdate (float delta) {
		 float frameTime = Math.min(delta, 0.25f);
	    wdelta += frameTime;
	    while (wdelta >= TIME_STEP) {
	        world.step(TIME_STEP, VELOCITY_ITERATIONS,POSITION_ITERATIONS);
	        wdelta -= TIME_STEP;
	    }		
	}

	public void setDebug (boolean debug) {
		this.debug = debug;
	}

}
