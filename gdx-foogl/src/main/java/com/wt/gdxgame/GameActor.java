package com.wt.gdxgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class GameActor extends Actor{

	private Sprite image=null;
	private Body body=null;
	private boolean hflip = false;
	private boolean vflip = false;
	
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
		if(image instanceof Sprite) this.image = (Sprite)image;
		else	this.image = new Sprite(image);
	}

	private float speed_x;
	private float speed_y;
	private float speed_rotate;

	public GameActor(float x,float y) {
       setPosition(x, y);
	}
	
	public void act (float delta){
		super.act(delta);
		if(body!=null){
			Vector2 pos=body.getPosition();
			setPosition(pos.x-getOriginX(), pos.y-getOriginY());			
			setRotation(MathUtils.radiansToDegrees*body.getAngle());
		}else{
		  moveBy(delta*speed_x,delta*speed_y);
		  if(speed_rotate!=0){
			float r=getRotation()+(delta*speed_rotate);
		    setRotation(r);
		  }
		}
		update(delta);
	}
	public void update(float delta) {
		
	}
	public void setSpeed(float speed_x,float speed_y,float speed_rotate) {
		this.speed_x=speed_x;
		this.speed_y=speed_y;
		this.speed_rotate=speed_rotate;
		
	}
	public void setSpeed(float speed_x,float speed_y) {
		this.speed_x=speed_x;
		this.speed_y=speed_y;
	}
	public float getSpeed_x() {
		return speed_x;
	}
	public void setSpeed_x(float speed_x) {
		this.speed_x = speed_x;
	}
	public float getSpeed_y() {
		return speed_y;
	}
	public void setSpeed_y(float speed_y) {
		this.speed_y = speed_y;
	}
	public float getSpeed_rotate() {
		return speed_rotate;
	}
	public void setSpeed_rotate(float speed_rotate) {
		this.speed_rotate = speed_rotate;
	}	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(image!=null){
		
		  image.setPosition(getX(),getY());
  		  image.draw(batch);
		}

/*		Color c=batch.getColor();
		if(c!=getColor())
		   batch.setColor(getColor());
		if(image!=null){
		   drawImage(batch,image);
		}
		if(c!=getColor())
			   batch.setColor(c); */
	}

	public void drawImage(Batch batch, TextureRegion region) {
		float x = getX();
		float y = getY();
		float scaleX = getScaleX();
		float scaleY = getScaleY();
		float rotation = getRotation();
		region.flip(hflip,vflip);
		if (scaleX == 1 && scaleY == 1 && rotation == 0)
			batch.draw(region, x, y, getWidth(), getHeight());
		else {
			batch.draw(region, x, y, getOriginX(), getOriginY(),getWidth(),getHeight(),scaleX, scaleY, rotation);
		}	
		region.flip(false,false);		
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
	public Body createBody(World world,boolean dynamic) {
        PolygonShape box=new PolygonShape();
        float w=getWidth();
        float h=getHeight();
        box.setAsBox(getScaleX()*w/2,getScaleY()*h/2);
        setOrigin(getWidth()/2,getHeight()/2);
        createBody(world,box,dynamic);
		box.dispose();
        return body;
	}
	
	public Body createBody(World world,Shape shape, boolean dynamic) {
		if (!dynamic) {
			BodyDef bdef=new BodyDef();
			bdef.position.set(getX(),getY());
			bdef.type = BodyType.StaticBody;
			body = world.createBody(bdef);
            body.createFixture(shape,0.0f);
		} else {
			BodyDef bdef=new BodyDef();
			bdef.position.set(getX(), getY());
			bdef.type = BodyType.DynamicBody;
			body = world.createBody(bdef);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 0.3f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.5f; // Make it bounce a little bit
            body.createFixture(fixtureDef);
		}
		return body;
	}

	protected void setStage (Stage stage) {		
		if(body!=null && getStage()!=null && stage==null){
		   body.getWorld().destroyBody(body);
		}
		if(getStage()!=null && stage==null){
		   onRemove();
		}
		super.setStage(stage);
	}

	public void onRemove() {
		
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if(body!=null){
			for(Fixture f:body.getFixtureList()){
				if(f.testPoint(x,y)) return this;
			}
			return null;
		}
		return super.hit(x, y, touchable);
	}
	
	public Vector2 parentToLocalCoordinates (Vector2 parentCoords) {
		if(body==null) return super.parentToLocalCoordinates(parentCoords);
		return body.getLocalPoint(parentCoords);
	}
	public boolean isHflip() {
		return hflip;
	}

	public void setHflip(boolean hflip) {
		this.hflip = hflip;
	}

	public boolean isVflip() {
		return vflip;
	}

	public void setVflip(boolean vflip) {
		this.vflip = vflip;
	}


}
