package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.nodes.*;

public class SimpleIcePlatform extends StaticNode {

    public SimpleIcePlatform(Root myRoot, float x, float y) {

        super(myRoot, x, y);

        Texture penguinTX = new Texture("simplePlatform.png");
        addChild(new TextureEntity(penguinTX,0,0,96,48));

        addChild(new CollisionShape(96/2,16/2,0,0));
        //addChild(new collisionShape(96/2,16/2,8,4));
        //addChild(new collisionShape(96/2,160/2,-80,4));

    }

}
