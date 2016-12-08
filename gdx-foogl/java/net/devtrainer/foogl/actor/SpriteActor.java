
package net.devtrainer.foogl.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.StringBuilder;

import net.devtrainer.foogl.Animations;
import net.devtrainer.foogl.Scene;
//import net.devtrainer.foogl.TSprite;

public class SpriteActor extends Group {
	public enum AssetType {
		AsCode, AsTexture, AsSpriteSheet, AsTextureAtlas, AsAnimations
	}
   private AssetType  assetType = AssetType.AsCode;
   private String     assetKey;
   private String     assetFrame=null;
   private int        assetFrameNo=-1;
	transient private Animations animations = null;
	transient private Sprite sprite = new Sprite();
	transient private Animation currentAnimation = null;
	transient private float timer = 0;
	transient private boolean animationFinished = true;
	
	public SpriteActor (Scene scene) {
		super(scene);
		assetType = AssetType.AsCode;
	}
	public SpriteActor (Scene scene, Texture texture) {
		super(scene);
		sprite = new Sprite(texture);
		setSourceSize(sprite.getRegionWidth(), sprite.getRegionHeight());
		assetType = AssetType.AsCode;
	}

	public SpriteActor (Scene scene, TextureRegion region) {
		super(scene);
		sprite = new Sprite(region);
		setSourceSize(sprite.getRegionWidth(), sprite.getRegionHeight());
		assetType = AssetType.AsCode;
	}

	public SpriteActor (Scene scene, String key, String frame, AssetType assetType) {
		this(scene);
		this.assetType  = assetType;
		this.assetKey   = key;
		this.assetFrame = frame;
		this.assetFrameNo=-1;
		if(assetType==AssetType.AsTexture){
			setTextureRegion(scene.builder.texture(key));
		}else if(assetType==AssetType.AsSpriteSheet){
			this.assetFrameNo=0;
			setTextureRegion(scene.builder.texture(key,0));
		}else if(assetType==AssetType.AsTextureAtlas){
			TextureAtlas atlas = scene.builder.atlas(key);
			setTextureRegion(atlas.findRegion(frame));
		}else if(assetType==AssetType.AsAnimations){
			setAnimations(key);
			play(frame);
		} 
	}
	public SpriteActor (Scene scene, String key, int frame, AssetType assetType) {
		this(scene);
		this.assetType  = assetType;
		this.assetKey   = key;
		this.assetFrame = "";
		this.assetFrameNo = frame;
		if(assetType==AssetType.AsTexture){
			setTextureRegion(scene.builder.texture(key));
		}else if(assetType==AssetType.AsSpriteSheet){
			setTextureRegion(scene.builder.texture(key,assetFrameNo));
		}else if(assetType==AssetType.AsTextureAtlas){
			TextureAtlas atlas = scene.builder.atlas(key);
			setTextureRegion(atlas.getRegions().first());
		}else if(assetType==AssetType.AsAnimations){			
			setAnimations(key);
		   play();	
		} 
	}
		
	public void setTextureRegion (TextureRegion texture) {
		sprite.setRegion(texture);
		sprite.setSize(sprite.getRegionWidth(), sprite.getRegionHeight());
		setSourceSize(sprite.getRegionWidth(), sprite.getRegionHeight());
	}

	public Animations getAnimations () {
		if (animations == null) animations = new Animations();
		return animations;
	}
	public void setAnimations (String animationsName) {		
		this.animations =  scene.builder.animations(animationsName);		
		assetType = AssetType.AsAnimations;
		assetKey  = animationsName;
		setTextureRegion(this.animations.getDefault().getKeyFrame(0));
	}

	public void setAnimations (Animations animations) {
		this.animations = animations;
		setTextureRegion(this.animations.getDefault().getKeyFrame(0));
	}

	public Animation getCurrentAnimation () {
		return currentAnimation;
	}

	public SpriteActor play () {
		play(null, PlayMode.LOOP);
		return this;
	}

	public SpriteActor play (String key) {
		play(key, PlayMode.LOOP);
		return this;
	}

	public SpriteActor play (String key, Animation.PlayMode mode) {
		if(animations==null) return this;
		assetFrame = key;
		if (key != null && animations.containsKey(key)) {
			Animation ani = animations.get(key);
			if (ani != currentAnimation) {
				currentAnimation = ani;
				currentAnimation.setPlayMode(mode);
				timer = 0;
			}
			onAnimationFinished();
		} else {
			if (currentAnimation == null) {
				currentAnimation = animations.getDefault();
			}
		}
		return this;
	}

	public void onAnimationFinished () {

	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		/** batch.setColor(getColor()); Rectangle r = getBound(); batch.draw(sprite, r.x, r.y, getPivotX(), getPivotY(),
		 * sprite.getRegionWidth(), sprite.getRegionHeight(), getScaleX(), getScaleY(), getRotation()); */
		sprite.setBounds(getLeft(), getBottom(), getSourceSize().x, getSourceSize().y);
        sprite.setColor(getColor());
        sprite.setOrigin(getPivotX(), getPivotY());
		sprite.setRotation(getRotation());
	    sprite.setScale(getScaleX(), getScaleY());
		sprite.draw(batch, parentAlpha*getAlpha());
		//batch.draw(sprite,getLeft(),getBottom(),getPivotX(), getPivotY(),getSourceSize().x,getSourceSize().y,getScaleX(),getScaleY(),getRotation());
	}

	@Override
	public void drawdebug (ShapeRenderer shape) {
		if(this.sprite!=null){		   
			shape.rect(getLeft(),getBottom(),0,0,64, 64, 1, 1, getRotation());
		}
		//super.drawdebug(shape);
	}
	public void update (float delta) {
        super.update(delta);
		if (currentAnimation != null) {
			timer += delta;
			Animation.PlayMode mode = currentAnimation.getPlayMode();
			setTextureRegion(currentAnimation.getKeyFrame(timer));
			if (mode == PlayMode.NORMAL || mode == PlayMode.LOOP) {
				if (currentAnimation.isAnimationFinished(timer)) {
					onAnimationFinished();
					animationFinished = true;
				} else {
					animationFinished = false;
				}
			}
		}
	}

	@Override
	protected void positionChanged () {
		super.positionChanged();
		sprite.setPosition(getLeft(),getBottom());
	}

	@Override
	protected void rotationChanged () {
		super.rotationChanged();
		//sprite.setOriginCenter();
		//System.out.print("ox:"+sprite.getOriginX());
		//System.out.println(" px:"+getPivotX()+" orx:"+getOrigin().x);
		//sprite.setOriginCenter(); //setOrigin(getPivotX(), getPivotY());
		sprite.setOrigin(0,0);
		sprite.setRotation(getRotation());
   }

	@Override
	protected void scaleChanged () {
		super.scaleChanged();
		sprite.setPosition(getLeft(),getBottom());
		sprite.setScale(getScaleX(), getScaleY());
	}

	@Override
	protected void sizeChanged () {
		super.sizeChanged();
		sprite.setPosition(getLeft(),getBottom());
		sprite.setScale(getScaleX(), getScaleY());
	}
	public String toString(){
		StringBuilder buffer = new StringBuilder(32);
		buffer.append(super.toString());
		buffer.append("\n  ");
		buffer.append(" texture:"); buffer.append(sprite.getTexture());
		buffer.append(" w:"); buffer.append(sprite.getRegionWidth());
		buffer.append(" h:"); buffer.append(sprite.getRegionHeight());
		buffer.append(" sw:"); buffer.append(sprite.getWidth());
		buffer.append(" sh:"); buffer.append(sprite.getHeight());
		buffer.append(" bound:");   buffer.append(sprite.getBoundingRectangle());
		buffer.append(" vertices:"); buffer.append(sprite.getVertices());
		
		return buffer.toString();
	}

/*
 * @Override public Actor hit (float x, float y, boolean touchable) { System.out.println("hit: x="+x+" y:"+y); // TODO
 * Auto-generated method stub return super.hit(x, y, touchable); }
 */
	
}
