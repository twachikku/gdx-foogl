package com.wt.gdxgame;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LoadingScene extends GameScene {
    Label lbl;

	@Override
	public void onInit() {
		lbl = new Label("Loading ",getSkin());
        addActor(lbl,340,200);
	}

	@Override
	public void onEnter() {

	}

	@Override
	public void onUpdate(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {

	}

}
