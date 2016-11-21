package com.wt.gdxgame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScene extends GameScene {
	private World world;
	Box2DDebugRenderer debugRenderer;
	private boolean debug=true;

	public World getWorld() {
		return world;
	}

	public Box2DScene() {
	}

	public Box2DScene(GameScene previousScene) {
		super(previousScene);
	}


	public Box2DScene(GameApp ownerApp, GameScene previousScene) {
		super(ownerApp, previousScene);
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public void onInit() {
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		onInitWorld(world);
/*		
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesnt
		// move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(100, 300);

		// Create our body in the world using our body definition
		Body body = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();
*/		
	}
	public void onInitWorld(World world) {
		// Create our body definition
		BodyDef groundBodyDef =new BodyDef();  
		// Set its world position
		groundBodyDef.position.set(new Vector2(0, 1));  

		// Create a body from the defintion and add it to the world
		Body groundBody = world.createBody(groundBodyDef);  

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(getCamera().viewportWidth, 1.0f);
		// Create a fixture from our polygon shape and add it to our ground body  
		groundBody.createFixture(groundBox, 0.0f); 

		Body leftBody = world.createBody(groundBodyDef);  
		groundBox.setAsBox(1,getCamera().viewportHeight);
		leftBody.createFixture(groundBox, 0.0f); 

		groundBodyDef.position.set(getCamera().viewportWidth,0);
		Body rightBody = world.createBody(groundBodyDef);  
		groundBox.setAsBox(1,getCamera().viewportHeight);
		rightBody.createFixture(groundBox, 0.0f); 
		
		groundBox.dispose(); 			
	}

	@Override
	public void onEnter() {

	}

	final float worldstep=1/60f;
	float wdelta=0;
	@Override
	public void onUpdate(float delta) {
		wdelta+=delta;
		if(wdelta>worldstep){
		  world.step(1/10f, 4, 4);
		  wdelta=0;
		}
	}

	@Override
	public void onDestroy() {
		world.dispose();
	}

	public void addActor2D(Box2DActor actor) {
		addActor(actor);
	}

	@Override
	public void afterDraw(float delta) {
		// TODO Auto-generated method stub
		super.afterDraw(delta);
		debugRenderer.render(world, getCamera().combined);
	}
}
