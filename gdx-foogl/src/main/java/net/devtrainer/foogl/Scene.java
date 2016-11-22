
package net.devtrainer.foogl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class Scene extends Stage implements Screen {
	boolean active = true;
	boolean visible = true;
	Game ownerApp = null;
	private Skin skin;
	private Color bgcolor = new Color(0, 0, 0, 1);
	private BitmapFont font = null;
	private Scene previousScene = null;
	boolean disposed = false;
	boolean inited = false;
	int state = 0;

	final public Builder add = new Builder(this);
	final public AssetLoader load = Game.loader;

	public Scene () {
		this(Game.defaultGameApp, null);

	}

	public Scene (Scene previousScene) {
		this(Game.defaultGameApp, previousScene);
	}

	public Scene (Game app, Scene previousScene) {
		super(new FitViewport(app.getWidth(), app.getHeight()), app.getBatch());
		this.ownerApp = app;
		disposed = false;
		this.previousScene = previousScene;
		active = false;
		inited = false;
	}

	public Skin getSkin () {
		if (skin == null && ownerApp != null) return ownerApp.getSkin();
		return skin;
	}

	public void setSkin (Skin skin) {
		this.skin = skin;
	}

	public Game getGameApp () {
		return ownerApp;
	}

	final public void init () {
		if (!inited) onCreate();
		inited = true;
	}

	abstract public void onPreload ();

	abstract public void onCreate ();

	abstract public void onUpdate (float delta);

	public void onActive () {
	}

	public void onInActive () {
	}

	abstract public void onDestroy ();

	public void onRender () {
	};

	@Override
	final public void render (float delta) {
		checkState();
		if (state >= 2) {
			renderScene(delta);
		}
	}

	public void draw (Batch batch, float delta) {
		getRoot().draw(batch, 1);
	}

	final protected void renderScene (float delta) {
		Batch batch = getBatch();
		Camera camera = getCamera();
		if (active) {
			act(delta);
			onUpdate(delta);
		}
		if (visible) {
			onRender();
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(bgcolor.r, bgcolor.g, bgcolor.b, bgcolor.a);
			camera.update();
			if (!getRoot().isVisible()) return;
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			draw(batch, delta);
			ownerApp.getEffectManager().render(batch, delta);
			batch.end();
			afterDraw(delta);
		}
	}

	public void afterDraw (float delta) {

	}

	@Override
	public void show () {
		visible = true;
	}

	@Override
	public void hide () {
		visible = false;
	}

	@Override
	public void pause () {
		active = false;
	}

	@Override
	public void resume () {
		setActive(true);
	}

	public boolean isActive () {
		return active;
	}

	public void setActive (boolean active) {
		if (this.active != active) {
			this.active = active;
			if (!active) {
				if (music != null) music.stop();
				onActive();
			} else {
				onInActive();
			}
		}
	}

	public boolean isVisible () {
		return visible;
	}

	public void setVisible (boolean visible) {
		this.visible = visible;
	}

	public Actor addActor (Actor actor, float x, float y) {
		actor.setPosition(x, y);
		addActor(actor);
		return actor;
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
		setActive(false);
		disposed = true;
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

	public void exit () {
		setActive(false);
	}

	public boolean isDisposed () {
		return disposed;
	}

	@Override
	public boolean keyDown (int keyCode) {
		if (keyCode == Keys.BACK || keyCode == Keys.ESCAPE) {
			exit();
		}
		return super.keyDown(keyCode);
	}

	public PooledEffect playEffect (String name, float x, float y) {
		return ownerApp.getEffectManager().playEffect(name, x, y);
	}

	public EffectManager getEffectManager () {
		return ownerApp.getEffectManager();
	}

	Music music = null;

	public Music playMusic (String name) {
		return playMusic(name, 0.3f);
	}

	public Music playMusic (String name, float volume) {
		if (music != null) music.stop();
		music = ownerApp.asset.get(name, Music.class);
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
			onPreload();
			state = 1;
		}
		if (this.state == 1) {
			if (Game.getAsset().update()) {
				onCreate();
				active = true;
				state = 2;
			}
		}
	}
}
