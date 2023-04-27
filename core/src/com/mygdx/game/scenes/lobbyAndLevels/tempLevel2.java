package com.mygdx.game.scenes.lobbyAndLevels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Elf;
import com.mygdx.game.entities.EndLevelGate;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.TimeVortex;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class tempLevel2 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");

        add(poolGet(EndLevelGate.class).init(843,-432,"","lobby-Level3","Lobby"));


        add(poolGet(Player.class).init(115,-90));
        ((Player) last()).takeGun(PenguinRevolver.class);

        add(poolGet(Elf.class).init(400.0f , -192.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(112.0f , -256.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(512.0f , -96.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(656.0f , -224.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(512.0f , -384.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(320.0f , -432.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(736.0f , -416.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(480.0f , -640.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(624.0f , -528.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(64.0f , -576.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(864.0f , -608.0f ,ObjectPool.get(ElfRevolver.class)));


        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tempLevel2));

        add(poolGet(TimeVortex.class).init(-2400,0,ObjectPool.getGarbage(Vector2.class).set(120,0)));

    }

}