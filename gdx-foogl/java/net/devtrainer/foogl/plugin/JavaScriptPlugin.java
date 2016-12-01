
package net.devtrainer.foogl.plugin;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import net.devtrainer.foogl.Scene;
import net.devtrainer.foogl.actor.Actor;

public class JavaScriptPlugin extends ScenePlugin {
	Context cx = null;
	Scriptable scope;
	String filename;

	public JavaScriptPlugin () {

	}

	public JavaScriptPlugin (String filename) {
		this.filename = filename;
	}

	@Override
	public void init (Scene scene) {
		super.init(scene);
		FileHandle file = getScriptPath();
		if (file.exists()) {
			runScript(file);
		} else {
			System.out.println(file + " is not found.");
		}
	}

	private void runScript (FileHandle file) {
		cx = Context.enter();
		scope = cx.initStandardObjects();
		ScriptableObject.putProperty(scope, "out", Context.javaToJS(System.out, scope));
		ScriptableObject.putProperty(scope, "game", Context.javaToJS(getScene().game, scope));
		ScriptableObject.putProperty(scope, "game", Context.javaToJS(getScene().game, scope));
		ScriptableObject.putProperty(scope, "scene", Context.javaToJS(getScene(), scope));
		ScriptableObject.putProperty(scope, "loader", Context.javaToJS(getScene().loader, scope));
		ScriptableObject.putProperty(scope, "builder", Context.javaToJS(getScene().builder, scope));
		ScriptableObject.putProperty(scope, "load", Context.javaToJS(getScene().loader, scope));
		ScriptableObject.putProperty(scope, "add", Context.javaToJS(getScene().builder, scope));
		cx.evaluateString(scope, file.readString(), file.name(), 1, null);

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
	}

	public FileHandle getScriptPath () {
		FileHandle f = Gdx.files.internal("js");
		if (filename == null) {
			filename = getScene().getClass().getSimpleName() + ".js";
		}
		return f.child(filename);
	}

	Function fOnCreate, fOnUpdate, fOnPreload, fOnLoaded, fOnDestroy, fOnActorRemoved, fOnActorAdded, fOnResize, fOnLoading,
		fOnResume, fOnPause, fOnPostDraw, fOnPreDraw;

	private Function js (String name) {
		Object fObj = scope.get(name, scope);
		if (fObj instanceof Function) {
			return (Function)fObj;
		}
		return null;
	}

	@Override
	public void onCreate () {
		if (fOnCreate != null) {
			Object arg[] = {};
			fOnCreate.call(cx, scope, scope, arg);
		}
	}

	@Override
	public void onUpdate (float delta) {
		if (fOnUpdate != null) {
			Object arg[] = {delta};
			fOnUpdate.call(cx, scope, scope, arg);
		}
	}

	@Override
	public void onDestroy () {
		if (fOnDestroy != null) {
			Object arg[] = {};
			fOnDestroy.call(cx, scope, scope, arg);
		}
		if (cx != null) {
			Context.exit();
		}
	}

	@Override
	public void onPreload () {
		if (fOnPreload != null) {
			Object arg[] = {};
			fOnPreload.call(cx, scope, scope, arg);
		}
	}

	@Override
	public void onLoaded () {
		if (fOnLoaded != null) {
			Object arg[] = {};
			fOnLoaded.call(cx, scope, scope, arg);
		}

	}

	@Override
	public void onResume () {
		if (fOnResume != null) {
			Object arg[] = {};
			fOnResume.call(cx, scope, scope, arg);
		}
	}

	@Override
	public void onPause () {
		if (fOnPause != null) {
			Object arg[] = {};
			fOnPause.call(cx, scope, scope, arg);
		}

	}

	@Override
	public void onLoading (float delta, float progress) {
		if (fOnLoading != null) {
			Object arg[] = {delta, progress};
			fOnLoading.call(cx, scope, scope, arg);
		}

	}

	@Override
	public void onResize () {
		if (fOnResize != null) {
			Object arg[] = {};
			fOnResize.call(cx, scope, scope, arg);
		}
	}

	@Override
	public void onPreDraw (float delta) {
		if (fOnPreDraw != null) {
			Object arg[] = {delta};
			fOnPreDraw.call(cx, scope, scope, arg);
		}

	}

	@Override
	public void onPostDraw (float delta) {
		if (fOnPostDraw != null) {
			Object arg[] = {delta};
			fOnPostDraw.call(cx, scope, scope, arg);
		}
	}

	@Override
	public void onActorRemoved (Actor a) {
		if (fOnActorRemoved != null) {
			Object arg[] = {a};
			fOnActorRemoved.call(cx, scope, scope, arg);
		}
	}

	@Override
	public void onActorAdded (Actor a) {
		if (fOnActorAdded != null) {
			Object arg[] = {a};
			fOnActorAdded.call(cx, scope, scope, arg);
		}
	}

}
