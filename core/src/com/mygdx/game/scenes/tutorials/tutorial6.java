package com.mygdx.game.scenes.tutorials;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class tutorial6 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,-80));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tutorial6));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(488.0f,0.0f,"","","lobby"));

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");


        add(poolGet(Player.class).init(136.0f,-336.0f));
        ((Player) last()).takeGun(PenguinRevolver.class);
        add(poolGet(Elf.class).init(152.0f , -432.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(ElfVip.class).init(312.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),true));





        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(10,0)));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 33.5),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        add(poolGet(TimeVortexVertical.class).init(0,-2700,ObjectPool.getGarbage(Vector2.class).set(0,10),ObjectPool.getGarbage(Vector2.class).set(0,100),0.008));
        add(poolGet(TimeVortexVertical.class).init(0,2700,ObjectPool.getGarbage(Vector2.class).set(0,-10),ObjectPool.getGarbage(Vector2.class).set(0,-30),0.008));


    }

    public void playBackAndChangeScene(String nextScene){
        SceneHandler.setCurrentScene(nextScene);
    }

}