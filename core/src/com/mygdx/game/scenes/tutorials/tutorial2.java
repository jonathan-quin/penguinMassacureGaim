package com.mygdx.game.scenes.tutorials;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.floorGuns.FloorShotgun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinMiniGun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.*;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class tutorial2 extends TimeRewindRoot {



    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,-60));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tutorial2));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(1192.0f,-416.0f,"","","tutorial3"));

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");

        add(poolGet(Player.class).init(64.0f,-240.0f));

        add(poolGet(Hints.class).init(-250,120,TextureHolder.Text.R));


        add(poolGet(FloorShotgun.class).init(1416.0f,-60.0f));




        add(poolGet(TimeVortex.class).init(-1940,0,ObjectPool.getGarbage(Vector2.class).set(10,0),ObjectPool.getGarbage(Vector2.class).set(60,0),0.008));
        add(poolGet(TimeVortex.class).init(3432,0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));


    }

    public void playBackAndChangeScene(String nextScene){
        SceneHandler.setCurrentScene(nextScene);
    }

}