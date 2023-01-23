package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.nodes.*;
import com.mygdx.game.scenes.testScene;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;


	root scene1;// = new testScene();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		scene1 = new testScene();
	}

	@Override
	public void render () {

		ScreenUtils.clear(1, 0, 0, 1);

		scene1.update();

		batch.begin();

		scene1.render(batch);

		batch.end();

		scene1.debug();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
