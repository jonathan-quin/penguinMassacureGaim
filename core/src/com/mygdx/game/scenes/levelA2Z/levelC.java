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

public class levelC extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

//        add(ObjectPool.get(Node.class).init(0,0));
//        last().addChild(poolGet(EndLevelGate.class).init(440.0f,16.0f,"","","NEXTSCENE"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(824.0f,-16.0f,"","","NEXTSCENE"));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(Player.class).init(40.0f,-80.0f));
        ((Player) last()).takeGun(PenguinRevolver.class);

        add(poolGet(Elf.class).init(136.0f , -80.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(ElfVip.class).init(280.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(Elf.class).init(232.0f , -144.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(ElfVip.class).init(392.0f , -16.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(Elf.class).init(456.0f , -16.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(568.0f , -96.0f ,ObjectPool.get(ElfMiniGun.class),true));
        add(poolGet(ElfVip.class).init(632.0f , -96.0f ,ObjectPool.get(ElfMiniGun.class),false));



        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelC));



        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(10,0),ObjectPool.getGarbage(Vector2.class).set(60,0),0.008));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 59),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        add(poolGet(TimeVortexVertical.class).init(0,-2200,ObjectPool.getGarbage(Vector2.class).set(0,10),ObjectPool.getGarbage(Vector2.class).set(0,1),0.008));
        add(poolGet(TimeVortexVertical.class).init(0,2100,ObjectPool.getGarbage(Vector2.class).set(0,-10),ObjectPool.getGarbage(Vector2.class).set(0,-1),0.008));


    }

}