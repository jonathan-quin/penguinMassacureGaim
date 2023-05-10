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

public class levelI extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(856.0f,-160.0f,"","lobby7","lobby"));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");


        add(poolGet(Player.class).init(136.0f,-176.0f));
        ((Player) last()).takeGun(PenguinRevolver.class);
        add(poolGet(Elf.class).init(264.0f , -176.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(536.0f , -16.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(ElfVip.class).init(200.0f , -16.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(744.0f , -16.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(ElfVip.class).init(808.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(472.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(280.0f , -16.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(456.0f , -176.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(ElfVip.class).init(568.0f , -176.0f ,ObjectPool.get(ElfShotgun.class),true));
        add(poolGet(Elf.class).init(760.0f , -176.0f ,ObjectPool.get(ElfRevolver.class),true));


        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelI));



        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(10,0),ObjectPool.getGarbage(Vector2.class).set(60,0),0.008));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 59),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        //add(poolGet(TimeVortexVertical.class).init(0,-2700,ObjectPool.getGarbage(Vector2.class).set(0,10),ObjectPool.getGarbage(Vector2.class).set(0,100),0.008));
        //add(poolGet(TimeVortexVertical.class).init(0,2700,ObjectPool.getGarbage(Vector2.class).set(0,-10),ObjectPool.getGarbage(Vector2.class).set(0,-30),0.008));


    }

}