package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Node {
    public Array<Node> children = new Array<Node>();
    public Vector2 position;
    public Vector2 globalPosition = new Vector2(0,0);
    public Vector2 parentPosition;

    public String name = "";

    Node parent = this;

    public Node(){

        position = new Vector2(0,0);
        parentPosition = new Vector2(0,0);
        updateGlobalPosition();

    }
    public Node(float x, float y){
        position = new Vector2(x,y);
        parentPosition = new Vector2(0,0);
        updateGlobalPosition();
    }

    public Node getParent(){
        return parent;
    }

    public void updateCascade(){

        //System.out.println(this);

        update(Math.min(1/6f, Gdx.graphics.getDeltaTime()));

        updateParentPos();
        for (Node child: children){
            child.updateCascade();
        }

    }

    public void update(double delta){

    }

    public void updateParentPos(){
        updateGlobalPosition();
        for (Node child:children) {
            child.parentPosition.set(globalPosition);
            child.parent = this;
        }
    }

    public void renderCascade(SpriteBatch batch){
        render(batch);
        for (Node child: children){
            child.renderCascade(batch);
        }
    }

    public void render(SpriteBatch batch){

    }

    public void debugCascade(){
        debug();
        for (Node child: children){
            child.debugCascade();
        }
    }

    public void debug(){

    }

    public void addChild(Node child){
        children.add(child);
    }


    //returns true if the child was found
    public boolean removeChild(Node child){

        if (children.contains(child,true)){
            children.removeIndex(children.indexOf(child,true));
            return true;
        }

        return false;

    }

    public void free(){
        getParent().removeChild(this);
        for (Node child : children){
            child.free();
        }
    }

    public void updateGlobalPosition() {

        this.globalPosition.set( parentPosition.x + position.x,parentPosition.y+position.y  );

    }

    public Node getNewestChild(){
        return children.get(children.size - 1);
    }

    public Node getChild(String name){
        for (Node child : children){
            if (child.name == name) return child;
        }
        return null;
    }






}
