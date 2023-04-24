package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.nodes.*;

public class MyGdxGame extends ApplicationAdapter {

	private OrthographicCamera camera;


	Root scene1;// = new testScene();
	
	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Globals.screenSize.x, Globals.screenSize.y);

		Globals.camera = camera;

		Globals.globalShape.setColor(new Color(0, 0, 1, 0.5f));

		SceneHandler.ready();

		SceneHandler.setCurrentScene("Lobby");

	}

	@Override
	public void render () {

//		System.out.println("before");
//		ObjectPool.printTotal();

		ScreenUtils.clear(0.921f, 0.55f, 0.96f, 1);



		SceneHandler.update();

		//System.out.println(ObjectPool.calculateTotal());
		//ObjectPool.printObjectBreakdownInUse();

	}
	
	@Override
	public void dispose () {


	}
}
