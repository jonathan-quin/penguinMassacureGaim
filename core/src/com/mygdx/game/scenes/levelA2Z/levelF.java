package com.mygdx.game.scenes.levelA2Z;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.ParalaxBackground;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.TimeVortex;
import com.mygdx.game.entities.guns.floorGuns.FloorMiniGun;
import com.mygdx.game.entities.guns.floorGuns.FloorRevolver;
import com.mygdx.game.entities.guns.floorGuns.FloorShotgun;
import com.mygdx.game.entities.levelCreator;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.*;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class levelF extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

//        add(ObjectPool.get(Node.class).init(0,0));
//        last().addChild(poolGet(EndLevelGate.class).init(440.0f,16.0f,"","","NEXTSCENE"));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(280.0f,-128.0f,"","","NEXTSCENE"));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");


        add(poolGet(Player.class).init(280.0f,-16.0f));
        add(poolGet(FloorRevolver.class).init(232.0f,-28.0f));
        add(poolGet(FloorRevolver.class).init(312.0f,-28.0f));
        add(poolGet(FloorShotgun.class).init(360.0f,-28.0f));
        add(poolGet(FloorShotgun.class).init(200.0f,-28.0f));
        add(poolGet(FloorMiniGun.class).init(168.0f,-28.0f));
        add(poolGet(FloorMiniGun.class).init(392.0f,-28.0f));
        add(poolGet(ElfVip.class).init(104.0f , -144.0f ,ObjectPool.get(ElfMiniGun.class),true));
        add(poolGet(Elf.class).init(120.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(ElfVip.class).init(136.0f , -144.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(Elf.class).init(152.0f , -144.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(ElfVip.class).init(168.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(184.0f , -144.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(200.0f , -144.0f ,ObjectPool.get(ElfMiniGun.class),true));
        add(poolGet(Elf.class).init(216.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(ElfVip.class).init(232.0f , -144.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(Elf.class).init(248.0f , -144.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(ElfVip.class).init(264.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(280.0f , -144.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(296.0f , -144.0f ,ObjectPool.get(ElfMiniGun.class),true));
        add(poolGet(Elf.class).init(312.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(ElfVip.class).init(328.0f , -144.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(Elf.class).init(344.0f , -144.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(ElfVip.class).init(360.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(376.0f , -144.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(392.0f , -144.0f ,ObjectPool.get(ElfMiniGun.class),true));
        add(poolGet(Elf.class).init(408.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(ElfVip.class).init(424.0f , -144.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(Elf.class).init(440.0f , -144.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(ElfVip.class).init(456.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),true));



        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelF));



        add(poolGet(TimeVortex.class).init(-1956 - 16,0,ObjectPool.getGarbage(Vector2.class).set(1,0)));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 36.5),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        //add(poolGet(TimeVortexVertical.class).init(0,-2700,ObjectPool.getGarbage(Vector2.class).set(0,10),ObjectPool.getGarbage(Vector2.class).set(0,100),0.008));
        add(poolGet(TimeVortexVertical.class).init(0,2700,ObjectPool.getGarbage(Vector2.class).set(0,-10),ObjectPool.getGarbage(Vector2.class).set(0,-30),0.008));


    }

}