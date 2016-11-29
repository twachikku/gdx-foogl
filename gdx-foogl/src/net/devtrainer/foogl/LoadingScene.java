package net.devtrainer.foogl;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LoadingScene extends Scene {
    Label lbl;

	@Override
	public void onCreate() {
		lbl = new Label("Loading ",getSkin());
		lbl.setPosition(340,200);
      addActor(lbl);
	}

	@Override
	public void onPreload() {

	}

	@Override
	public void onUpdate(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onLoaded () {
		// TODO Auto-generated method stub
		
	}

	

}
