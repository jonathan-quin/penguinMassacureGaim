package com.mygdx.game.scenes;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.SimpleIcePlatform;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class TimeRewindScene extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");


        add(poolGet(Player.class).init(0,0));


        add(poolGet(TileMapProcessor.class).init(TileMapHolder.sillyLevel));

    }

}
