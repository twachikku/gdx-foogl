
package net.devtrainer.foogl.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.StringBuilder;

import net.devtrainer.foogl.Game;
import net.devtrainer.foogl.Scene;
import net.devtrainer.foogl.action.ActionBuilder;

abstract public class Actor {
	static private int ID_COUNTER = 0; 
	static public float min (float v, float m) {
		if (v >= 0 && v < m) return m;
		if (v < 0 && v > -m) return -m;
		return v;
	}

	transient final public ActionBuilder actions = new ActionBuilder(this);
	private int id;
	private Vector2 anchor = new Vector2(0, 0);
	private Vector2 origin = new Vector2(0.5f, 0.5f);
	private Vector2 size = new Vector2(1, 1);
	private Vector2 scale = new Vector2(1, 1);
	transient private Rectangle bound = new Rectangle();
	private float x, y, rotation;

	private Color color=Color.WHITE;

	transient public final Game game;

	transient public final Scene scene;

	private int z;

	transient protected boolean killed = false;

	protected boolean visible = false;

	transient private Group parent;

	public Actor (Scene scene) {
		super();
		this.id = ID_COUNTER++;
		this.scene = scene;
		this.game = scene.game;
	}

	final public void act (float delta){
		actions.act(delta);
		update(delta);
	};
	public abstract void update(float delta);
	public abstract void draw (Batch batch, float parentAlpha);

	public Vector2 getAnchor () {
		return anchor;
	}

	public float getBottom () {
		return getBound().y;
	}

	public Rectangle getBound () {
		return bound;
	}
   public Vector2 getCenter(){
   	Vector2 v=new Vector2(); 
   	v.x=x+origin.x*size.x;
   	v.y=y+origin.y*size.y;
   	return v; 
   }
	public Color getColor () {
		return color;
	}

	public float getHeight () {
		return bound.height;
	}

	public float getLeft () {
		return getBound().x;
	}

	public Vector2 getOrigin () {
		return origin;
	}

	public Group getParent () {
		return parent;
	}

	public float getPivotX () {
		return origin.x * size.x;
	}

	public float getPivotY () {
		return origin.y * size.y;
	}

	public float getRotation () {
		return rotation;
	}

	public Vector2 getScale () {
		return scale;
	}

	public float getScaleX () {
		return scale.x;
	}

	public float getScaleY () {
		return scale.y;
	}

	public Scene getScene () {
		return scene;
	}

	protected Vector2 getSourceSize () {
		return size;
	}

	public float getWidth () {
		return  bound.width;
	}

	public float getX () {
		return x;
	}

	public float getY () {
		return y;
	}

	public int getZ () {
		return z;
	}

	public boolean isKilled () {
		return killed;
	}

	public boolean isVisible () {
		return visible;
	}

	public void kill () {
		killed = true;
	}

	public void onDestroy () {
	}

	public void onKill () {
	}

	protected void positionChanged () {
		bound.x = x - anchor.x * bound.width;
		bound.y = y - anchor.y * bound.height;
	}

	protected void rotationChanged () {
	}

	protected void scaleChanged () {
		Vector2 size = getSourceSize();
		bound.width =size.x * scale.x;
		bound.height=size.y * scale.y;
		bound.x = x - anchor.x * bound.width;
		bound.y = y - anchor.y * bound.height;
	}

	public void setAnchor (float x, float y) {
		if (x < 0) x = 0;
		if (x > 1) x = 1;
		if (y < 0) y = 0;
		if (y > 1) y = 1;
		anchor.x = x;
		anchor.y = y;
		scaleChanged ();
	}

	public void setColor (Color color) {
		this.color = color;
	}

	public void setHeight (float height) {
		setSize(bound.width, height);
	}

	public void setOrigin (float x, float y) {
		if (x < 0) x = 0;
		if (x > 1) x = 1;
		if (y < 0) y = 0;
		if (y > 1) y = 1;
		origin.x = x;
		origin.y = y;
	}
	public void setOriginPosition (float x, float y) {
		setLeft(x-origin.x*size.x);
		setBottom(y-origin.y*size.y);
		positionChanged();
	}

	public void setLeft (float v) {
		setX(v+anchor.x*getWidth());
	}
	
	public void setBottom (float v) {
		setY(v+anchor.y*getHeight());
	}

	public void setParent (Group parent) {
		this.parent = parent;
	}

	public void setRotation (float rotation) {
		if (this.rotation != rotation) {
			this.rotation = rotation;
			rotationChanged();
		}
	}

	public void setScale (float sx, float sy) {
		scale.x = min(sx, 0.001f);
		scale.y = min(sy, 0.001f);
		this.scale.set(sx, sy);
		scaleChanged();
	}

	public void setScale (float scale) {
		setScale(scale, scale);
	}
	
	public void setScale (Vector2 scale) {
		setScale(scale.x, scale.y);
	}

	public void setScaleX (float sx) {
		setScale(sx, scale.y);
	}

	public void setScaleY (float sy) {
		setScale(scale.x, sy);
	}

	public void setSourceSize (float sx,float sy) {
		if (this.size.x != sy || this.size.y != sy) {
			if (sx <= 0) sx = 1;
			if (sy <= 0) sy = 1;
			this.size.x = sx;
			this.size.y = sy;
			scaleChanged();
		}
	}
	public void setSourceSize (Vector2 size) {
		setSourceSize(size.x, size.y);
	}

	public void setVisible (boolean visible) {
		this.visible = visible;
	}

	public void setSize (float width, float height) {
		if (this.bound.width != width || this.bound.height != height) {
			this.bound.width = width;
			this.bound.height = height;
			sizeChanged();
		}
	}

	public void setWidth (float width) {
		setSize(width, this.bound.height);
	}

	public void setX (float x) {
		setPosition(x, y);
	}

	public void setY (float y) {
		setPosition(x, y);
	}

	public void setPosition (float x, float y) {
		if (x != this.x || y != this.y) {
			this.x = x;
			this.y = y;
			positionChanged();
		}
	}

	public void setZ (int z) {
		this.z = z;
	}

	protected void sizeChanged () {
		Vector2 size = getSourceSize();
		this.scale.x = this.bound.width / size.x;
		this.scale.y = this.bound.height / size.y;
		bound.x = x - anchor.x * this.bound.width;
		bound.y = y - anchor.y * this.bound.height;
	}
	public String toString(){
		StringBuilder buffer = new StringBuilder(32);
		buffer.append(getClass().toString());
		buffer.append(" x:");buffer.append(x);
		buffer.append(" y:");buffer.append(y);
		buffer.append(" bound:");buffer.append(bound);
		buffer.append(" scale:");buffer.append(scale);
		buffer.append(" size:");buffer.append(size);
		buffer.append(" anchor:");buffer.append(anchor);
		buffer.append(" origin:");buffer.append(origin);
		buffer.append(" rotation:");buffer.append(rotation);
		return buffer.toString();
	}

	public void setPosition (Vector2 position) {
		setPosition(position.x, position.y);		
	}
	
	public Actor setStyle(String jsonText){
		//Json json = JsonReader
		return this;
	}
	public int getId () {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public void drawdebug (ShapeRenderer shape) {
		
	}	
}
