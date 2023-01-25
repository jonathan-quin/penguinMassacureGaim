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

        staticNode platform = new staticNode(this,100,400);
        platform.addChild(new collisionShape(128,128));
        rootNode.addChild(platform);

        staticNode platform2 = new staticNode(this,430,400);
        platform2.addChild(new collisionShape(128,128));
        rootNode.addChild(platform2);

        staticNode platform3 = new staticNode(this,400,300);
        platform3.addChild(new collisionShape(128,128));
        rootNode.addChild(platform3);




    }

}
