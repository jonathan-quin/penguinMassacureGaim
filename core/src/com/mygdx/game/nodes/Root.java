package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Root {

    public Array<ColliderObject> colliders = new Array<ColliderObject>();

    public Node rootNode;

    public Root(){

    }

    public void update(){
        rootNode.updateCascade();
    }
    public void render(SpriteBatch batch){
        rootNode.renderCascade(batch);
    }

    public void debug(){rootNode.debugCascade();}


}
