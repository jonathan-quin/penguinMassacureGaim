package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
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
        myPoint  = new CollisionShape();
        myPoint.init(1,1,0,0);

        lastCollisionPoint = new Vector2(0,0);

        mask = new ArrayList<>();
        setMaskLayers( getMaskLayers(0) );


    }

    public Raycast init(float x,float y, float castX,float castY,ArrayList<Integer> mask){
        super.init(x,y);


        lastCollider = null;
        lastCollided = false;

        castTo.set(castX,castY);

        setMaskLayers(mask);

        return this;
    }

    public void ready(){

        updateRaycast();
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



    public CollisionShape myPoint;


    public Vector2 getGlobalFirstCollision(Vector2 distance){

        updateGlobalPosition();
        SweepInfo currentInfo = sweepTestArray(distance,myRoot.getCollidersInLayers(mask));


        Vector2 output =  ((Vector2) ObjectPool.getGarbage(Vector2.class) ).set(globalPosition.x + distance.x,globalPosition.y + distance.y);

        if (currentInfo != null){
            output.set(globalPosition.x + currentInfo.offset.x,globalPosition.y + currentInfo.offset.y);

            lastCollided = currentInfo.collides || currentInfo.startedWithin;

            if (lastCollided){
                lastCollider = currentInfo.collider;
            }

            if (currentInfo.startedWithin){
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

        boolean startedInSomething = false;

        for (ColliderObject other : others){
            tempSweepInfo = sweepTest(distance,other);

            if (tempSweepInfo.startedWithin){
                startedInSomething = true;
            }

            if (tempSweepInfo != null && (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time) )
                returnSweepInfo = tempSweepInfo;

        }

        //System.out.println("colliderobjecct array " + returnSweepInfo.firstImpact);

        if (returnSweepInfo != null) returnSweepInfo.startedWithin = startedInSomething;

        return returnSweepInfo;
    }

    public void debug(){


        if (Globals.showCollision){
            myPoint.debug();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            //I'm using the Filled ShapeType, but remember you have three of them

            float offsetPosX = (globalPosition.x)- Globals.cameraOffset.x + 512;
            float offsetPosY = (globalPosition.y )- Globals.cameraOffset.y + 300;

            float distanceToPointX = lastCollisionPoint.x - globalPosition.x;
            float distanceToPointY = lastCollisionPoint.y - globalPosition.y;


            //Globals.globalShape.rect( offsetX,offsetY,offsetX + castTo.x,  offsetY + castTo.y); //assuming you have created those x, y, width and height variables

            Color tempColor = Globals.globalShape.getColor().cpy();

            Globals.globalShape.setColor(new Color(Color.RED));

            Globals.globalShape.rectLine( offsetPosX,  offsetPosY,offsetPosX + castTo.x,  offsetPosY + castTo.y,3); //assuming you have created those x, y, width and height variables

            Globals.globalShape.setColor(new Color(Color.BLUE));

            Globals.globalShape.rectLine( offsetPosX,  offsetPosY,offsetPosX + distanceToPointX,  offsetPosY + distanceToPointY,3);


            Globals.globalShape.setColor(new Color(Color.GREEN));
            if (lastCollided) Globals.globalShape.setColor(new Color(Color.YELLOW));

            Globals.globalShape.rect(offsetPosX + distanceToPointX - 5,  offsetPosY + distanceToPointY - 5,10,10);

            Globals.globalShape.setColor(tempColor);

            //Gdx.gl.glDisable(GL20.GL_BLEND); //this breaks transparency apparently?
        }




    }





}
