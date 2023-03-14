package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.globals;
import com.mygdx.game.nodes.collisionShapeHelpers.sweepInfo;

public class colliderObject extends node{


    Array<collisionShape> shapes = new Array<collisionShape>();
    root myRoot;

    int[] mask;
    int[] layers;

    public boolean isColliding(){
        return overlaps(myRoot.colliders);
    }

    public Vector2 getFirstCollision(Vector2 distance){

        sweepInfo currentInfo = sweepTestArray(distance,myRoot.getCollidersInLayers(mask));

       // System.out.println("first impact " + currentInfo.firstImpact);

        if (false && globals.showCollision){


            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            globals.globalShape.begin(ShapeRenderer.ShapeType.Filled);
            globals.globalShape.setColor(new Color(0, 1, 0, 1f));

            globals.globalShape.circle( (globalPosition.x )-globals.cameraOffset.x + 512,
                    (globalPosition.y )-globals.cameraOffset.y + 300,5);

            globals.globalShape.setColor(new Color(0, 1, 1, 1f));
            globals.globalShape.circle( ( currentInfo.firstImpact.x)-globals.cameraOffset.x + 512,
                    (currentInfo.firstImpact.y )-globals.cameraOffset.y + 300,5);

           // if (currentInfo.hit != null) globals.globalShape.circle( (globalPosition.x + currentInfo.hit.delta.x)-globals.cameraOffset.x + 512,
            //        ( globalPosition.y + currentInfo.hit.delta.y )-globals.cameraOffset.y + 300,5);

            globals.globalShape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } //debugging nonsense

        Vector2 output = new Vector2(position.x + distance.x,position.y + distance.y);

        if (currentInfo != null){
            output.set(position.x + currentInfo.offset.x,position.y + currentInfo.offset.y);
        }


        //System.out.println("output" + output);
       // System.out.println("first" + currentInfo.firstImpact + " resolves: " + currentInfo.collides);

       // System.out.println(currentInfo.time);
        return output;//currentInfo.firstImpact;

    }

    public sweepInfo sweepTest(Vector2 distance,colliderObject other){

        if (other == this) return null;

        sweepInfo returnSweepInfo = null;
        sweepInfo tempSweepInfo;
        for (collisionShape myShape : shapes){
            tempSweepInfo = myShape.sweepTestArray(distance,other.getShapes());
            if (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time )
                returnSweepInfo = tempSweepInfo;

        }
        return returnSweepInfo;  
    }

    public sweepInfo sweepTestArray(Vector2 distance,Array<colliderObject> others){
        sweepInfo returnSweepInfo = null;
        sweepInfo tempSweepInfo;
        for (colliderObject other : others){
            tempSweepInfo = sweepTest(distance,other);
            if ( (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time) )
                returnSweepInfo = tempSweepInfo;

        }

        //System.out.println("colliderobjecct array " + returnSweepInfo.firstImpact);

        return returnSweepInfo;
    }

    public boolean overlaps(colliderObject other){

        if (other == this){
            //System.out.println("collider equals this!");
            return false;
        }

        for (collisionShape shape : shapes){
            if (shape instanceof collisionShape) {
                if (shape.overlaps(other.getShapes())) return true;
            }
        }
        return false;
    }

    public boolean overlaps(Array<colliderObject> others){
        for (colliderObject other : others){
            if (overlaps(other)) return true;
        }

        return false;
    }

    public Array<collisionShape> getShapes(){
        return shapes;
    }

    public colliderObject(root myRoot){
        this(myRoot,0,0,new int[]{0},new int[]{0});
    }
    public colliderObject(root myRoot,float x, float y, int[] mask,int[] layers ){
        super(x,y);
        this.myRoot = myRoot;
        this.myRoot.colliders.add(this);

        this.mask = new int[mask.length];
        for (int i = 0; i < mask.length; i++){
            this.mask[i] = mask[i];
        }
        this.layers = new int[layers.length];
        for (int i = 0; i < layers.length; i++){
            this.layers[i] = layers[i];
        }

    }

    public void addChild(node child){
        children.add(child);

        if (child instanceof collisionShape){
            shapes.add( (collisionShape) child);
        }

    }

    public boolean removeChild(node child){

        if (children.contains(child,true)){

            children.removeIndex(children.indexOf(child,true));

        }

        if (child instanceof collisionShape){
            if (shapes.contains((collisionShape) child,true)){
                shapes.removeIndex(children.indexOf(child,true));
                return true;
            }
        }

        return false;

    }

    public void free(){
        getParent().removeChild(this);
        for (node child : children){
            child.free();
        }
        myRoot.colliders.removeIndex(myRoot.colliders.indexOf(this,true));
    }

}
