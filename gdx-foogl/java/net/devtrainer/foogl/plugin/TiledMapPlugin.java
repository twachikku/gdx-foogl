package net.devtrainer.foogl.plugin;

import java.util.ArrayList;
import java.util.function.Function;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import net.devtrainer.foogl.actor.Actor;

public class TiledMapPlugin extends ScenePlugin {
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	private int[] backgrounds;
	private int[] forgrounds;
	private TiledMapCallBack callback;

	public TiledMapPlugin () {
	
	}
	public TiledMap getMap () {
		return map;
	}

	public OrthogonalTiledMapRenderer getRenderer () {
		return renderer;
	}
	public void setRenderer (OrthogonalTiledMapRenderer renderer) {		
		this.renderer = renderer;
		this.renderer.setMap(map);
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
		if(renderer==null){
		   renderer = new TiledMapRenderer(map,getScene().getBatch());
		}else{
			renderer.setMap(map);
		}
		getScene().getViewport().setWorldSize(maxw,maxh);
		onResize();
	}
	@Override
	public void onCreate () {
	}

	@Override
	public void onPreload () {
	}

	@Override
	public void onLoaded () {
	}

	@Override
	public void onUpdate (float delta) {
	}

	@Override
	public void onPreDraw (float delta) {
		if(map!=null && renderer!=null){
		  renderer.setView((OrthographicCamera)getScene().getCamera());
		  renderer.render(backgrounds);
		}
	}

	@Override
	public void onPostDraw (float delta) {
		if(map!=null && renderer!=null){
    		renderer.render(forgrounds);
		}
	}
	
	@Override
	public void onDestroy () {
		if(map!=null){
			map.dispose();
			renderer.dispose();
		}
	}
	public TiledMapCallBack getCallback () {
		return callback;
	}
	public void setCallback (TiledMapCallBack callback) {
		this.callback = callback;
	}
	
	public void collide(Actor actor,String layer, TiledMapCollideCallBack callback){
	  // TODO - collision detection	
	}
	
	class TiledMapRenderer extends OrthogonalTiledMapRenderer{

		public TiledMapRenderer (TiledMap map, Batch batch) {
			super(map, batch);			
		}
		@Override
		public void renderObject(MapObject object) {
			if(callback!=null) callback.renderObject(object);
		}	   	
   }
}
