package com.mygdx.game.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.player;
import com.mygdx.game.nodes.*;

public class testScene extends root {

    private Texture penguinTX;
    public testScene(){
        rootNode = new node();


       // penguinTX = new Texture("badlogic.jpg");
       // rootNode.addChild(new textureEntity(penguinTX,0,0,64,64));

        rootNode.addChild( new player(this,200,200) );

        staticNode platform = new staticNode(this,400,400);
        platform.addChild(new collisionShape(128,128));
        rootNode.addChild(platform);

    }

}
