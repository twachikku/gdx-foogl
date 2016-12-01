package net.devtrainer.foogl.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Disposable;

public class FillDrawable extends TiledDrawable implements Disposable {
    Color color;
    Texture tex;

	public FillDrawable(Color color) {
		super();
		setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if(this.color!=color && color!=null){
		  this.color = color;
		  if(tex!=null) tex.dispose();
		  Pixmap pixmap=new Pixmap(16,16,Format.RGBA4444);
		  createPattern(pixmap);
		  tex=new Texture(16,16,Format.RGBA4444);
		  tex.draw(pixmap,0,0);
		  pixmap.dispose();
		  setRegion(new TextureRegion(tex));
		}
	}

	protected void createPattern(Pixmap pixmap) {
		pixmap.setColor(color);
		pixmap.fill();
	}
	@Override
	public void dispose() {
		if(tex!=null)tex.dispose();		
	}	
}
