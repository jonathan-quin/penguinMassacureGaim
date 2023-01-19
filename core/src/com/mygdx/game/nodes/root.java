package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class root {

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
