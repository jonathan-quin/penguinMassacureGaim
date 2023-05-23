package com.mygdx.game.helpers.constants;
import static com.mygdx.game.helpers.constants.Globals.camera;
import static com.mygdx.game.helpers.constants.Globals.sceneJustChanged;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.nodes.GroupHandler;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;
import com.mygdx.game.scenes.TestScene;
import com.mygdx.game.scenes.levelA2Z.levelA;
import com.mygdx.game.scenes.levelA2Z.levelB;
import com.mygdx.game.scenes.levelA2Z.levelC;
import com.mygdx.game.scenes.levelA2Z.levelD;
import com.mygdx.game.scenes.levelA2Z.levelE;
import com.mygdx.game.scenes.levelA2Z.levelF;
import com.mygdx.game.scenes.levelA2Z.levelG;
import com.mygdx.game.scenes.levelA2Z.levelH;
import com.mygdx.game.scenes.levelA2Z.levelI;
import com.mygdx.game.scenes.levelA2Z.levelJ;
import com.mygdx.game.scenes.lobbyAndTitle.lobby;
import com.mygdx.game.scenes.lobbyAndTitle.titleScreen;
import com.mygdx.game.scenes.tutorials.tutorial1;
import com.mygdx.game.scenes.tutorials.tutorial2;
import com.mygdx.game.scenes.tutorials.tutorial3;
import com.mygdx.game.scenes.tutorials.tutorial4;
import com.mygdx.game.scenes.tutorials.tutorial5;
import com.mygdx.game.scenes.tutorials.tutorial6;
import com.mygdx.game.scenes.tutorials.tutorial7;
import com.mygdx.game.scenes.tutorials.tutorial8;

public class SceneHandler {

    public static HashMap<String, Root> scenes;

    static SpriteBatch batch;

    private static String currentSceneName;

    private static Root currentScene;

    private static boolean sceneChangeThisFrame = false;
    public static void ready(){
        scenes = new HashMap<>();



        scenes.put("TestScene", new TestScene());

        /*
        scenes.put("TestScene2", new TestScene2());
        scenes.put("TimeRewindTest", new TimeRewindScene());



        scenes.put("Level2", new tempLevel2());
        scenes.put("Level3", new tempLevel3());
        */

        scenes.put("lobby", new lobby());
        scenes.put("titleScreen", new titleScreen());

        scenes.put("levelA", new levelA());
        scenes.put("levelB", new levelB());
        scenes.put("levelC", new levelC());
        scenes.put("levelD", new levelD());
        scenes.put("levelE", new levelE());
        scenes.put("levelF", new levelF());
        scenes.put("levelG", new levelG());
        scenes.put("levelH", new levelH());
        scenes.put("levelI", new levelI());
        scenes.put("levelJ", new levelJ());


        scenes.put("tutorial1", new tutorial1());
        scenes.put("tutorial2", new tutorial2());
        scenes.put("tutorial3", new tutorial3());
        scenes.put("tutorial4", new tutorial4());
        scenes.put("tutorial5", new tutorial5());
        scenes.put("tutorial6", new tutorial6());
        scenes.put("tutorial7", new tutorial7());
        scenes.put("tutorial8", new tutorial8());




        //scenes.put("testscene4", new tempLevel1());


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

        for (Node n : tempScene.groups.getNodesInGroup(GroupHandler.RENDERONTOP)){
            n.renderCascade(batch);
        }

        batch.end();

        Globals.globalShape.begin(ShapeRenderer.ShapeType.Filled);

        if (true || Globals.showCollision){
            tempScene.debug();
        }

        Globals.globalShape.end();


        ObjectPool.takeOutTrash();


       //ObjectPool.printTotal();

        for (Node n : tempScene.groups.getNodesInGroup(GroupHandler.QUEUEFREE)){
            n.free();
        }

        tempScene.groups.clearGroup(GroupHandler.QUEUEFREE);
        
        if (sceneChangeThisFrame){
//          ObjectPool.printTotal();
            ObjectPool.printObjectBreakdownInUse();
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
