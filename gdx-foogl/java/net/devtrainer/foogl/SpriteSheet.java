
package net.devtrainer.foogl;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class SpriteSheet {
	private int frameWidth = 32;
	private int frameHeight = 32;
	private int margin = 0;
	private int spacing = 0;
	private int frameMax = -1;
	private TextureRegion srcRegion;
	private String assetKey;
	private boolean dirty = true;
	private Array<TextureRegion> frames = new Array<TextureRegion>();

	public SpriteSheet (String key, int frameWidth2, int frameHeight2, int margin2, int spacing2, int frameMax2) {
		this.assetKey = key;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.margin = margin;
		this.spacing = spacing;
		this.frameMax = frameMax;
	}

	public SpriteSheet (TextureRegion srcRegion) {
		super();
	}

	public SpriteSheet (TextureRegion srcRegion, int frameWidth, int frameHeight) {
		this.srcRegion = srcRegion;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}

	public SpriteSheet (TextureRegion srcRegion, int frameWidth, int frameHeight, int margin, int spacing, int frameMax) {
		super();
		this.srcRegion = srcRegion;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.margin = margin;
		this.spacing = spacing;
		this.frameMax = frameMax;
	}

	public TextureRegion getFrame (int index) {
		if (this.frameMax > 0 && index >= this.frameMax) return null;
		Array<TextureRegion> frames = getFrames();
		if (index < 0 || index >= frames.size) return null;
		TextureRegion r = frames.get(index);
		// System.out.println(r.getRegionWidth()+","+r.getRegionHeight());
		return r;
	}

	public int getFrameHeight () {
		return frameHeight;
	}

	public int getFrameMax () {
		return frameMax;
	}

	public Array<TextureRegion> getFrames () {
		if (!this.dirty && this.frames != null) {
			return this.frames;
		}
		if (srcRegion == null) {
			srcRegion = Game.builder.texture(assetKey);
			// srcRegion.flip(false,true);
		}

		int x, y;
		int w = this.frameWidth + this.spacing;
		int h = this.frameHeight + this.spacing;
		int n = 0;
		for (y = margin; y < srcRegion.getRegionHeight(); y += h) {
			for (x = margin; x < srcRegion.getRegionWidth(); x += w) {
				frames.add(new TextureRegion(srcRegion, x, y, this.frameWidth, this.frameHeight));
				if (this.frameMax > 0 && n >= this.frameMax) break;
			}
		}
		// srcRegion.flip(false,true);
		this.dirty = false;
		return frames;
	}

	public Array<TextureRegion> getFrames (int[] indexs) {
		this.frames = getFrames();
		Array<TextureRegion> list = new Array<TextureRegion>();
		for (int i : indexs) {
			if (i < frames.size) {
			  list.add(frames.get(i));
			}
		}
		return list;
	}

	public int getFrameWidth () {
		return frameWidth;
	}

	public int getMargin () {
		return margin;
	}

	public Array<TextureRegion> getRang (int start, int to) {
		Array<TextureRegion> list = new Array<TextureRegion>();
		frames = getFrames();
		for (int i = start; i <= to; i++) {
			if (i < frames.size) {
				list.add(frames.get(i));
			}
		}
		return list;
	}

	public int getSpacing () {
		return spacing;
	}

	public TextureRegion getSrcRegion () {
		return srcRegion;
	}

	public void setByRowsCols (int rows, int cols) {
		if (rows <= 0) rows = 1;
		if (cols <= 0) cols = 1;

		int w = srcRegion.getRegionWidth();
		int h = srcRegion.getRegionHeight();
		w -= margin;
		h -= margin;
		this.frameWidth = (w / cols) - spacing;
		this.frameHeight = (w / rows) - spacing;
		this.dirty = true;
	}

	public void setFrameHeight (int frameHeight) {
		if (frameWidth < 2) frameWidth = 2;
		this.frameHeight = frameHeight;
		this.dirty = true;
	}

	public void setFrameMax (int frameMax) {
		this.frameMax = frameMax;
		this.dirty = true;
	}

	public void setFrameWidth (int frameWidth) {
		if (frameWidth < 2) frameWidth = 2;
		this.frameWidth = frameWidth;
		this.dirty = true;
	}

	public void setMargin (int margin) {
		this.margin = margin;
		this.dirty = true;
	}

	public void setSpacing (int spacing) {
		this.spacing = spacing;
		this.dirty = true;
	}

	public void setSrcRegion (TextureRegion srcRegion) {
		this.srcRegion = srcRegion;
		this.dirty = true;
	}

}
