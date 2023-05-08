package com.mygdx.game.scenes.tutorials;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.floorGuns.FloorRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class tutorial7 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tutorial4));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(440.0f,16.0f,"","","tutorial5"));

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");

        add(poolGet(Player.class).init(56.0f,0.0f));

        add(poolGet(Elf.class).init(344.0f , 0.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(Elf.class).init(376.0f , 0.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(FloorRevolver.class).init(88.0f,-12.0f));






        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(20,0)));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 32.4),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));


    }

    public void playBackAndChangeScene(String nextScene){
        SceneHandler.setCurrentScene(nextScene);
    }

}