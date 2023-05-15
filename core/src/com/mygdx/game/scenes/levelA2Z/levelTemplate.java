package com.mygdx.game.scenes.levelA2Z;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.ParalaxBackground;
import com.mygdx.game.entities.TimeVortex;
import com.mygdx.game.entities.levelCreator;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class levelTemplate extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

//        add(ObjectPool.get(Node.class).init(0,0));
//        last().addChild(poolGet(EndLevelGate.class).init(440.0f,16.0f,"","","NEXTSCENE"));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(ObjectPool.get(levelCreator.class).init());


        add(poolGet(TileMapProcessor.class).init(TileMapHolder.levelA));



        add(poolGet(TimeVortex.class).init(-1956,0,ObjectPool.getGarbage(Vector2.class).set(10,0),ObjectPool.getGarbage(Vector2.class).set(60,0),0.008));
        add(poolGet(TimeVortex.class).init((float) (1948 + 16 * 59),0,ObjectPool.getGarbage(Vector2.class).set(-1,0)));

        //add(poolGet(TimeVortexVertical.class).init(0,-2700,ObjectPool.getGarbage(Vector2.class).set(0,10),ObjectPool.getGarbage(Vector2.class).set(0,100),0.008));
        //add(poolGet(TimeVortexVertical.class).init(0,2700,ObjectPool.getGarbage(Vector2.class).set(0,-10),ObjectPool.getGarbage(Vector2.class).set(0,-30),0.008));


    }

}