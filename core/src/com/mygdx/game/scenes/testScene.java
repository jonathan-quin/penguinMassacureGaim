package com.mygdx.game.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.*;
import com.mygdx.game.nodes.*;

public class testScene extends Root {

    private Texture penguinTX;
    public testScene(){
        rootNode = new Node();


       // penguinTX = new Texture("badlogic.jpg");
       // rootNode.addChild(new textureEntity(penguinTX,0,0,64,64));

        rootNode.addChild( new Player(this,220,400) );


        //rootNode.addChild(new simpleIcePlatform(this,100,100));


        //rootNode.addChild();

        rootNode.addChild(new SimpleIcePlatform(this,100-96,100));
        rootNode.addChild(new SimpleIcePlatform(this,100-96-96,100));
        rootNode.addChild(new SimpleIcePlatform(this,100-96-96-96,100));
        rootNode.addChild(new SimpleIcePlatform(this,100-96-96-82,120));
        rootNode.addChild(new SimpleIcePlatform(this,100-96-96-96-96,100));

        rootNode.addChild(new SimpleIcePlatform(this,100+96+18,100+32));


    }

}
