
package net.devtrainer.foogl.plugin;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.devtrainer.foogl.actor.Actor;
import net.devtrainer.foogl.event.CallBack;
import net.devtrainer.foogl.event.IActorEvent;

public class Box2DPlugin extends ScenePlugin {
	private World world;
	Box2DDebugRenderer debugRenderer;
	private boolean debug = false;
	private ObjectMap<Actor, Body> bodies = new ObjectMap<Actor, Body>();

	float TIME_STEP = 1 / 60f;
	int VELOCITY_ITERATIONS = 6;
	int POSITION_ITERATIONS = 2;
	float pixelPerUnit = 1f;
	float gravity = -100f;
	boolean removeOutOfWorld=true;

	public Box2DPlugin () {

	}

	public float getPixelPerUnit () {
		return pixelPerUnit;
	}

	public void setPixelPerUnit (float pixelPerUnit) {
		this.pixelPerUnit = pixelPerUnit;
	}

	float wdelta = 0;

	public Body getBody (Actor a) {
		if (bodies.containsKey(a)) {
			return bodies.get(a);
		}
		return null;
	}
	public Body enableBody (Actor a, Shape shape, BodyDef.BodyType btype) {
		if (world == null) return null;
		boolean cflag = false;
		if (bodies.containsKey(a)) {
			return bodies.get(a);
		}
		BodyDef bdef = new BodyDef();
		bdef.position.set(a.getCenter().x / pixelPerUnit, a.getCenter().y/ pixelPerUnit);
		bdef.type = btype;
		Body body = world.createBody(bdef);
		if (shape == null) {
			shape = new PolygonShape();
			((PolygonShape)shape).setAsBox(a.getBound().width / (2 * pixelPerUnit), a.getBound().height / (2 * pixelPerUnit));
			cflag = true;
		}else if(shape instanceof CircleShape){
			((CircleShape)shape).setRadius(a.getBound().width / (2 * pixelPerUnit));
		}
		

		if (bdef.type != BodyType.DynamicBody) {
			body.createFixture(shape, 0.0f);
		} else {
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 0.3f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.5f; // Make it bounce a little bit
			body.createFixture(fixtureDef);
		}
		if (cflag) {
			shape.dispose();
		}
		a.setAnchor(0f,0f);
		bodies.put(a, body);
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
		world = new World(new Vector2(0, gravity), true);
		debugRenderer = new Box2DDebugRenderer();
		//System.out.println(world);
	}

	@Override
	public void onDestroy () {
		if (world != null) world.dispose();
	}

	@Override
	public void onPostDraw (float delta) {
		// TODO Auto-generated method stub
		if (debug && world != null) {
			
			debugRenderer.render(world, getScene().getCamera().combined);
		}
	}

	@Override
	public void onUpdate (float delta) {
		if (world == null) return;
		float frameTime = Math.min(delta, 0.25f);
		wdelta += frameTime;
		while (wdelta >= TIME_STEP) {
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			wdelta -= TIME_STEP;
		}
		Viewport v=getScene().getViewport();
		Rectangle worldbound = new Rectangle(-50,-50,v.getWorldWidth()+50, v.getWorldHeight()+50);
		Array<Actor> killed=new Array<>();
		for (Entry<Actor, Body> en : bodies.entries()) {
			float x = en.value.getPosition().x * pixelPerUnit;
			float y = en.value.getPosition().y * pixelPerUnit;
			//if(en.key.getScaleX()!=1){
			//	x=x+en.key.getWidth()/(en.key.getScaleX()+1);
			//}
			//if(en.key.getScaleY()!=1){
			//	y=y+en.key.getHeight()/(en.key.getScaleY()+1);
			//}
			en.key.setOriginPosition(x, y);
			en.key.setRotation(en.value.getAngle()*MathUtils.radiansToDegrees);
			if(!worldbound.overlaps(en.key.getBound())){
				onOutOfWorld.handle(this,en.key);
				if(removeOutOfWorld){
					en.key.kill();
				}
			}
		//	if(en.key.isKilled()){
		//		killed.add(en.key);
		//	}
		}
	//	for(Actor a:killed){
	//		bodies.remove(a);
	//	}
	}

	public void setDebug (boolean debug) {
		this.debug = debug;
	}

	@Override
	public void onActorRemoved (Actor a) {
		if (world == null) return;
		if (bodies.containsKey(a)) {
			 world.destroyBody(bodies.get(a));
		    bodies.remove(a);
		}
	}
	
	final public CallBack onOutOfWorld = new CallBack(IActorEvent.class);

}
