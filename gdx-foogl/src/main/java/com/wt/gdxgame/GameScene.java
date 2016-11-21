package com.wt.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class GameScene extends Stage implements Screen {
	boolean active = true;
	boolean visible = true;
	GameApp ownerApp = null;
	private Skin skin;	
	private Color bgcolor = new Color(0,0,0,1);
	private BitmapFont font=null;
	private GameScene previousScene=null;
	boolean disposed = false;
	boolean inited=false;
		
	public GameScene() {
		this(GameApp.defaultGameApp,null);
	}
	public GameScene(GameScene previousScene) {
		this(GameApp.defaultGameApp,previousScene);
	}
	public GameScene(GameApp ownerApp,GameScene previousScene) {
		super(ownerApp.getWidth(), ownerApp.getHeight(), ownerApp
				.isKeepAspectRatio());
		this.ownerApp = ownerApp;
		disposed=false;
		this.previousScene = previousScene;
		active=false;
		inited=false;
	}


	public Skin getSkin() {
		if(skin==null && ownerApp!=null) return ownerApp.getSkin();
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public GameApp getGameApp() {
		return ownerApp;
	}
	final public void init(){
	 if(!inited) onInit();
	 inited=true;
	}

	abstract public void onInit();
	abstract public void onEnter();
	abstract public void onUpdate(float delta);
	public boolean onExit(){ return true; }
	abstract public void onDestroy();

	@Override
	final public void render(float delta) {
		renderScene(delta);		
	}

	public void draw(SpriteBatch batch, float delta){
		getRoot().draw(batch, 1);		
	}

    final protected void renderScene(float delta) {
		SpriteBatch batch = getSpriteBatch();
		Camera camera=getCamera();
		
		if (active) {
			act(delta);
			onUpdate(delta);
		}
		if (visible) {
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(bgcolor.r, bgcolor.g, bgcolor.b, bgcolor.a);
			camera.update();
			if (!getRoot().isVisible()) return;
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			draw(batch,delta);
			ownerApp.getEffectManager().render(batch, delta);
			batch.end();
			afterDraw(delta);
		}
	}

	public void afterDraw(float delta) {
		
	}

	@Override
	public void resize(int width, int height) {
		setViewport(width, height, ownerApp.isKeepAspectRatio());
	}

	@Override
	public void show() {
		visible = true;
	}

	@Override
	public void hide() {
		visible = false;
	}

	@Override
	public void pause() {
		active = false;

	}

	@Override
	public void resume() {
		setActive(true);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		if(active) init();
		if(this.active!=active){
		  if(active) onEnter();
		  if(!active){
			 if(music!=null) music.stop();
			 if(!onExit()) return;
		  }
		  this.active = active;
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Actor addActor(Actor actor,float x,float y){
		actor.setPosition(x, y);
		addActor(actor);
		return actor;
	}

	public Color getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgcolor.set(bgcolor);
	}
	public BitmapFont getFont() {
		if(font==null)
			font = getSkin().getFont("default");
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
	public void setFont(String font) {
		this.font = getSkin().getFont(font);	
	}

	public TextBounds drawString(CharSequence str, float x,
			float y) {		
		SpriteBatch batch=getSpriteBatch();
		if(batch==null) return null;
		if(font==null) getFont();
		if(font==null) return null;
		return font.draw(batch, str, x, y);
	}

	@Override
	public void dispose() {
		setActive(false);
		disposed=true;
		super.dispose();
		onDestroy();
		getGameApp().getDb().flush();
	}		

	public GameScene getPreviusScene() {
		return previousScene;
	}

	public void setPreviusScene(GameScene previusScene) {
		this.previousScene = previusScene;
	}
	public void exit(){
		setActive(false);
	}

	public boolean isDisposed() {
		return disposed;
	}	
	@Override
	public boolean keyDown(int keyCode) {
		if(keyCode == Keys.BACK || keyCode == Keys.ESCAPE){
		   exit();
	    }
		return super.keyDown(keyCode);
	}
	
	public PooledEffect playEffect(String name, float x,float y){
		return ownerApp.getEffectManager().playEffect(name,x,y);
	}
	public EffectManager getEffectManager() {
		return ownerApp.getEffectManager();
	}
	public Sound playSound(String name) {
		return ownerApp.playSound(name);
	}	
	Music music=null;
	public Music playMusic(String name){
		return playMusic(name,0.3f);
	}
	public Music playMusic(String name,float volume){
		if(music!=null) music.stop();		
		music = ownerApp.asset.get(name,Music.class);
		if(music!=null){
			Gdx.app.log("GameScene", "playMusic "+name);
			music.setLooping(true);
			music.setVolume(volume);
			music.play();
		}
		return music;
	}
	public void stopMusic(){
		if(music!=null) music.stop();				
	}
}
