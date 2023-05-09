package com.mygdx.game.scenes.tutorials;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.floorGuns.FloorRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class tutorial5 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,60));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tutorial5));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(584.0f,-80.0f,"","","tutorial6"));

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");


        add(poolGet(Player.class).init(152.0f,-144.0f));
        add(poolGet(FloorRevolver.class).init(184.0f,-156.0f));
        add(poolGet(Elf.class).init(392.0f , -96.0f ,ObjectPool.get(ElfRevolver.class),true));
        //add(poolGet(Elf.class).init(424.0f , -96.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(ElfVip.class).init(216.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),false));





        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(20,0)));
        add(poolGet(TimeVortex.class).init(1948 + 16 * 40 ,0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));


    }

    public void playBackAndChangeScene(String nextScene){
        SceneHandler.setCurrentScene(nextScene);
    }

}