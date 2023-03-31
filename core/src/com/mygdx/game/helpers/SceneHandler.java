package com.mygdx.game.helpers;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;
import com.mygdx.game.scenes.TestScene;
import com.mygdx.game.scenes.TestScene2;

import java.util.HashMap;

import static com.mygdx.game.helpers.Globals.camera;

public class SceneHandler {

    public static HashMap<String, Root> scenes;

    static SpriteBatch batch;

    private static String currentSceneName;

    private static Root currentScene;
    public static void ready(){
        scenes = new HashMap<>();

        scenes.put("TestScene", new TestScene());
        scenes.put("TestScene2", new TestScene2());


        batch = new SpriteBatch();

    }


    public static void setCurrentScene(String scene){

        if (currentScene != null) currentScene.close();

        currentScene = scenes.get(scene);

        currentSceneName = scene;

        currentScene.open();

        System.out.println(scene);

    }

    public static String getCurrentScene(){
        return currentSceneName;
    }

    public static void update(){

        Root tempScene = currentScene;

        tempScene.update();


        camera.position.set(Globals.cameraOffset,camera.position.z);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        tempScene.render(batch);


        batch.end();

        Globals.globalShape.begin(ShapeRenderer.ShapeType.Filled);
        if (Globals.showCollision){
            tempScene.debug();
        }
        Globals.globalShape.end();


        ObjectPool.takeOutTrash();


        ObjectPool.printTotal();


        for (Node n : tempScene.groups.getNodesInGroup(GroupHandler.QUEUEFREE)){
            n.free();
        }

        tempScene.groups.clearGroup(GroupHandler.QUEUEFREE);


    }





}
