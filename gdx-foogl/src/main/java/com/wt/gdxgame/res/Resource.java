package com.wt.gdxgame.res;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Resource {
	static FileHandle path=null;
	static public FileHandle getFile(String name){
		return getPath().child(name);
	}
	static public FileHandle getPath(){
		if(path==null) path=Gdx.files.classpath("com/wt/gdxgame/res");
		return path;
	}
}
