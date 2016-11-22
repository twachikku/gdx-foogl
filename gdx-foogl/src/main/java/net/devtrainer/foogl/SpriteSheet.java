package net.devtrainer.foogl;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class SpriteSheet { 
	Array<TextureRegion> cells=new Array<TextureRegion>();
    Array<String> codes=new Array<String>();

    public void putStrip(TextureRegion img,int width,int height){
		if(img==null) return;
		putStrip(img,0,0,img.getRegionWidth(),img.getRegionHeight(),width,height,0,0);
	}
	public void putStrip(TextureRegion img,int startx,int starty,int endx,int endy){
		putStrip(img, startx, starty, endx, endy,endx-startx,endy-endy,0,0);
	}
	public void putStrip(TextureRegion img,int startx,int starty,int endx,int endy,int width,int height){
		putStrip(img, startx, starty, endx, endy,width,height,0,0);		
	}
	public void putStrip(TextureRegion img,int startx,int starty,int endx,int endy,int width,int height,int paddingx,int paddingy){
		for(int y=starty;y<=endy;y+=height+paddingy){
			for(int x=startx;x<=endx;x+=width+paddingx){
				int x2=x+width;
				int y2=y+height;
				if(x2<=endx && y2<=endy){
					System.out.println("cell "+x+","+y);
				   TextureRegion t = new TextureRegion(img,x,y,width,height);
				   cells.add(t);
				}
			}			
		}
	}
	public Array<TextureRegion> getAll() {
		return cells;
	}	
	public Array<TextureRegion> getList(int ...index) {
		Array<TextureRegion> list=new Array<TextureRegion>();
		for(int i:index){
			list.add(cells.get(i));
		}
		return list;
	}	
	public Array<TextureRegion> getRang(int start,int to) {
		Array<TextureRegion> list=new Array<TextureRegion>();
		for(int i=start;i<=to;i++){
			list.add(cells.get(i));
		}
		return list;
	}	
}
