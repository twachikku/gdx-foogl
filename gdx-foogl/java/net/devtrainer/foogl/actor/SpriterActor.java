
package net.devtrainer.foogl.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Mainline.Key;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Player.PlayerListener;

import net.devtrainer.foogl.Scene;

public class SpriterActor extends Actor implements PlayerListener {
	Player player;
	String entityName;
	int entityId;
	SpriterData data;

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
		setOrigin(player.getPivotX(), player.getPivotY());
		setAnchor(0.5f, 0);
		player.addListener(this);
	}

	public void play (String actionName) {
		this.player.setAnimation(actionName);
		com.brashmonkey.spriter.Rectangle b=player.getBoudingRectangle(null);
		Vector2 s=new Vector2(b.right-b.left, b.top-b.bottom);
		setSourceSize(s); 
		//System.out.println(this);
	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
		data.drawer.setBatch(batch);
		data.drawer.draw(player);
	}

		@Override
	public void update (float delta) {
		player.update();
	}

	@Override
	protected void positionChanged () {
		super.positionChanged();
		Rectangle b=getBound();
		player.setPosition(b.x, b.y);
	}

	@Override
	protected void sizeChanged () {
      super.sizeChanged();
		player.setScale(getScale().x, getScale().y);
	}

	@Override
	protected void scaleChanged () {
		super.scaleChanged();
      player.setScale(getScale().x, getScale().y);
	}
	
	@Override
	protected void rotationChanged () {
		super.rotationChanged();
		Vector2 o = getOrigin();
		player.setPivot(getPivotX(), getPivotY());
		player.setAngle(getRotation());
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
}
