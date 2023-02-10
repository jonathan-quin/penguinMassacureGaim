package com.mygdx.game.nodes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.nodes.collisionShapeHelpers.sweepInfo;

public class colliderObject extends node{


    Array<collisionShape> shapes = new Array<collisionShape>();
    root myRoot;

    public boolean isColliding(){
        return overlaps(myRoot.colliders);
    }

    public Vector2 getFirstCollision(Vector2 distance){

        sweepInfo currentInfo = sweepTest(distance,myRoot.colliders);

        if (currentInfo == null) {
            System.out.println("heyo");
            return new Vector2(position.x + distance.x, position.y + distance.y);

        }

        return currentInfo.pos;

    }

    public sweepInfo sweepTest(Vector2 distance,colliderObject other){

        if (other == this) return null;

        sweepInfo returnSweepInfo = null;
        sweepInfo tempSweepInfo;
        for (collisionShape myShape : shapes){
            tempSweepInfo = myShape.sweepTest(distance,other.getShapes());
            if (tempSweepInfo != null && tempSweepInfo.hit != null && (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time) )
                returnSweepInfo = tempSweepInfo;

        }
        return returnSweepInfo;  
    }

    public sweepInfo sweepTest(Vector2 distance,Array<colliderObject> others){
        sweepInfo returnSweepInfo = null;
        sweepInfo tempSweepInfo;
        for (colliderObject other : others){
            tempSweepInfo = sweepTest(distance,other);
            if (tempSweepInfo != null && tempSweepInfo.hit != null && (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time) )
                returnSweepInfo = tempSweepInfo;

        }
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
        super();
        this.myRoot = myRoot;
        this.myRoot.colliders.add(this);
    }
    public colliderObject(root myRoot,float x, float y ){
        super(x,y);
        this.myRoot = myRoot;
        this.myRoot.colliders.add(this);
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
