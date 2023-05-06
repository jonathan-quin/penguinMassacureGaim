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

public class tempLevel3 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");

        add(poolGet(EndLevelGate.class).init(712,-893,"","","Lobby"));


        add(poolGet(Player.class).init(115,-60));
        ((Player) last()).takeGun(PenguinRevolver.class);

        add(poolGet(Elf.class).init(304.0f , -176.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(416.0f , -160.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(624.0f , -256.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(688.0f , -384.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(576.0f , -432.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(368.0f , -544.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(192.0f , -640.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(96.0f , -752.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(208.0f , -800.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(368.0f , -896.0f ,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(560.0f , -928.0f ,ObjectPool.get(ElfRevolver.class)));


        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tempLevel3));

        add(poolGet(TimeVortex.class).init(-2400,0,ObjectPool.getGarbage(Vector2.class).set(120,0)));
    }

}