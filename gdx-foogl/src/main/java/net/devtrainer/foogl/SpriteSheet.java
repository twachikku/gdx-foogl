package net.devtrainer.foogl;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class SpriteSheet {
	private int srcWidth=64;
	private int srcHeight=64;
	private int frameWidth=32;
	private int frameHeight=32;
	private int margin=0;
	private int spacing=0;
	private int frameMax=-1;
	private TextureRegion src;
	private boolean dirty=true;


	public SpriteSheet() {
		super();
		
	}
	public SpriteSheet(int frameWidth, int frameHeight) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}
	public SpriteSheet(int frameWidth, int frameHeight, int margin, int spacing, int frameMax) {
		super();
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.margin = margin;
		this.spacing = spacing;
		this.frameMax = frameMax;
	}
	public void setSource(TextureRegion src){
		if(this.src!=src){
		   this.src=src;
		   srcWidth = this.src.getRegionWidth();
		   srcHeight = this.src.getRegionHeight();
		}
	}
	public void setSource(Texture src){
		this.src=new TextureRegion(src);
	}
	
	public void setByRowsCols(int rows,int cols){
		
	}

	public Array<TextureRegion> getRang(int start,int to) {
		Array<TextureRegion> list=new Array<TextureRegion>();
		for(int i=start;i<=to;i++){
			
		}
		return list;
	}	
	
	public T
}
