package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class node {
    public Array<node> children = new Array<node>();
    public Vector2 position;
    public Vector2 globalPosition = new Vector2(0,0);
    public Vector2 parentPosition;

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

        //System.out.println(this);

        update(Gdx.graphics.getDeltaTime());

        updateParentPos();
        for (node child: children){
            child.updateCascade();
        }

    }

    public void update(double delta){

    }

    public void updateParentPos(){
        updateGlobalPosition();
        for (node child:children) {
            child.parentPosition.set(globalPosition);
            child.parent = this;
        }
    }

    public void renderCascade(SpriteBatch batch){
        render(batch);
        for (node child: children){
            child.renderCascade(batch);
        }
    }

    public void render(SpriteBatch batch){

    }

    public void debugCascade(){
        debug();
        for (node child: children){
            child.debugCascade();
        }
    }

    public void debug(){

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
