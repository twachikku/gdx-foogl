package com.wt.gdxgame;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LoadingScene extends GameScene {
    Label lbl;

	@Override
	public void onCreate() {
		lbl = new Label("Loading ",getSkin());
      addActor(lbl,340,200);
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

	

}
