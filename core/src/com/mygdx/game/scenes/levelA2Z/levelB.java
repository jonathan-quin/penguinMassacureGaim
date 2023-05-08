package com.mygdx.game.scenes.levelA2Z;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class levelB extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(200.0f,-288.0f,"","","NEXTSCENE"));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(Player.class).init(88.0f,-16.0f));
        ((Player) last()).takeGun(PenguinRevolver.class);

        add(poolGet(Elf.class).init(312.0f , -16.0f ,ObjectPool.get(ElfMiniGun.class),true));
        add(poolGet(ElfVip.class).init(152.0f , -128.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(ElfVip.class).init(248.0f , -208.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(Elf.class).init(248.0f , -128.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(Elf.class).init(152.0f , -208.0f ,ObjectPool.get(ElfRevolver.class),true));


        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelB));



        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(10,0),ObjectPool.getGarbage(Vector2.class).set(60,0),0.008));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 32),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

    }

}