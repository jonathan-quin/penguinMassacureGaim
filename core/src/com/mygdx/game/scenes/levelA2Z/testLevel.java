package com.mygdx.game.scenes.levelA2Z;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class testLevel extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(960.0f , -96.0f,"","lobby2","lobby"));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.realLevel1));

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");


        add(poolGet(Player.class).init(48.0f , -112.0f));
        ((Player) last()).takeGun(PenguinRevolver.class);



    }

}