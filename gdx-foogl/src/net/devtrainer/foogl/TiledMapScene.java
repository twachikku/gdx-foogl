package net.devtrainer.foogl;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

abstract public class TiledMapScene extends Scene {
   //private Array<int> backgrounds;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	private int[] backgrounds;
	private int[] forgrounds;

	public TiledMapScene () {
		
	}
	
	public TiledMap getMap () {
		return map;
	}

	public void setMap (TiledMap map) {
		this.map = map;
		MapLayers layers = this.map.getLayers();
		ArrayList<Integer> bg=new ArrayList();
		ArrayList<Integer> fg=new ArrayList();
		boolean found=false;
		float maxw=0,maxh=0;
		for(int i=0;i<layers.getCount();i++){
			MapLayer layer=layers.get(i);			
			if (layer instanceof TiledMapTileLayer || layer instanceof TiledMapImageLayer) {
				if(layer instanceof TiledMapTileLayer){
					TiledMapTileLayer t=(TiledMapTileLayer) layer;
					float w = t.getWidth()*t.getTileWidth();
					float h = t.getHeight()*t.getTileHeight();
					if(w>maxw)maxw=w;
					if(h>maxh)maxh=h;
				}
				if(layer.getName().startsWith("fg")){
					found=true;
				}
				if(!found){
					bg.add(i);
				}else{
					fg.add(i);
				}	
			}			
		}
		backgrounds = new int[bg.size()];
		for(int i=0;i<bg.size();i++){
			backgrounds[i]=bg.get(i);
		}
		forgrounds  = new int[fg.size()];
		for(int i=0;i<fg.size();i++){
			forgrounds[i]=fg.get(i);
		}		
		renderer = new OrthogonalTiledMapRenderer(map,getBatch());	
		getViewport().setWorldSize(maxw,maxh);
	}

	@Override
	public void onPreDraw (float delta) {
		if(map!=null && renderer!=null){
		  renderer.setView((OrthographicCamera)getCamera());
		  renderer.render(backgrounds);
		}
	}

	@Override
	public void onPostDraw (float delta) {
		if(map!=null && renderer!=null){
    		renderer.render(forgrounds);
		}
	}

	public void onRenderObject(MapObject object){
		
	}	
	
	@Override
	public void onDestroy () {
		if(map!=null){
			map.dispose();
			renderer.dispose();
		}
	}
   
   class TiledMapRenderer extends OrthogonalTiledMapRenderer{

		public TiledMapRenderer (TiledMap map, Batch batch) {
			super(map, batch);			
		}
		@Override
		public void renderObject(MapObject object) {
          onRenderObject(object);
		}	   	
   }
}
