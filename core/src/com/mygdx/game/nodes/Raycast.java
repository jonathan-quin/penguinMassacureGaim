package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.helpers.Globals;
import com.mygdx.game.helpers.ObjectPool;
import com.mygdx.game.nodes.collisionShapeHelpers.SweepInfo;

import java.util.ArrayList;

public class Raycast extends Node{

    public Node lastCollider;
    public boolean lastCollided;
    protected ArrayList<Integer> mask;



    Vector2 lastCollisionPoint;

    public Vector2 castTo;

    public Raycast(){

        super();

        castTo = new Vector2(0,0);
        lastCollider = null;
        lastCollided = false;

        lastCollisionPoint = new Vector2(0,0);

        setMaskLayers( getMaskLayers(0) );


    }

    public Raycast init(float x,float y, float castX,float castY,ArrayList<Integer> mask){
        super.init(x,y);


        lastCollider = null;
        lastCollided = false;

        lastCollisionPoint.set(castX,castY);

        setMaskLayers(mask);

        return this;
    }

    public static ArrayList<Integer> getMaskLayers(int...newArr){

        ArrayList<Integer> arr = (ArrayList<Integer>) ObjectPool.getGarbage(ArrayList.class);

        arr.clear();

        for (int i = 0; i < newArr.length; i++){
            arr.add(newArr[i]);
        }

        return arr;
    }

    public void setMaskLayers(ArrayList<Integer> newMask){
        this.mask.clear();
        for (int i = 0; i < newMask.size(); i++){
            this.mask.add(i,newMask.get(i));
        }
    }

    public void updateRaycast(){
        lastCollisionPoint.set(getGlobalFirstCollision(castTo));
    }

    public boolean isColliding(){
        return lastCollided;
    }

    public Vector2 getCollisionPoint(){
        return lastCollisionPoint;
    }

    public void setCast(Vector2 newCast){
        castTo.set(newCast);
    }

    public void update(double delta){
        updateRaycast();
    }

    public CollisionShape myPoint = new CollisionShape(1,1,0,0);


    public Vector2 getGlobalFirstCollision(Vector2 distance){

        updateGlobalPosition();
        SweepInfo currentInfo = sweepTestArray(distance,myRoot.getCollidersInLayers(mask));


        Vector2 output =  ((Vector2) ObjectPool.getGarbage(Vector2.class) ).set(globalPosition.x + distance.x,globalPosition.y + distance.y);

        if (currentInfo != null){
            output.set(globalPosition.x + currentInfo.offset.x,globalPosition.y + currentInfo.offset.y);
            lastCollided = currentInfo.collides;
            if (lastCollided){
                lastCollider = currentInfo.collider;
            }
            if (currentInfo.resolved){
                output.set(globalPosition);
            }
        }

        return output;

    }

    public SweepInfo sweepTest(Vector2 distance, ColliderObject other){



        SweepInfo returnSweepInfo = myPoint.sweepTestArray(distance,other.getShapes());

        return returnSweepInfo;
    }

    public SweepInfo sweepTestArray(Vector2 distance, Array<ColliderObject> others){

        updateGlobalPosition();
        myPoint.parentPosition.set(globalPosition);
        myPoint.updateGlobalPosition();

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

    public void debug(){


        if (Globals.showCollision){


            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            //I'm using the Filled ShapeType, but remember you have three of them

            Globals.globalShape.line( (globalPosition.x)- Globals.cameraOffset.x + 512,  (globalPosition.y )- Globals.cameraOffset.y + 300,(globalPosition.x)- Globals.cameraOffset.x + 512 + castTo.x,  (globalPosition.y )- Globals.cameraOffset.y + 300 + castTo.y); //assuming you have created those x, y, width and height variables




            //Gdx.gl.glDisable(GL20.GL_BLEND); //this breaks transparency apparently?
        }




    }





}
