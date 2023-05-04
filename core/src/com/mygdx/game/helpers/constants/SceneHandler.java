package com.mygdx.game.helpers.constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.nodes.GroupHandler;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;
import com.mygdx.game.scenes.TestScene;
import com.mygdx.game.scenes.TestScene2;
import com.mygdx.game.scenes.TimeRewindScene;
import com.mygdx.game.scenes.lobbyAndLevels.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.helpers.constants.Globals.camera;
import static com.mygdx.game.helpers.constants.Globals.sceneJustChanged;

public class SceneHandler {

    public static HashMap<String, Root> scenes;

    static SpriteBatch batch;

    private static String currentSceneName;

    private static Root currentScene;

    private static boolean sceneChangeThisFrame = false;
    public static void ready(){
        scenes = new HashMap<>();

        scenes.put("TestScene", new TestScene());
        scenes.put("TestScene2", new TestScene2());
        scenes.put("TimeRewindTest", new TimeRewindScene());

        scenes.put("Lobby", new Lobby());
        scenes.put("Level1", new Level1());
        scenes.put("Level2", new tempLevel2());
        scenes.put("Level3", new tempLevel3());

        scenes.put("testscene4", new tempLevel1());


        batch = new SpriteBatch();

    }


    public static void goToNextScene(){
        String[] tempKeys = scenes.keySet().toArray(new String[0]) ;

        ArrayList<String> keys =  (ArrayList<String>) ObjectPool.getGarbage(ArrayList.class);
        keys.clear();

        for (int i = 0; i < tempKeys.length; i++){
            keys.add(tempKeys[i]);
        }

        int newSceneIndex = keys.indexOf(currentSceneName) + 1;

        if (newSceneIndex == keys.size()){
            newSceneIndex = 0;
        }

        setCurrentScene(keys.get(newSceneIndex));

    }

    public static void setCurrentScene(String scene){


        currentScene = scenes.get(scene);

        currentSceneName = scene;

        System.out.println(scene);
        
        sceneChangeThisFrame = true;

    }

    public static String getCurrentScene(){
        return currentSceneName;
    }

    public static Root getCurrentRoot(){
        return currentScene;
    }

    public static void update(){
        
        boolean sceneWasChangedBeforeStart = Globals.sceneJustChanged;

        Root tempScene = currentScene;

        if (!tempScene.isOpen()){
            tempScene.open();
            sceneChangeThisFrame = false;
            sceneJustChanged = true;

        }

        tempScene.update();


        camera.position.set(Globals.cameraOffset,camera.position.z);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        tempScene.render(batch);


        batch.end();

        Globals.globalShape.begin(ShapeRenderer.ShapeType.Filled);

        if (true || Globals.showCollision){
            tempScene.debug();
        }

        Globals.globalShape.end();


        ObjectPool.takeOutTrash();


       // ObjectPool.printTotal();

        for (Node n : tempScene.groups.getNodesInGroup(GroupHandler.QUEUEFREE)){
            n.free();
        }

        tempScene.groups.clearGroup(GroupHandler.QUEUEFREE);
        
        if (sceneChangeThisFrame){
//            ObjectPool.printTotal();
//            ObjectPool.printObjectBreakdownInUse();
            tempScene.close();

            currentScene.init();
            currentScene.open();


            sceneJustChanged = true;
            sceneChangeThisFrame = false;
        }
        else{
            sceneJustChanged = false;
        }


    }





}
