package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Globals;
import com.mygdx.game.nodes.collisionShapeHelpers.sweepInfo;

public class ColliderObject extends Node {


    Array<CollisionShape> shapes = new Array<CollisionShape>();
    Root myRoot;

    //Constructors
    public ColliderObject(Root myRoot){
        super();
        this.myRoot = myRoot;
        this.myRoot.colliders.add(this);
    }
    public ColliderObject(Root myRoot, float x, float y ){
        super(x,y);
        this.myRoot = myRoot;
        this.myRoot.colliders.add(this);
    }

    public boolean isColliding(){
        return overlaps(myRoot.colliders);
    }

    public Vector2 getFirstCollision(Vector2 distance){

        sweepInfo currentInfo = sweepTestArray(distance,myRoot.colliders);

       // System.out.println("first impact " + currentInfo.firstImpact);

        if (false && Globals.showCollision){


            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Globals.globalShape.begin(ShapeRenderer.ShapeType.Filled);
            Globals.globalShape.setColor(new Color(0, 1, 0, 1f));

            Globals.globalShape.circle( (globalPosition.x )- Globals.cameraOffset.x + 512,
                    (globalPosition.y )- Globals.cameraOffset.y + 300,5);

            Globals.globalShape.setColor(new Color(0, 1, 1, 1f));
            Globals.globalShape.circle( ( currentInfo.firstImpact.x)- Globals.cameraOffset.x + 512,
                    (currentInfo.firstImpact.y )- Globals.cameraOffset.y + 300,5);

           // if (currentInfo.hit != null) globals.globalShape.circle( (globalPosition.x + currentInfo.hit.delta.x)-globals.cameraOffset.x + 512,
            //        ( globalPosition.y + currentInfo.hit.delta.y )-globals.cameraOffset.y + 300,5);

            Globals.globalShape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } //debugging nonsense

        Vector2 output = new Vector2(position.x + currentInfo.offset.x,position.y + currentInfo.offset.y);

        //System.out.println("output" + output);
       // System.out.println("first" + currentInfo.firstImpact + " resolves: " + currentInfo.collides);

       // System.out.println(currentInfo.time);



        return output;//currentInfo.firstImpact;

    }

    public sweepInfo sweepTest(Vector2 distance, ColliderObject other){

        if (other == this) return null;

        sweepInfo returnSweepInfo = null;
        sweepInfo tempSweepInfo;
        for (CollisionShape myShape : shapes){
            tempSweepInfo = myShape.sweepTestArray(distance,other.getShapes());
            if (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time )
                returnSweepInfo = tempSweepInfo;

        }
        return returnSweepInfo;  
    }

    public sweepInfo sweepTestArray(Vector2 distance,Array<ColliderObject> others){
        sweepInfo returnSweepInfo = null;
        sweepInfo tempSweepInfo;
        for (ColliderObject other : others){
            tempSweepInfo = sweepTest(distance,other);
            if ( (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time) )
                returnSweepInfo = tempSweepInfo;

        }

        //System.out.println("colliderobjecct array " + returnSweepInfo.firstImpact);

        return returnSweepInfo;
    }

    /**
     * Returns true if param is different object and if any of their shapes overlaps
     * @param other
     * @return
     */
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


    public void addChild(Node child){
        children.add(child);

        if (child instanceof CollisionShape){
            shapes.add( (CollisionShape) child);
        }

    }

    /**
     * Returns true if a child is successfully removed. Returns false if no child is present.
     * @param child
     * @return 
     */
    public boolean removeChild(Node child){

        if (children.contains(child,true)){

            children.removeIndex(children.indexOf(child,true));

        }

        if (child instanceof CollisionShape){
            if (shapes.contains((CollisionShape) child,true)){
                shapes.removeIndex(children.indexOf(child,true));
                return true;
            }
        }

        return false;

    }

    /**
     * Removes all children from parent object
     */
    public void free(){
        getParent().removeChild(this);
        for (Node child : children){
            child.free();
        }
        myRoot.colliders.removeIndex(myRoot.colliders.indexOf(this,true));
    }

}
