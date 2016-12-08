
package net.devtrainer.foogl.actor;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import net.devtrainer.foogl.Scene;

public class Group extends Actor {
	Array<Actor> children = new Array<Actor>();

	public Group (Scene scene) {
		super(scene);
	}
    public void clear(){
    	children.clear();
    }
	public Group add (Actor a) {
		a.setParent(this);
		//System.out.println("add "+a);
		if (children.size > 0) {
			for (int i = children.size - 1; i >= 0; i--) {
				if (a.getZ() >= children.get(i).getZ()) {
					children.insert(i, a);
					return this;
				}
			}
			children.insert(0, a);
		} else {
			children.add(a);
		}
		scene.onActorAdded(a);
		return this;
	}

	public void updateZorder () {
		children.sort(new Comparator<Actor>() {
			@Override
			public int compare (Actor o1, Actor o2) {
				int z1=o1.getZ()*10000+o1.getId(), z2=o2.getZ()*10000+o2.getId();

				if(z1==z2)	return 0;
				if(z1>z2) return 1;
				else return -1;
		}});
	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
       Rectangle vbox = getScene().getScreenBound();
       vbox.x-=20;
       vbox.y-=20;
       vbox.width+=100; vbox.height+=100;
        //TextureRegion box = game.builder.texture("box");
	   parentAlpha*=getAlpha();
       //if(box!=null){
         //batch.draw(box,vbox.x+1,vbox.y+1, vbox.width-2, vbox.height-2);
       //}
       for (int i = 0; i < children.size; i++) {
 			Actor a=children.get(i);
 			if(vbox.overlaps(a.getBound())){ 	 			
 				a.draw(batch, parentAlpha);
 			}
       }
	}

	@Override
	public void update (float delta) {
		//System.out.println("size "+children);
		for (int i = 0; i < children.size; i++) {
			Actor a = children.get(i);
			a.act(delta);
			if (a.killed) {
				children.removeIndex(i);
				scene.onActorRemoved(a);
				i--;
			}
		}
	}

	public boolean hasChildren () {		
		return children.size>0;
	}

	public void drawdebug (ShapeRenderer shape) {
		
      for (int i = 0; i < children.size; i++) {
			Actor a=children.get(i);
			a.drawdebug(shape);
      }
	}

}
