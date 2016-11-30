package net.devtrainer.foogl.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import net.devtrainer.foogl.Box2DScene;
import net.devtrainer.foogl.action.ApplyForceAction;

public class Box2DActor extends Actor {
	Body body;
	World world;
	Actor actor;
	
	public Box2DActor(World world,Actor actor,boolean dynamic) {
		this(world,actor.getX(),actor.getY(),actor.getWidth(),actor.getHeight(),dynamic);
		this.actor=actor;
	}

	public Box2DActor(World world,float x,float y,float w,float h,boolean dynamic) {
		super();
        PolygonShape box=new PolygonShape();
        box.setAsBox(w/2,h/2);
		if (!dynamic) {
			BodyDef bdef=new BodyDef();
			bdef.position.set(x, y);
			bdef.type = BodyType.StaticBody;
			body = world.createBody(bdef);
            body.createFixture(box,0.0f);
		} else {
			BodyDef bdef=new BodyDef();
			bdef.position.set(x, y);
			bdef.type = BodyType.DynamicBody;
			body = world.createBody(bdef);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = box;
			fixtureDef.density = 0.3f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.5f; // Make it bounce a little bit
            body.createFixture(fixtureDef);
		}
		box.dispose();
		initBody();
	}
	public Box2DActor(World world, BodyDef bodydef, Shape shape) {
		super();
		body = world.createBody(bodydef);
		if (body.getType()==BodyType.DynamicBody) {	
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.4f;
			fixtureDef.restitution = 0.6f; // Make it bounce a little bit
            body.setType(BodyType.DynamicBody);
            body.createFixture(fixtureDef);
		}else{
			body.createFixture(shape, 0);
		}
		initBody();
	}

	@Override
	protected void setStage(Stage stage) {
		if (!(stage instanceof Box2DScene)) {
			throw new RuntimeException(
					"Box2DActor must be added only on Box2DScene.");
		}
		super.setStage(stage);
	}
	public Body getBody() {
		return body;
	}
	public World getWorld() {
		return world;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(body!=null){
		  setRotation(MathUtils.radiansToDegrees*body.getAngle());
		  Vector2 pos=body.getPosition();
		  setPosition(pos.x-getOriginX(), pos.y-getOriginY());
		}
	}	
	private void initBody() {
		float r=0;
		for(Fixture f: body.getFixtureList()){
			float fr = f.getShape().getRadius();
			if(fr>r)r=fr;
		}
		setOrigin(r/2,r/2);
		setSize(r,r);
	}	

	private TextureRegion image=null;
	
	public TextureRegion getImage() {
		return image;
	}

	public void setImage(TextureRegion image) {
		if(this.image==image) return;
		if(image!=null && (getWidth()<5 || getHeight()<5)){
			setWidth(image.getRegionWidth());
			setHeight(image.getRegionHeight());
			setOrigin(image.getRegionWidth()/2,image.getRegionHeight()/2);
		}
		this.image = image;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {		
		Color c=batch.getColor();
		if(c!=getColor())
		   batch.setColor(getColor());
		if(image!=null){
		   drawImage(batch,image);
		}
		if(c!=getColor())
		   batch.setColor(c);
	}

	public void drawImage(Batch batch, TextureRegion region) {
		float x = getX();
		float y = getY();
		float scaleX = getScaleX();
		float scaleY = getScaleY();
		float rotation = getRotation();
		if (scaleX == 1 && scaleY == 1 && rotation == 0)
			batch.draw(region, x, y, getWidth(), getHeight());
		else {
			batch.draw(region, x, y, getOriginX(), getOriginY(),getWidth(),getHeight(),scaleX, scaleY, rotation);
		}	
	}
	

	
}
