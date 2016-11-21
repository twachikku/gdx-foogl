package com.wt.gdxgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BackgroundActor extends Actor {
	private TextureRegion image=null;

	public BackgroundActor(TextureRegion image) {
		super();
		this.image = image;
		setSize(image.getRegionWidth(), image.getRegionHeight());
		setZIndex(0);
		
	}
  
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Stage st=getStage();
		if(st==null) return;
		
		float x = 0;
		float y = 0;
		for(y=0;y<st.getHeight();y+=getHeight()){
			for(x=0;x<st.getWidth();x+=getWidth()){
			   batch.draw(image, x, y, getWidth(), getHeight());
			}			
		}			
	}
}
