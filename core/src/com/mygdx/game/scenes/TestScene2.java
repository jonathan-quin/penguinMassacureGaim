package com.mygdx.game.scenes;

import com.mygdx.game.entities.ParalaxBackground;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.SimpleIcePlatform;
import com.mygdx.game.entities.guns.penguinGuns.PenguinMiniGun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;

public class TestScene2 extends Root {


    public void open(){
        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.paralaxMountainsAndTrees));

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(Player.class).init(220,300));
        ((Player) last()).takeGun(PenguinMiniGun.class);;

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