package com.mygdx.game.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.*;
import com.mygdx.game.nodes.*;

public class testScene extends root {

    private Texture penguinTX;
    public testScene(){
        rootNode = new node();


       // penguinTX = new Texture("badlogic.jpg");
       // rootNode.addChild(new textureEntity(penguinTX,0,0,64,64));

        rootNode.addChild( new player(this,220,400) );


        rootNode.addChild(new simpleIcePlatform(this,110,100));


        //rootNode.addChild();

        rootNode.addChild(new simpleIcePlatform(this,100-96,100));
        rootNode.addChild(new simpleIcePlatform(this,100-96-96,100));
        rootNode.addChild(new simpleIcePlatform(this,100-96-96-96,100));
        rootNode.addChild(new simpleIcePlatform(this,100-96-96-82,120));
        rootNode.addChild(new simpleIcePlatform(this,100-96-96-96-96,100));

        rootNode.addChild(new simpleIcePlatform(this,100+96+18,100+32));


    }

}
