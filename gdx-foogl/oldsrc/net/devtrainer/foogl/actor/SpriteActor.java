
package net.devtrainer.foogl.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.devtrainer.foogl.Animations;
import net.devtrainer.foogl.Scene;

public class SpriteActor extends Actor {

	public SpriteActor (Scene scene) {
		super(scene);
	}
	public SpriteActor (Scene scene, Texture texture) {
		super(scene);
		sprite = new Sprite(texture);
		//sprite.setSize(width, height);
		setSourceSize(sprite.getRegionWidth(), sprite.getRegionHeight());
	}

	public SpriteActor (Scene scene, TextureRegion region) {
		super(scene);
		sprite = new Sprite(region);
		setSourceSize(sprite.getRegionWidth(), sprite.getRegionHeight());
	}

	public SpriteActor (Scene scene, String textureKey) {
		this(scene, scene.builder.texture(textureKey));
	}

	public SpriteActor (Scene scene, TextureAtlas atlas, String key) {
		super(scene);
		sprite.set(atlas.createSprite(key));
		setSourceSize(sprite.getRegionWidth(), sprite.getRegionHeight());
	}

	private Animations animations = null;
	private Sprite sprite = new Sprite();
	private Animation currentAnimation = null;
	private float timer = 0;
	private boolean animationFinished = true;

	public void setTextureRegion (TextureRegion texture) {
		sprite.setRegion(texture);
		sprite.setSize(sprite.getRegionWidth(), sprite.getRegionHeight());
		setSourceSize(sprite.getRegionWidth(), sprite.getRegionHeight());
	}

	public Animations getAnimations () {
		if (animations == null) animations = new Animations();
		return animations;
	}

	public void setAnimations (Animations animations) {
		this.animations = animations;
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
		/** batch.setColor(getColor()); Rectangle r = getBound(); batch.draw(sprite, r.x, r.y, getPivotX(), getPivotY(),
		 * sprite.getRegionWidth(), sprite.getRegionHeight(), getScaleX(), getScaleY(), getRotation()); */
      sprite.draw(batch, parentAlpha);
	}

	public void update (float delta) {
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
		sprite.setOrigin(getPivotX(), getPivotY());
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
