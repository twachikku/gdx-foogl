package net.devtrainer.foogl;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AnimateFrames extends HashMap<String, ArrayList<TextureRegion>> {
	private ArrayList<TextureRegion> firstFrames = null;
	private TextureRegion firstFrame = null;
 
	public ArrayList<TextureRegion> getFirstFrames() {
		return firstFrames;
	}
	public TextureRegion getFirstFrame() {
		return firstFrame;
	}
	public void addFrames(String key,TextureRegion ...frame){				
		ArrayList<TextureRegion> frames = get(key);
		if (frames == null) {
			frames = new ArrayList<TextureRegion>();
			put(key, frames);
		}		
		if (firstFrames == null)   firstFrames= frames;
		for(TextureRegion r:frame){
		  if (firstFrame == null)  firstFrame = r;
		   frames.add(r);
		}
	}
	public void addFrames(String key,Array<TextureRegion> frameArray){				
		ArrayList<TextureRegion> frames = get(key);
		if (frames == null) {
			frames = new ArrayList<TextureRegion>();
			put(key, frames);
		}		
		if (firstFrames == null)   firstFrames= frames;
		for(TextureRegion r:frameArray){
		  if (firstFrame == null)  firstFrame = r;
		   frames.add(r);
		}
	}
	public void addFrames(String key,TextureAtlas images, String ...imagename){		
		for(String s:imagename){
			for(TextureRegion f : images.findRegions(s)){
				addFrames(key,f);
			}
		}
	}
	public void addFrames(String key,TextureAtlas images, String imagename,int ...index){
		for(int i:index){
			AtlasRegion r = images.findRegion(imagename, i);
			if(r!=null){
				addFrames(key,r);
			}
		}
	}
}
