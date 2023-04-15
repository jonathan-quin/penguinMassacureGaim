package com.mygdx.game.scenes;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.SimpleIcePlatform;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;

public class TestScene2 extends Root {


    public void open(){
        rootNode = poolGet(Node.class);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(Player.class).init(220,300));


        rootNode.addChild( ObjectPool.get(SimpleIcePlatform.class).init(110,100));


        //rootNode.addChild();

        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96,100));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96,100));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96-96,100));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96-82,120));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96-96-96,100));

        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100+96+18,100+32));
    }


}