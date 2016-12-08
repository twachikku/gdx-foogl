
package net.devtrainer.foogl.plugin;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import net.devtrainer.foogl.Scene;
import net.devtrainer.foogl.actor.Actor;

public class JavaScriptPlugin extends ScenePlugin {
	static Context    globalCx = null;
	Scriptable globalScope = null;	
	Context cx=null;
	Scriptable thisScope;
	String filename;
	boolean autoReload=true;
	boolean runned = false;
	long fileTime = 0;

	Function fOnCreate, fOnUpdate, fOnPreload, fOnLoaded, fOnDestroy, fOnActorRemoved, fOnActorAdded, fOnResize, fOnLoading,
		fOnResume, fOnPause, fOnPostDraw, fOnPreDraw;

	float reloadTime=0;

	public JavaScriptPlugin () {

	}

	public JavaScriptPlugin (String filename) {
		this.filename = filename;
	}

	public FileHandle getScriptPath () {
		FileHandle f = Gdx.files.internal("js");
		if (filename == null) {
			filename = getScene().getClass().getSimpleName() + ".js";
		}
		return f.child(filename);
	}
	@Override
	public void init (Scene scene) {
		super.init(scene);
		
		if(Gdx.app.getType() != Application.ApplicationType.Desktop){
			autoReload =false;
		}
		if(autoReload){
		   getScene().game.setPauseOnInactive(false);	
		}
        if(globalCx==null){
           globalCx =  Context.enter();
        }
		FileHandle file = getScriptPath();
		if (file.exists()) {
			runScript(file);
		} else {
			System.out.println(file + " is not found.");
		}
	}

	public boolean isAutoReload() {
		return autoReload;
	}

	private Function js (String name) {
		Object fObj = thisScope.get(name, thisScope);
				
		if(fObj==Scriptable.NOT_FOUND){
			fObj = globalScope.get(name, globalScope);			
		}
		
		if (fObj instanceof Function) {
			return (Function)fObj;
		}
		return null;
	}

	@Override
	public void onActorAdded (Actor a) {
		if (fOnActorAdded != null) {
			Object arg[] = {a};
			fOnActorAdded.call(cx, globalScope, thisScope, arg);
		}
	}

	@Override
	public void onActorRemoved (Actor a) {
		if (fOnActorRemoved != null) {
			Object arg[] = {a};
			fOnActorRemoved.call(cx, globalScope, thisScope, arg);
		}
	}
    
	@Override
	public void onCreate () {
		if (fOnCreate != null) {
			Object arg[] = {};
			fOnCreate.call(cx, globalScope, thisScope, arg);
		}
	}
	@Override
	public void onDestroy () {
		if (fOnDestroy != null) {
			Object arg[] = {};
			fOnDestroy.call(cx, globalScope, thisScope, arg);
		}
		if (runned) {			
			Context.exit();
			runned = false;
		}
	}

	@Override
	public void onLoaded () {
		if (fOnLoaded != null) {
			Object arg[] = {};
			fOnLoaded.call(cx, globalScope, thisScope, arg);
		}

	}

	@Override
	public void onLoading (float delta, float progress) {
		if (fOnLoading != null) {
			Object arg[] = {delta, progress};
			fOnLoading.call(cx, globalScope, thisScope, arg);
		}

	}

	@Override
	public void onPause () {
		if (fOnPause != null) {
			Object arg[] = {};
			fOnPause.call(cx, globalScope, thisScope, arg);
		}
		

	}

	@Override
	public void onPostDraw (float delta) {
		if (fOnPostDraw != null) {
			Object arg[] = {delta};
			fOnPostDraw.call(cx, globalScope, thisScope, arg);
		}
	}

	@Override
	public void onPreDraw (float delta) {
		if (fOnPreDraw != null) {
			Object arg[] = {delta};
			fOnPreDraw.call(cx, globalScope, thisScope, arg);
		}

	}

	@Override
	public void onPreload () {
		if (fOnPreload != null) {
			Object arg[] = {};
			fOnPreload.call(cx, globalScope, thisScope, arg);
		}
	}

	@Override
	public void onResize () {
		if (fOnResize != null) {
			Object arg[] = {};
			fOnResize.call(cx, globalScope, thisScope, arg);
		}
	}

	@Override
	public void onResume () {
		if (fOnResume != null) {
			Object arg[] = {};
			fOnResume.call(cx, globalScope, thisScope, arg);
		}
	}

	@Override
	public void onUpdate (float delta) {
		if(autoReload){
		  reloadTime+=delta;
		  if(reloadTime>2f){
			reloadTime=0;
			FileHandle file=getScriptPath();
			if(file.exists() && file.lastModified()>fileTime){
				System.out.println("restart file:"+file);
				runScript(file);
				getScene().requestRestart();	
      			return;
			}
		   }
		}
		if (fOnUpdate != null) {
			Object arg[] = {delta};
			fOnUpdate.call(cx, globalScope, thisScope, arg);
		}
	}
	
	public void addClass(Class c){
		String name=c.getSimpleName();
		ScriptableObject.putProperty(globalScope,name,cx.getWrapFactory().wrapJavaClass(cx,globalScope,c));
	}
	private void importGdxClasses() {
		addClass(com.badlogic.gdx.Gdx.class);
		addClass(com.badlogic.gdx.graphics.Color.class);
		addClass(MathUtils.class);
		addClass(Interpolation.class);
		addClass(Vector2.class);
	}
	private void runScript (FileHandle file) {
		if(autoReload) {
			fileTime = file.lastModified() + 10;
		}
		if(!runned){			
		  Context.exit();   
		}		
	    cx = Context.enter();
		cx.setOptimizationLevel(-1);
		runned=true;
		globalScope = cx.initStandardObjects();
		thisScope = globalScope; //(Scriptable) Context.javaToJS(getScene(), globalScope);
		
		importGdxClasses();
		
		ScriptableObject.putProperty(globalScope, "gdx", Context.javaToJS(System.out, globalScope));		
		ScriptableObject.putProperty(globalScope, "out", Context.javaToJS(System.out, globalScope));
		ScriptableObject.putProperty(globalScope, "game", Context.javaToJS(getScene().game, globalScope));
		ScriptableObject.putProperty(globalScope, "game", Context.javaToJS(getScene().game, globalScope));
		ScriptableObject.putProperty(globalScope, "scene", Context.javaToJS(getScene(), globalScope));
		ScriptableObject.putProperty(globalScope, "loader", Context.javaToJS(getScene().loader, globalScope));
		ScriptableObject.putProperty(globalScope, "builder", Context.javaToJS(getScene().builder, globalScope));
		ScriptableObject.putProperty(globalScope, "load", Context.javaToJS(getScene().loader, globalScope));
		ScriptableObject.putProperty(globalScope, "add", Context.javaToJS(getScene().builder, globalScope));
		
		cx.evaluateString(globalScope, file.readString(), file.name(), 1, null);

		fOnCreate = js("create");
		fOnUpdate = js("update");
		fOnPreload = js("preload");
		fOnLoaded = js("loaded");
		fOnDestroy = js("destroy");
		fOnActorRemoved = js("actorRemoved");
		fOnActorAdded = js("actorAdded");
		fOnResize = js("resize");
		fOnLoading = js("loading");
		fOnResume = js("resume");
		fOnPause = js("pause");
		fOnPostDraw = js("postDraw");
		fOnPreDraw = js("preDraw");
		//System.out.println(fOnUpdate);
	}

	

	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
