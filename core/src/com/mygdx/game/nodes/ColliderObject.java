package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.helpers.Globals;
import com.mygdx.game.helpers.ObjectPool;
import com.mygdx.game.nodes.collisionShapeHelpers.SweepInfo;

import java.util.ArrayList;

public class ColliderObject extends Node {


    Array<CollisionShape> shapes = ( (Array<CollisionShape>) ObjectPool.get( Array.class ) );
    public Root myRoot;
    ArrayList<Integer> mask;
    ArrayList<Integer> layers;

    public boolean isColliding(){
        return overlaps(myRoot.colliders);
    }

    public Vector2 getFirstCollision(Vector2 distance){

        SweepInfo currentInfo = sweepTestArray(distance,myRoot.getCollidersInLayers(mask));

       // System.out.println("first impact " + currentInfo.firstImpact);


        Vector2 output = new Vector2(position.x + distance.x,position.y + distance.y);

        if (currentInfo != null){
            output.set(position.x + currentInfo.offset.x,position.y + currentInfo.offset.y);
        }


        //System.out.println("output" + output);
       // System.out.println("first" + currentInfo.firstImpact + " resolves: " + currentInfo.collides);

       // System.out.println(currentInfo.time);
        return output;//currentInfo.firstImpact;

    }

    public SweepInfo sweepTest(Vector2 distance, ColliderObject other){

        if (other == this) return null;

        SweepInfo returnSweepInfo = null;
        SweepInfo tempSweepInfo;
        for (CollisionShape myShape : shapes){
            tempSweepInfo = myShape.sweepTestArray(distance,other.getShapes());
            if (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time )
                returnSweepInfo = tempSweepInfo;

        }
        return returnSweepInfo;  
    }

    public SweepInfo sweepTestArray(Vector2 distance, Array<ColliderObject> others){
        SweepInfo returnSweepInfo = null;
        SweepInfo tempSweepInfo;
        for (ColliderObject other : others){
            tempSweepInfo = sweepTest(distance,other);

            if (tempSweepInfo != null && (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time) )
                returnSweepInfo = tempSweepInfo;

        }

        //System.out.println("colliderobjecct array " + returnSweepInfo.firstImpact);

        return returnSweepInfo;
    }

    public boolean overlaps(ColliderObject other){

        if (other == this){
            //System.out.println("collider equals this!");
            return false;
        }

        for (CollisionShape shape : shapes){
            if (shape instanceof CollisionShape) {
                if (shape.overlaps(other.getShapes())) return true;
            }
        }
        return false;
    }

    public boolean overlaps(Array<ColliderObject> others){
        for (ColliderObject other : others){
            if (overlaps(other)) return true;
        }

        return false;
    }

    public Array<CollisionShape> getShapes(){
        return shapes;
    }

    public ColliderObject init(Root myRoot, float x, float y, int[] mask, int[] layers){


        return this;
    }
    public ColliderObject(){

        this(null);

    }

    public ColliderObject(Root myRoot){
        this(myRoot,0,0, getMaskLayers(0) , getMaskLayers(0));


    }

    public static ArrayList<Integer> getMaskLayers(int...newArr){

        ArrayList<Integer> arr = (ArrayList<Integer>) ObjectPool.getGarbage(ArrayList.class);

        arr.clear();

        for (int i = 0; i < newArr.length; i++){
            arr.add(newArr[i]);
        }

        return arr;
    }

    public void setMaskLayers(ArrayList<Integer> mask, ArrayList<Integer> layers){
        this.mask.clear();
        for (int i = 0; i < mask.size(); i++){
            this.mask.add(i,mask.get(i));
        }

        this.layers.clear();
        for (int i = 0; i < layers.size(); i++){
            this.layers.add(i,layers.get(i));
        }
    }

    public ColliderObject(Root myRoot, float x, float y, ArrayList<Integer> mask, ArrayList<Integer> layers ){

        super(x,y);

        this.myRoot = myRoot;

        if (myRoot != null) this.myRoot.colliders.add(this);

        this.mask = ( (ArrayList<Integer>) ObjectPool.get( ArrayList.class ) );
        this.mask.clear();
        for (int i = 0; i < mask.size(); i++){
            this.mask.add(i,mask.get(i));
        }

        this.layers = ( (ArrayList<Integer>) ObjectPool.get( ArrayList.class ) );
        this.layers.clear();
        for (int i = 0; i < layers.size(); i++){
            this.layers.add(i,layers.get(i));
        }
    }

    public void setMyRoot(Root newRoot){
        this.myRoot = newRoot;
        if (myRoot != null) this.myRoot.colliders.add(this);
    }

    public void addChild(Node child){
        children.add(child);

        if (child instanceof CollisionShape){
            shapes.add( (CollisionShape) child);
        }

    }

    public boolean removeChild(Node child){

        if (children.contains(child)){

            children.remove(children.indexOf(child));

        }

        if (child instanceof CollisionShape){
            if (shapes.contains((CollisionShape) child,true)){
                shapes.removeIndex(children.indexOf(child));
                return true;
            }
        }

        return false;

    }

    public void free(){
        getParent().removeChild(this);
        for (Node child : children){
            child.free();
        }
        myRoot.colliders.removeIndex(myRoot.colliders.indexOf(this,true));
    }

}
