package net.devtrainer.foogl;

import com.badlogic.gdx.Gdx;

public class InputKey {
   int key;
	public InputKey (int key) {
		this.key=key;		
	}
   public boolean isDown(){
   	return Gdx.input.isKeyPressed(key);
   }
   public boolean isUp(){
   	return !isDown();
   }
}
