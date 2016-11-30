
package net.devtrainer.foogl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import net.devtrainer.foogl.assets.Resource;

public abstract class Game implements ApplicationListener, InputProcessor {
	static protected Game defaultGameApp;
	static protected AssetManager asset;
	static public final AssetLoader loader = new AssetLoader();
	static public final AssetBuilder builder = new AssetBuilder();

	public static AssetManager getAsset () {
		if (asset == null) asset = new AssetManager();
		return asset;
	}

	public static Game getDefaultGameApp () {
		return defaultGameApp;
	}

	private SpriteBatch batch;
	private boolean active = true;
	private int width = 800;
	private int height = 600;
	private boolean keepAspectRatio = true;
	private Skin defaultSkin = new Skin();
	private boolean confirmExit = false;
	private ObjectMap<String, Scene> scenes = new ObjectMap<String, Scene>();
	private Array<Scene> activeScenes = new Array<Scene>(); 
	private Sprite loadingSprite = null;

	Preferences db;

	private boolean loadedFlag = false;

	int exitState = 0;

	int _state = 0;

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

	float loadtimer = 0;

	private void checkState () {
		
		if (_state == 0) {
			_state = 1;
			//setLoadingSprite(Resource.getFile("loading.png"));
			onPreload();
		}
		if (_state == 1) {
			loadtimer += Gdx.graphics.getRawDeltaTime();
			if (asset.update()) {
				_state = 2;
				onLoaded();
			}
		}
		if (_state == 2) {
         if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)){
         	if(onExit()) exitState=2;
         }
		}
	}

	@Override
	public void create () {
		// Dialog.fadeDuration = 0;
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		db = Gdx.app.getPreferences(this.getClass().toString());
		scenes.clear();
		activeScenes.clear();
		checkState();
	}

	@Override
	public void dispose () {
		db.flush();
		for(Scene s: scenes.values()){
			s.dispose();
		}
		defaultSkin.dispose();
		asset.clear();
		batch.dispose();
	}

	public SpriteBatch getBatch () {
		return batch;
	}

	public Preferences getDb () {
		return db;
	}

	public int getHeight () {
		return height;
	}


	public Skin getSkin () {
		return defaultSkin;
	}

	public int getWidth () {
		return width;
	}

	public boolean isActive () {
		return active;
	}

	public boolean isConfirmExit () {
		return confirmExit;
	}

	public boolean isKeepAspectRatio () {
		return keepAspectRatio;
	}

	abstract public void onLoaded ();

	public boolean onExit () {
		return true;
	}

	abstract public void onPreload ();

	@Override
	public void pause () {
		active = false;
		for(Scene s:activeScenes){
			s.pause();
		}
	}

	@Override
	public void render () {
		if (active) if (exitState == 2) {
			Gdx.app.exit();
			return;
		}
		checkState();
		if (_state >= 2) {
			for(Scene s:activeScenes){
				s.render(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
			}
			if(activeScenes.size==0){
				exitState = 2;
			}
		} else {
			if (_state == 1 && loadingSprite != null) {
				renderLoading(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
			}
		}
	}

	public ObjectMap<String, Scene> getScenes () {
		return scenes;
	}

	public Array<Scene> getActiveScenes () {
		return activeScenes;
	}

	public void renderLoading (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		float p=asset.getProgress();
		if(loadingSprite!=null){
		  loadingSprite.setScale(p,1);
		  loadingSprite.draw(batch);
		}
		batch.end();
	}

	@Override
	public void resize (int width, int height) {
		/*
		this.width=width;
		this.height=height;
		for(Scene s:activeScenes){
		  s.resize(this.width, this.height);
		}
		*/
	}

	@Override
	public void resume () {
		// System.out.println("resume ()");
		active = true;
		for(Scene s:activeScenes){
			 s.resume();
		}
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

	public void showScene (String name) {
		Scene s = scenes.get(name);
		if(s!=null){
	  	   showScene(s);
		}
	}
	public void showScene (Scene scene) {
		if(!activeScenes.contains(scene, false)){
		  this.activeScenes.add(scene);
		  Gdx.input.setInputProcessor(scene);
		  scene.restart();
		}
	}
	public void startScene (String name) {
		Scene s = scenes.get(name);
		if(s!=null){
	  	   startScene(s);
		}
	}
	public void startScene (Scene scene) {
		this.activeScenes.clear();
		this.activeScenes.add(scene);
		//Gdx.input.setInputProcessor(scene);
		Gdx.input.setInputProcessor(this);
		scene.restart();
	}
	
	public void addScene(Scene scene){
		addScene(scene.getClass().getSimpleName(),scene);
	}
	public void addScene(String name, Scene scene){
		System.out.println("add scene:"+name);
		scenes.put(name,scene);
	}

	public void setWidth (int width) {
		this.width = width;
	}

	public Sprite getLoadingSprite () {
		return loadingSprite;
	}

	public void setLoadingSprite (FileHandle file) {
		Texture texture = new Texture(file);
		float gw = Gdx.graphics.getWidth();
		float gh = Gdx.graphics.getHeight();
		float w = texture.getWidth(), h = texture.getHeight();
		float x = (gw - w) / 2f;
		float y = (gh - h) / 2f;
		this.loadingSprite = new Sprite(texture);
		this.loadingSprite.setPosition(x, y);
		//	System.out.println("w:" + w + " h:" + h + " x:" + x + " y:" + y + "  width:" + gw + " height:" + gh);
	}
	
	public boolean isKeyPressed(int key){
		return Gdx.input.isKeyPressed(key);
	}
	

	@Override
	public boolean keyDown (int keycode) {
		if(activeScenes!=null){
		 for(Scene s:activeScenes){
			 s.keyDown(keycode);
		 }
		}
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		if(activeScenes!=null){
			 for(Scene s:activeScenes){
				 s.keyUp(keycode);
			 }
		}
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		if(activeScenes!=null){
			 for(Scene s:activeScenes){
				 s.keyTyped(character);
			 }
		}
		return false;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		if(activeScenes!=null){
			 for(Scene s:activeScenes){
				 s.touchDown(screenX, screenY, pointer, button);
			 }
		}
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if(activeScenes!=null){
			 for(Scene s:activeScenes){
				 s.touchUp(screenX, screenY, pointer, button);
			 }
		}
		return false;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		if(activeScenes!=null){
			 for(Scene s:activeScenes){
				 s.touchDragged(screenX, screenY, pointer);
			 }
		}
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		if(activeScenes!=null){
			 for(Scene s:activeScenes){
				 s.mouseMoved(screenX, screenY);
			 }
		}
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		if(activeScenes!=null){
			 for(Scene s:activeScenes){
				 s.scrolled(amount);
			 }
		}
		return false;
	}

	
}
