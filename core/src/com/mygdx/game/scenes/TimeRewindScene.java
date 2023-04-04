package com.mygdx.game.scenes;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.SimpleIcePlatform;
import com.mygdx.game.helpers.ObjectPool;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TimeRewindRoot;

public class TimeRewindScene extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class);
        rootNode.setMyRoot(this);


        add(poolGet(Player.class).init(220,300));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        rootNode.addChild( ObjectPool.get(SimpleIcePlatform.class).init(110,100));


        //rootNode.addChild();

        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96,100));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96,120));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(80-96-96-96,120));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96-82,110));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96-96-96,140));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100+96,100+32));

    }

}
