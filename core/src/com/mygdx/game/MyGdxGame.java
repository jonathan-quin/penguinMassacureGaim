package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.helpers.Globals;
import com.mygdx.game.helpers.ObjectPool;
import com.mygdx.game.nodes.*;
import com.mygdx.game.scenes.TestScene;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	private OrthographicCamera camera;


	Root scene1;// = new testScene();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		scene1 = new TestScene();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 600);

		Globals.camera = camera;

		Globals.globalShape.setColor(new Color(0, 0, 1, 0.5f));



	}

	@Override
	public void render () {

//		System.out.println("before");
//		ObjectPool.printTotal();

		ScreenUtils.clear(0.921f, 0.55f, 0.96f, 1);

		scene1.update();


		camera.position.set(Globals.cameraOffset,camera.position.z);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		scene1.render(batch);


		batch.end();

		Globals.globalShape.begin(ShapeRenderer.ShapeType.Filled);
		scene1.debug();
		Globals.globalShape.end();


		ObjectPool.takeOutTrash();

		//System.out.println("after");
		ObjectPool.printTotal();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
