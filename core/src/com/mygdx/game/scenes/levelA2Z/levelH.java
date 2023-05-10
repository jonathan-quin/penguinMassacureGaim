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

public class levelH extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,-300));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(216.0f,-992.0f,"","","lobby"));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(Player.class).init(552.0f,-240.0f));
        ((Player) last()).takeGun(PenguinRevolver.class);
        add(poolGet(Elf.class).init(632.0f , -240.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(232.0f , -352.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(Elf.class).init(168.0f , -352.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(ElfVip.class).init(104.0f , -352.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(136.0f , -496.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(520.0f , -528.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(280.0f , -592.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(424.0f , -576.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(344.0f , -592.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(ElfVip.class).init(296.0f , -704.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(344.0f , -704.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(Elf.class).init(616.0f , -848.0f ,ObjectPool.get(ElfMiniGun.class),true));
        add(poolGet(Elf.class).init(440.0f , -816.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(ElfVip.class).init(552.0f , -832.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(104.0f , -784.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(200.0f , -784.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(536.0f , -1056.0f ,ObjectPool.get(ElfMiniGun.class),true));
        add(poolGet(Elf.class).init(408.0f , -1024.0f ,ObjectPool.get(ElfRevolver.class),true));
        add(poolGet(Elf.class).init(152.0f , -1024.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(ElfVip.class).init(200.0f , -1008.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(376.0f , -1024.0f ,ObjectPool.get(ElfShotgun.class),true));





        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelH));



        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(1,0)));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 45),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        add(poolGet(TimeVortexVertical.class).init(0,-3200,ObjectPool.getGarbage(Vector2.class).set(0,1)));
        add(poolGet(TimeVortexVertical.class).init(0,2700,ObjectPool.getGarbage(Vector2.class).set(0,-10),ObjectPool.getGarbage(Vector2.class).set(0,-30),0.008));


    }

}