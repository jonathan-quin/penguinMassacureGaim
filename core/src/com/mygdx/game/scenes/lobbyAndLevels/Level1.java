package com.mygdx.game.scenes.lobbyAndLevels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.helpers.constants.TileMapHolder2;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class Level1 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(EndLevelGate.class).init(960.0f , -96.0f,"","lobby-Level2","Lobby"));

        add(poolGet(Player.class).init(48.0f , -112.0f));
        ((Player) last()).takeGun(PenguinRevolver.class);

        add(poolGet(Elf.class).init(272.0f , -112.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(352.0f , -32.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(528.0f , 0.0f ,ObjectPool.get(ElfShotgun.class)));
        add(poolGet(Elf.class).init(672.0f , -112.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(ElfVip.class).init(880.0f , -112.0f ,ObjectPool.get(ElfRevolver.class)));




        add(poolGet(TileMapProcessor.class).init(TileMapHolder2.realLevel1));

        add(poolGet(TimeVortex.class).init(-1970,0,ObjectPool.getGarbage(Vector2.class).set(60,0)));
        add(poolGet(TimeVortex.class).init(3000,0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

    }

}