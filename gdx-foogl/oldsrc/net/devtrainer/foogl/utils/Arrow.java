package net.devtrainer.foogl.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import net.devtrainer.foogl.assets.Resource;

public class Arrow implements Disposable{
	Texture image;
	TextureRegion r[]=new TextureRegion[4];
	boolean ownerImage=false;
	SpriteBatch batch;
	Color color;
	
	public Arrow() {
		this(Resource.getFile("arrow.png"));
		batch=new SpriteBatch();
	}
	public Arrow(FileHandle imageFile) {
		super();
		setImage(new Texture(imageFile));
		ownerImage=true;
		color=new Color(Color.RED);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color.set(color);
	}
	public Texture getImage() {
		return image;
	}
	public void setImage(Texture image) {
		if(this.image!=null && ownerImage){
			this.image.dispose();
		}
		ownerImage=false;
		this.image = image;
		if(image!=null){
			r[0]=new TextureRegion(image,0,0,16,16);   // start
			r[1]=new TextureRegion(image,16,0,16,16);  // center
			r[2]=new TextureRegion(image,0,16,16,16);  // end
			r[3]=new TextureRegion(image,16,16,16,16); // overlay
		}
	}
	@Override
	public void dispose() {
		if(image!=null && ownerImage){
			this.image.dispose();			
		}
		batch.dispose();
	}
	boolean drawover=true;
	public boolean isDrawover() {
		return drawover;
	}
	public void setDrawover(boolean drawover) {
		this.drawover = drawover;
	}
	public void draw(Camera camera, float x1,float y1,float x2,float y2){
	    float ang=MathUtils.radiansToDegrees*MathUtils.atan2(y2-y1,x2-x1);		
		batch.setProjectionMatrix(camera.combined);
		batch.setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE);
		batch.begin();
		batch.setColor(color);
		float dx=x2-x1,dy=y2-y1;
		float d=(float)Math.sqrt(dx*dx+dy*dy);
		float cx=x1,cy=y1;
		batch.draw(r[0],cx-8,cy-8,8,0,16,16,1,1,ang-90);
		
		cx=x1-8+MathUtils.cosDeg(ang)*16;
		cy=y1-8+MathUtils.sinDeg(ang)*16;
		batch.draw(r[1],cx,cy,8,0,16,d-16,1,1,ang-90);	
		batch.draw(r[2],x2-8,y2-8,8,0,16,16,1,1,ang-90);
		
		batch.setColor(Color.BLUE);
		if(drawover){
			batch.draw(r[3],cx,cy,8,0,16,d-16,1,1,ang-90);				
		}
        batch.end();
	}
	public static void main(String[] args) {
	    float a=MathUtils.atan2(100,0);
	    float d=MathUtils.radiansToDegrees*a;
	    float x=MathUtils.cosDeg(d)*16;
	    System.out.println("a="+a);
	    System.out.println("d="+d);
	    System.out.println("x="+x);
	}
	
}
