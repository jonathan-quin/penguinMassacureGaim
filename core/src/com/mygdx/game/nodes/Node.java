package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Player;
import com.mygdx.game.helpers.GroupHandler;
import com.mygdx.game.helpers.ObjectPool;
import java.util.ArrayList;

public class Node {
    public ArrayList<Node> children;

    public ArrayList<String> groups;


    public Vector2 position;
    public Vector2 globalPosition;
    public Vector2 parentPosition;
    public String name = "";

    Node parent = this;

    public Node(){

        this(0,0);

    }
    public Node(float x, float y){
        position = new Vector2(x,y);
        parentPosition = new Vector2(0,0);
        globalPosition = new Vector2(0,0);
        children = new ArrayList<>();
        groups = new ArrayList<>();
        updateGlobalPosition();
    }

    public Node init(float x, float y){
        position.set(x,y);
        children.clear();
        groups.clear();
        updateGlobalPosition();

        return this;
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

        try {
            for (Node child : children) {
                child.parentPosition.set(globalPosition);
                child.parent = this;
            }
        }catch (Exception ex){
            System.out.println("I have a bad child");
            System.out.println(this);
            for (Object child : children) {
                System.out.println(child);
            }
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

        if (children.contains(child)){
            children.remove(child);
            return true;
        }

        return false;

    }

    public boolean addToGroup(String group){
        if (GroupHandler.isInGroup(group,this)) return false;
        GroupHandler.addToGroup(group,this);
        return true;
    }
    public void queueFree(){
        addToGroup(GroupHandler.QUEUEFREE);
    }

    public void free(){

        for (String group : groups){
            GroupHandler.removeFromGroup(group,this);
        }

        ObjectPool.remove(this);

        getParent().removeChild(this);

        for (int i = children.size()-1;i>=0;i--){
            children.get(i).free();
            //children.remove(i);
        }
        children.clear();

    }

    public void updateGlobalPosition() {

        this.globalPosition.set( parentPosition.x + position.x,parentPosition.y+position.y  );

    }

    public Node getNewestChild(){
        return children.get(children.size() - 1);
    }

    public Node getChild(String name){
        for (Node child : children){
            if (child.name.equals( name)) return child;
        }
        return null;
    }


    public boolean isInGroup(String group) {
        return GroupHandler.isInGroup(group,this);
    }

    public boolean removeFromGroup(String group){

       boolean returnVal = GroupHandler.removeFromGroup(group,this);

       groups.remove(group);

       return returnVal;
    }

}
