
package net.devtrainer.foogl;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

import net.devtrainer.foogl.assets.Resource;

public abstract class Game implements ApplicationListener {
	static protected Game defaultGameApp;
	static protected AssetManager asset;
	static protected AssetLoader loader = new AssetLoader();

	public static Game getDefaultGameApp () {
		return defaultGameApp;
	}

	private SpriteBatch batch;
	private boolean active = true;
	private Scene scene;
	private int width = 800;
	private int height = 600;
	private boolean keepAspectRatio = true;
	private Skin defaultSkin = new Skin();
	private HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	private boolean confirmExit = false;
	private EffectManager effectManager;

	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();

	Preferences db;

	private boolean loadedFlag = false;

	int exitState = 0;

	public Game () {
		this(800, 600, true);
	}

	public Game (int width, int height, boolean keepAspectRatio) {
		super();
		this.width = width;
		this.height = height;
		this.keepAspectRatio = keepAspectRatio;
		defaultGameApp = this;
	}

	/** Load a font to the skin by file name without ".fnt" .
	 * @param name
	 * @return */
	public BitmapFont addFont (String name) {
		FileHandle f, fp;
		f = Resource.getFile(name + ".fnt");
		fp = Resource.getFile(name + "_0.png");

		if (!f.exists()) {
			f = Gdx.files.internal("font/" + name + ".fnt");
			fp = Gdx.files.internal("font/" + name + "_0.png");
		}
		if (f.exists()) {
			BitmapFont b = new BitmapFont(f, fp, false);
			defaultSkin.add(name, b);
			return b;
		}
		return null;
	}

	public LabelStyle addLabelStyle (String name) {
		return addLabelStyle(name, name, Color.WHITE);
	}

	public LabelStyle addLabelStyle (String name, String fontname, Color color) {
		if (!defaultSkin.has(fontname, BitmapFont.class)) {
			if (addFont(fontname) == null) return null;
		}
		LabelStyle lstyle = new LabelStyle(defaultSkin.getFont(fontname), color);
		defaultSkin.add(name, lstyle);
		return lstyle;
	}

	@Override
	public void create () {
		// Dialog.fadeDuration = 0;
		batch = new SpriteBatch();
		effectManager = new EffectManager();
		Gdx.input.setCatchBackKey(true);
		db = Gdx.app.getPreferences(this.getClass().toString());
		initSkin();
		setScene(new LoadingScene());
		checkState();
	}

	private void checkState () {
		if (state == 0) {
			state = 1;
			onPreload();
		}
		if (state == 1) {
			if (asset.update()) {
				state = 2;
				onCreate();
			}
		}
	}

	int state = 0;

	abstract public void onPreload ();

	abstract public void onCreate ();

	@Override
	public void dispose () {
		db.flush();
		if (scene != null) scene.dispose();
		defaultSkin.dispose();
		for (Sound s : sounds.values()) {
			if (s != null) s.dispose();
		}
		asset.clear();
		effectManager.dispose();
		batch.dispose();

	}

	public Preferences getDb () {
		return db;
	}

	public EffectManager getEffectManager () {
		return effectManager;
	}

	public int getHeight () {
		return height;
	}

	public Scene getScene () {
		return scene;
	}

	public Skin getSkin () {
		return defaultSkin;
	}

	public int getWidth () {
		return width;
	}

	private void initSkin () {
		defaultSkin = new Skin(Resource.getFile("uiskin.json"));
		// defaultSkin.add("default",new BitmapFont());

		NinePatchDrawable dnbox[] = new NinePatchDrawable[8];
		for (int i = 1; i <= 4; i++) {
			Texture box1 = new Texture(Resource.getFile("images/box" + i + ".png"));
			Texture boxr = new Texture(Resource.getFile("images/box" + i + "r.png"));

			NinePatch nbox1 = new NinePatch(box1, 10, 10, 5, 5);
			NinePatch nbox1r = new NinePatch(boxr, 10, 10, 5, 5);
			dnbox[i - 1] = new NinePatchDrawable(nbox1);
			dnbox[i + 3] = new NinePatchDrawable(nbox1r);
			defaultSkin.add("box" + i, nbox1);
			defaultSkin.add("box" + i + "r", nbox1r);
		}
		// addLabelStyle("default");
		addLabelStyle("icon");
		// WindowStyle ws = new WindowStyle(defaultSkin.getFont("default-font"),Color.WHITE,dnbox[0]);
		// defaultSkin.add("default",ws);
		ButtonStyle bs = new ButtonStyle(dnbox[0], dnbox[4], dnbox[0]);
		bs.over = dnbox[0];
		TextButtonStyle tbs = new TextButton.TextButtonStyle(bs.up, bs.down, bs.checkedOver, defaultSkin.getFont("default-font"));
		tbs.over = dnbox[0];
		tbs.fontColor = Color.WHITE;
		defaultSkin.add("default", bs);
		defaultSkin.add("default", tbs);
		ImageButtonStyle ibs = new ImageButtonStyle(bs);
		defaultSkin.add("default", ibs);
	}

	public boolean isConfirmExit () {
		return confirmExit;
	}

	public boolean isKeepAspectRatio () {
		return keepAspectRatio;
	}

	public boolean onExit () {
		return true;
	}

	@Override
	public void pause () {
		active = false;
		if (scene != null) scene.pause();
	}

	@Override
	public void render () {
		if (active) if (exitState == 2) {
			Gdx.app.exit();
			return;
		}
		checkState();
		if (state >= 2 && scene != null) {
			scene.render(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		}
	}

	@Override
	public void resize (int width, int height) {
		if (scene != null) scene.resize(this.width, this.height);
	}

	@Override
	public void resume () {
		// System.out.println("resume ()");
		active = true;
		if (scene != null) scene.resume();
	}

	public void setConfirmExit (boolean confirmExit) {
		this.confirmExit = confirmExit;
	}

	public void setHeight (int height) {
		this.height = height;
	}

	public void setKeepAspectRatio (boolean keepAspectRatio) {
		this.keepAspectRatio = keepAspectRatio;
	}

	public void setScene (Scene scene) {
		if (scene != this.scene) {
			if (scene != null) {
				Gdx.input.setInputProcessor(scene);
				scene.checkState();
			}
			this.scene = scene;
		}
	}

	public void setWidth (int width) {
		this.width = width;
	}

	public boolean isActive () {
		return active;
	}

	public SpriteBatch getBatch () {
		return batch;
	}

	public static AssetManager getAsset () {
		if (asset == null) asset = new AssetManager();
		return asset;
	}
}
