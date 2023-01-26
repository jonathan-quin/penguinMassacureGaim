package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.nodes.*;

public class simpleIcePlatform extends staticNode {

    public simpleIcePlatform(root myRoot, float x, float y) {

        super(myRoot, x, y);

        Texture penguinTX = new Texture("simplePlatform.png");
        addChild(new textureEntity(penguinTX,0,0,96,48));

        addChild(new collisionShape(96,16,0,0));

    }

}
