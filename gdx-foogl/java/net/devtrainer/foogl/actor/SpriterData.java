package net.devtrainer.foogl.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.LibGdx.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdx.LibGdxLoader;

public class SpriterData {
	LibGdxDrawer drawer;
	ShapeRenderer renderer;
	Data data;
	LibGdxLoader loader;
   String file;
   
   public SpriterData (String file) {
		super();
		this.loader = loader;
		FileHandle handle = Gdx.files.internal(file);
		this.data = new SCMLReader(handle.read()).getData();
		loader = new LibGdxLoader(data);
		loader.load(handle.file());
		renderer = new ShapeRenderer();
		drawer = new LibGdxDrawer(loader, renderer);
	}
}
