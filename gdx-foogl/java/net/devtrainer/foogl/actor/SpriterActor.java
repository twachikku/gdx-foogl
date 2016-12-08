
package net.devtrainer.foogl.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Mainline.Key;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Player.PlayerListener;

import net.devtrainer.foogl.Scene;

public class SpriterActor extends Group implements PlayerListener {
	transient Player player;
	private String name;
	private String entityName;
	private int entityId;
	private  Vector2 offset=new Vector2(0,0);
	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	transient SpriterData data;

	public SpriterActor (Scene scene, SpriterData data) {
		this(scene, data, 0);
	}

	public SpriterActor (Scene scene, SpriterData data, String entityName) {
		super(scene);
		this.data = data;
		this.entityName = entityName;
		this.player = new Player(data.data.getEntity(entityName));
		init();
	}

	public SpriterActor (Scene scene, SpriterData data, int entityId) {
		super(scene);
		this.data = data;
		this.entityId = entityId;
		this.player = new Player(data.data.getEntity(entityId));
		init();
	}

	private void init () {
		com.brashmonkey.spriter.Rectangle b=player.getBoudingRectangle(null);
		offset.x = b.left;
		offset.y = b.bottom;
		Vector2 s=new Vector2(b.right-b.left, b.top-b.bottom);
		setSourceSize(s);
		setAnchor(0.0f, 0.0f);
		player.addListener(this);
	}

	public void play (String actionName) {
		this.player.setAnimation(actionName);
	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		Color c=getColor();
		data.drawer.setColor(c.r,c.g,c.b,getAlpha()*parentAlpha);
		data.drawer.setBatch(batch);
		data.drawer.draw(player);
		data.drawer.setColor(1,1,1,1);
	}

		@Override
	public void update (float delta) {
		super.update(delta);
			player.update();
	}

	@Override
	protected void positionChanged () {
		/**
		 * TODO : Scale issue
		 */
		super.positionChanged();
		Rectangle b=getBound();
		Vector2 center = new Vector2(b.getWidth()/2,b.getHeight()/2);
		Vector2 v1 = new Vector2(-center.x,-center.y);
		Vector2 v2 = new Vector2(offset.x,offset.y);
		if(isFlipX()) v2.x=-(b.width+v2.x);
		if(isFlipY()) v2.y=-(b.width+v2.y);
		v1.rotate(getRotation());
		v1.add(center);
		v2.rotate(getRotation());
		player.setPivot(0,0);
		player.setAngle(getRotation());
		player.setPosition(getX()-v2.x+v1.x,getY()-v2.y+v1.y);
	}

	@Override
	protected void sizeChanged () {
        super.sizeChanged();
		player.setScale(getScale().x, getScale().y);
	}

	@Override
	protected void scaleChanged () {
		super.scaleChanged();
		com.brashmonkey.spriter.Rectangle b=player.getBoudingRectangle(null);
		offset.x = b.left*getScaleX();
		offset.y = b.bottom*getScaleY();
		player.setScale(getScale().x, getScale().y);
	}
	
	@Override
	protected void rotationChanged () {
		super.rotationChanged();
		positionChanged ();
	}

	public String getEntityName () {
		return entityName;
	}

	public void setEntityName (String entityName) {
		Entity e = data.data.getEntity(entityName);
		if (e != null) {
			this.entityName = entityName;
			this.entityId = e.id;
			player.setEntity(e);
		}
	}

	public int getEntityId () {
		return entityId;
	}

	public void setEntityId (int entityId) {
		Entity e = data.data.getEntity(entityId);
		if (e != null) {
			this.entityName = e.name;
			this.entityId = e.id;
			player.setEntity(e);
		}
	}

	public Player getPlayer () {
		return player;
	}

	/** call when after finish of each animation loop. */
	@Override
	public void animationFinished (Animation animation) {

	}

	@Override
	public void animationChanged (Animation oldAnim, Animation newAnim) {
	}

	@Override
	public void preProcess (Player player) {

	}

	@Override
	public void postProcess (Player player) {
		}

	@Override
	public void mainlineKeyChanged (Key prevKey, Key newKey) {

	}

	@Override
	public void setFlipX(boolean flipX) {
		super.setFlipX(flipX);
		if((player.flippedX()==-1 && !flipX)||(player.flippedX()==1 && flipX) ) player.flipX();
	}

	@Override
	public void setFlipY(boolean flipY) {
		super.setFlipY(flipY);
		if((player.flippedY()==-1 && !flipY)||(player.flippedY()==1 && flipY) ) player.flipY();
	}
}
