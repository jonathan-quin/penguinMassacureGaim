package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class root {

    public Array<colliderObject> colliders = new Array<colliderObject>();

    public node rootNode;

    public root(){

    }

    public void update(){
        rootNode.update();
    }
    public void render(SpriteBatch batch){
        rootNode.render(batch);
    }


}
