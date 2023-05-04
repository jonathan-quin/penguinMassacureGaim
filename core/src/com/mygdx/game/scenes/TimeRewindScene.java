package com.mygdx.game.scenes;

import com.mygdx.game.entities.Elf;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.SimpleIcePlatform;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.helpers.constants.TileMapHolder2;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class TimeRewindScene extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");


        add(poolGet(Player.class).init(658,-150));
        ((Player) last()).takeGun(PenguinRevolver.class);


        add(poolGet(Elf.class).init(200,-180,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(500,-300,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(800,-80,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(600,-700,ObjectPool.get(ElfRevolver.class)));

        add(poolGet(Elf.class).init(385,-142,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(850,-199,ObjectPool.get(ElfRevolver.class)));
        add(poolGet(Elf.class).init(710,-281,ObjectPool.get(ElfRevolver.class)));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder2.biggerTestLevel));

    }

}
