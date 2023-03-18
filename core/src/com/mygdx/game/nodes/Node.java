package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.helpers.ObjectPool;
import java.util.ArrayList;

public class Node {
    public Array<Node> children;


    public Vector2 position;
    public Vector2 globalPosition;
    public Vector2 parentPosition;

    public String name = "";

    Node parent = this;

    public Node(){

        this(0,0);

    }
    public Node(float x, float y){
        position = ((Vector2) ObjectPool.get(Vector2.class)).set(x,y);
        parentPosition = ((Vector2) ObjectPool.get(Vector2.class)).set(0,0);
        globalPosition = ((Vector2) ObjectPool.get(Vector2.class)).set(0,0);
        children = ( (Array<Node>) ObjectPool.get( Array.class ) );
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
