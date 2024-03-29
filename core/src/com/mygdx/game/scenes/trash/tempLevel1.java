package com.mygdx.game.scenes.trash;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfCrackShotgun;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.*;

public class tempLevel1 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,-380));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(EndLevelGate.class).init(915,-386,"","lobby-Level2","lobby"));

        add(poolGet(Player.class).init(115,-260));
        ((Player) last()).takeGun(PenguinRevolver.class);

        add(poolGet(ElfVip.class).init(704.0f , -448.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(144.0f , -416.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(Elf.class).init(96.0f , -576.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(Elf.class).init(752.0f , -576.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(Elf.class).init(960.0f , -496.0f ,ObjectPool.get(ElfMiniGun.class),false));
        add(poolGet(Elf.class).init(128.0f , -416.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(Elf.class).init(400.0f , -496.0f ,ObjectPool.get(ElfRevolver.class),false));




        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tempLevel1));

        add(poolGet(TimeVortex.class).init(-2400,0,ObjectPool.getGarbage(Vector2.class).set(60,0)));


    }

}
