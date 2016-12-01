
package net.devtrainer.foogl.actor;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import net.devtrainer.foogl.Scene;

public class Group extends Actor {
	Array<Actor> children = new Array<Actor>();

	public Group (Scene scene) {
		super(scene);
	}

	public Group add (Actor a) {
		a.setParent(this);
		System.out.println("add "+a);
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
		return this;
	}

	public void updateZorder () {
		children.sort(new Comparator<Actor>() {
			@Override
			public int compare (Actor o1, Actor o2) {
				int z1=o1.getZ(), z2=o2.getZ();
				if(z1==z2)	return 0;
				if(z1>z2) return 1;
				else return -1;
		}});
	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
       Rectangle vbox = getScene().getScreenBound(); 
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
				i--;
			}
		}
	}

	public boolean hasChildren () {		
		return children.size>0;
	}

}
