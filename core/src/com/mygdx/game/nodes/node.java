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

    node parent = this;

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

    public node getParent(){
        return parent;
    }

    public void updateCascade(){

        update();

        updateParentPos();
        for (node child: children){
            child.updateCascade();
        }

    }

    public void update(){

    }

    public void updateParentPos(){
        updateGlobalPosition();
        for (node child:children) {
            child.parentPosition.set(globalPosition);
            child.parent = this;
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


    //returns true if the child was found
    public boolean removeChild(node child){

        if (children.contains(child,true)){
            children.removeIndex(children.indexOf(child,true));
            return true;
        }

        return false;

    }

    public void free(){
        getParent().removeChild(this);
        for (node child : children){
            child.free();
        }
    }

    public void updateGlobalPosition() {

        this.globalPosition.set( parentPosition.x + position.x,parentPosition.y+position.y  );

    }

}
