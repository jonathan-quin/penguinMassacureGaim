package com.mygdx.game.scenes.levelA2Z;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.floorGuns.FloorRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class levelE extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

//        add(ObjectPool.get(Node.class).init(0,0));
//        last().addChild(poolGet(EndLevelGate.class).init(440.0f,16.0f,"","","NEXTSCENE"));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(312.0f,-112.0f,"","lobby4","lobby"));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");


        add(poolGet(Player.class).init(312.0f,-128.0f));


        add(poolGet(ElfVip.class).init(552.0f , -208.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(Elf.class).init(56.0f , -208.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(88.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(Elf.class).init(24.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(ElfVip.class).init(520.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(584.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),true));

        add(poolGet(FloorRevolver.class).init(360.0f,-140.0f));
        add(poolGet(FloorRevolver.class).init(248.0f,-140.0f));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelE));

        add(poolGet(TimeVortex.class).init(-1956 - 32,0,ObjectPool.getGarbage(Vector2.class).set(1,0)));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 42),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        add(poolGet(TimeVortexVertical.class).init(0,-2300,ObjectPool.getGarbage(Vector2.class).set(0,2),ObjectPool.getGarbage(Vector2.class).set(0,20),0.008));
        add(poolGet(TimeVortexVertical.class).init(0,2700,ObjectPool.getGarbage(Vector2.class).set(0,-10),ObjectPool.getGarbage(Vector2.class).set(0,-30),0.008));


    }

}