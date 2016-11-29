
package net.devtrainer.foogl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.devtrainer.foogl.actor.Actor;
import net.devtrainer.foogl.actor.Group;

public abstract class Scene extends Stage implements Screen {
	boolean active = true;
	boolean visible = true;
	private Skin skin;
	private Color bgcolor = new Color(0, 0, 0, 1);
	private BitmapFont font = null;
	private Scene previousScene = null;
	boolean disposed = false;
	boolean inited = false;
	int state = 0;
	private Rectangle screenRect = new Rectangle(0, 0, 800, 600);	
	private Group root = new Group(this);
	private Group loading_root = new Group(this);
	
	final public AssetBuilder builder = Game.builder;
	final public AssetLoader  loader = Game.loader;
	final public Game game;

	private ShapeRenderer shape;
	
	public Scene () {
		this(Game.defaultGameApp, null);

	}

	public Scene (Scene previousScene) {
		this(Game.defaultGameApp, previousScene);
	}

	public Scene (Game app, Scene previousScene) {
		super(new FitViewport(app.getWidth(), app.getHeight()), app.getBatch());
		if (getCamera() instanceof OrthographicCamera) {
			OrthographicCamera cam = (OrthographicCamera)getCamera();
			// cam.rotate(1);
			// cam.setToOrtho(true);
		}
		this.game = app;
		disposed = false;
		this.previousScene = previousScene;
		active = false;
		inited = false;
		screenRect.width = app.getWidth();
		shape = new ShapeRenderer();
		shape.setColor(Color.WHITE);
		shape.setAutoShapeType(true);
	}
   
	public Group getRootGroup () {
		return root;
	}

	public Skin getSkin () {
		if (skin == null && game != null) return game.getSkin();
		return skin;
	}

	public void setSkin (Skin skin) {
		this.skin = skin;
	}

	public Game getGameApp () {
		return game;
	}

	/* Methods for handling events */
	public void onCreate () {
	}

	abstract public void onPreload ();

	abstract public void onLoaded ();

	abstract public void onUpdate (float delta);

	public void onResume () {
	}

	public void onPause () {
	}

	abstract public void onDestroy ();

	public void onPreDraw (float delta) {
		
	}

	public void onPostDraw (float delta) {
		
	}
	public void onLoading (float delta, float progress) {
		shape.begin();
		shape.setColor(Color.WHITE);
		shape.circle(getWidth()/2,getHeight()/2,progress*200);
		shape.end();
	}
	
	@Override
	final public void render (float delta) {
		checkState();
		if (state >= 2) {
			renderScene(delta);
		}else{
			renderLoading(delta);
		}
	}

	private void renderLoading (float delta) {
		loading_root.act(delta);
		Batch batch = getBatch();
		Camera camera = getCamera();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(bgcolor.r, bgcolor.g, bgcolor.b, bgcolor.a);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		if(!loading_root.hasChildren()){
			getGameApp().renderLoading(delta);
		}		
		batch.begin();
		onLoading(delta,Game.asset.getProgress());		
		loading_root.draw(batch, 1);
		batch.end();
		
	}

	public void addActor (Actor actor) {
		if (state >= 2) {
		   root.add(actor);
		}else{
			loading_root.add(actor);
		}
	}

	public void draw (Batch batch, float delta) {
		//getRoot().draw(batch, 1);
		root.draw(batch,1);
	}

	final protected void renderScene (float delta) {
		Batch batch = getBatch();
		Camera camera = getCamera();
		//getRoot().setCullingArea(getScreenBound());

		if (active) {
			update(delta);
			onUpdate(delta);
		}
		if (visible) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(bgcolor.r, bgcolor.g, bgcolor.b, bgcolor.a);
			camera.update();
			onPreDraw(delta);
			//if (!getRoot().isVisible()) return;
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			draw(batch, delta);
			batch.end();
			onPostDraw(delta);
		}
	}

	protected void update (float delta) {
		root.act(delta);
	}

	@Override
	public void show () {
		visible = true;
	}

	@Override
	public void hide () {
		visible = false;
	}

	boolean musicPlaying = false;

	@Override
	public void pause () {
		active = false;
		if (music != null) {
			musicPlaying = music.isPlaying();
			music.pause();
		}
		onPause();
	}

	@Override
	public void resume () {
		if (music != null && musicPlaying) {
			music.play();
		}
		active = true;
		onResume();
	}

	public boolean isActive () {
		return active;
	}

	public boolean isVisible () {
		return visible;
	}

	public void setVisible (boolean visible) {
		this.visible = visible;
	}

	public Color getBgcolor () {
		return bgcolor;
	}

	public void setBgcolor (Color bgcolor) {
		this.bgcolor.set(bgcolor);
	}

	public BitmapFont getFont () {
		if (font == null) font = getSkin().getFont("default");
		return font;
	}

	public void setFont (BitmapFont font) {
		this.font = font;
	}

	public void setFont (String font) {
		this.font = getSkin().getFont(font);
	}

	public GlyphLayout drawString (CharSequence str, float x, float y) {
		Batch batch = getBatch();
		if (batch == null) return null;
		if (font == null) getFont();
		if (font == null) return null;
		return font.draw(batch, str, x, y);
	}

	@Override
	public void dispose () {
		pause();
		disposed = true;
		shape.dispose();
		super.dispose();
		onDestroy();
		getGameApp().getDb().flush();
	}

	public Scene getPreviusScene () {
		return previousScene;
	}

	public void setPreviusScene (Scene previusScene) {
		this.previousScene = previusScene;
	}

	public boolean isDisposed () {
		return disposed;
	}

	@Override
	public boolean keyDown (int keyCode) {
		if (keyCode == Keys.BACK || keyCode == Keys.ESCAPE) {
			pause();
		}
		return super.keyDown(keyCode);
	}

	Music music = null;

	public Music playMusic (String name) {
		return playMusic(name, 0.3f);
	}

	public Music playMusic (String name, float volume) {
		if (music != null) music.stop();
		music = game.asset.get(name, Music.class);
		if (music != null) {
			Gdx.app.log("GameScene", "playMusic " + name);
			music.setLooping(true);
			music.setVolume(volume);
			music.play();
		}
		return music;
	}

	public void stopMusic () {
		if (music != null) music.stop();
	}

	@Override
	public void resize (int width, int height) {
		getViewport().setScreenSize(width, height);
	}

	public void checkState () {		
		if (this.state == 0) {
			builder.setScene(this);
			if (!inited) {
				onCreate();
				inited = true;
			}
			onPreload();
			state = 1;
		}
		if (this.state == 1) {
			if (Game.getAsset().update()) {
				onLoaded();
				active = true;
				state = 2;
			}
		}
	}

	public Rectangle getScreenBound () {
		OrthographicCamera cam = (OrthographicCamera)getCamera();
		screenRect.width = cam.viewportWidth * cam.zoom;
		screenRect.height = cam.viewportHeight * cam.zoom;
		screenRect.x = cam.position.x - (screenRect.width / 2);
		screenRect.y = cam.position.y - (screenRect.height / 2);
		return screenRect;
	}
	public boolean isKeyPressed(int key){
		return Gdx.input.isKeyPressed(key);
	}
	public ShapeRenderer getShape () {
		return shape;
	}
	public void restart () {
		state=0;
		getRoot().clear();
		checkState();
	}

/*
	Actor touchActor=null;
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {	
		touchActor = hit(screenX, screenY, true);
		if(touchActor!=null){
			InputEvent event=new InputEvent();
			event.setType(Type.touchDown);
			event.setStageX(screenX);
			event.setStageY(screenY);
			event.setPointer(pointer);
			event.setButton(button);
			event.setTarget(touchActor);
			touchActor.fire(event);
			return touchActor.fire(event);
		}
		return false;
		}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		touchActor = hit(screenX, screenY, true);
		if(touchActor!=null){
			InputEvent event=new InputEvent();
			event.setType(Type.touchDown);
			event.setStageX(screenX);
			event.setStageY(screenY);
			event.setPointer(pointer);
			event.setTarget(touchActor);
			return touchActor.fire(event);
		}
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if(touchActor!=null){
			InputEvent event=new InputEvent();
			event.setType(Type.touchUp);
			event.setStageX(screenX);
			event.setStageY(screenY);
			event.setPointer(pointer);
			event.setButton(button);
			event.setTarget(touchActor);
			boolean r=touchActor.fire(event);
			touchActor = null;
			return r;
		}
		return false;
	}
*/
	
}
