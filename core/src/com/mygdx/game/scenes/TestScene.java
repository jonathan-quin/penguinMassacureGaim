package com.mygdx.game.scenes;

import com.mygdx.game.entities.*;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.helpers.utilities.TileMapInfo;
import com.mygdx.game.nodes.*;

public class TestScene extends Root {



    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");


        add(poolGet(Player.class).init(110,300));



        rootNode.addChild( ObjectPool.get(SimpleIcePlatform.class).init(110,100));
        rootNode.addChild( ObjectPool.get(SimpleIcePlatform.class).init(110+96,100));


        //add(poolGet(TileMapProcessor.class).init(TileMapHolder.testInfo) );

        //rootNode.addChild();

      /*  rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96,100));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96,120));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96-96,120));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96-82,130));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100-96-96-96-96,140));
        rootNode.addChild(ObjectPool.get(SimpleIcePlatform.class).init(100+96,100+32));*/



    }


}
