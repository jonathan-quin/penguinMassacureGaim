package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class node {
    public Array<node> children = new Array<node>();
    Vector2 position;
    Vector2 globalPosition = new Vector2(0,0);
    Vector2 parentPosition;

    public node(){

        position = new Vector2(0,0);
        parentPosition = new Vector2(0,0);
        updateGlobalPosition();

    }
    public node(float x, float y){
        position = new Vector2(x,y);
        parentPosition = new Vector2(0,0);
        updateGlobalPosition();
    }

    public void update(){

        updateGlobalPosition();
        for (node child:children) {
            child.parentPosition.set(globalPosition);
        }
        for (node child: children){
            child.update();
        }

    }

    public void render(SpriteBatch batch){
        for (node child: children){
            child.render(batch);
        }
    }

    public void addChild(node child){
        children.add(child);
    }

    public void updateGlobalPosition() {

        this.globalPosition.set( parentPosition.x + position.x,parentPosition.y+position.y  );

    }

}
