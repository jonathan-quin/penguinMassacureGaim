package com.mygdx.game.scenes.tutorials;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Elf;
import com.mygdx.game.entities.EndLevelGate;
import com.mygdx.game.entities.Hints;
import com.mygdx.game.entities.ParalaxBackground;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.TimeVortex;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class tutorial3 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,60));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tutorial3));
        last().getChild("underground", TextureEntity.class).position.add(0,64);

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(568.0f,-304.0f,"","","tutorial4"));

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");


        add(poolGet(Player.class).init(72.0f,-96.0f));
        add(poolGet(Hints.class).init(-250,120,TextureHolder.Text.R));
        add(poolGet(Hints.class).init(-250,0,TextureHolder.Text.shift));

        add(poolGet(Elf.class).init(216f , -224.0f ,ObjectPool.get(ElfMiniGun.class),false));






        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(20,0)));
        add(poolGet(TimeVortex.class).init(2828,0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));


    }

    public void playBackAndChangeScene(String nextScene){
        SceneHandler.setCurrentScene(nextScene);
    }

}