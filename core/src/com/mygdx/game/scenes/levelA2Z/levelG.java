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

public class levelG extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

//        add(ObjectPool.get(Node.class).init(0,0));
//        last().addChild(poolGet(EndLevelGate.class).init(440.0f,16.0f,"","","NEXTSCENE"));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(1240.0f,-16.0f,"","lobby3","lobby"));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(Player.class).init(40.0f,-32.0f));
        add(poolGet(Elf.class).init(152.0f , -32.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(FloorRevolver.class).init(88.0f,-44.0f));
        add(poolGet(ElfVip.class).init(680.0f , -16.0f ,ObjectPool.get(ElfShotgun.class),false));
        add(poolGet(ElfVip.class).init(392.0f , -64.0f ,ObjectPool.get(ElfRevolver.class),false));
        add(poolGet(ElfVip.class).init(952.0f , -48.0f ,ObjectPool.get(ElfRevolver.class),false));



        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelG));



        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(10,0),ObjectPool.getGarbage(Vector2.class).set(60,0),0.008));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 83),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        add(poolGet(TimeVortexVertical.class).init(0,-2300,ObjectPool.getGarbage(Vector2.class).set(0,1)));
        //add(poolGet(TimeVortexVertical.class).init(0,2700,ObjectPool.getGarbage(Vector2.class).set(0,-10),ObjectPool.getGarbage(Vector2.class).set(0,-30),0.008));


    }

}