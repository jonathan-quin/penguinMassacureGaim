package com.mygdx.game.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.*;
import com.mygdx.game.helpers.GroupHandler;
import com.mygdx.game.nodes.*;

public class TestScene extends Root {

    private Texture penguinTX;
    public TestScene(){
        rootNode = new Node();
        rootNode.myRoot = this;
        groups = new GroupHandler();


        rootNode.addChild( new Player(this,220,400) );

        rootNode.addChild(new Node());

        rootNode.getNewestChild().setName("bulletHolder");

        rootNode.addChild(new SimpleIcePlatform(this,110,100));


        //rootNode.addChild();

        rootNode.addChild(new SimpleIcePlatform(this,100-96,100));
        rootNode.addChild(new SimpleIcePlatform(this,100-96-96,100));
        rootNode.addChild(new SimpleIcePlatform(this,100-96-96-96,100));
        rootNode.addChild(new SimpleIcePlatform(this,100-96-96-82,120));
        rootNode.addChild(new SimpleIcePlatform(this,100-96-96-96-96,100));

        rootNode.addChild(new SimpleIcePlatform(this,100+96+18,100+32));


    }

}
