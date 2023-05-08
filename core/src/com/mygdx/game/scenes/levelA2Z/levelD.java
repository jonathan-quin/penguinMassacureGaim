package com.mygdx.game.scenes.levelA2Z;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.floorGuns.FloorRevolver;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class levelD extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(328.0f,16.0f,"","","NEXTSCENE"));


        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(Player.class).init(88.0f,-272.0f));
        add(poolGet(Elf.class).init(200.0f , -272.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(248.0f , -16.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(376.0f , -16.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(344.0f + 64f + 32f , -368.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(296.0f , -480.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(Elf.class).init(216.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(FloorRevolver.class).init(328.0f - 64f,-492.0f));


        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelD));



        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(5,0),ObjectPool.getGarbage(Vector2.class).set(1,0),0.008));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 34),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        add(poolGet(TimeVortexVertical.class).init(0,-2900,ObjectPool.getGarbage(Vector2.class).set(0,10),ObjectPool.getGarbage(Vector2.class).set(0,60),0.004));

    }

}