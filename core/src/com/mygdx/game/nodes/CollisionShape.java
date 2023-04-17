package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Globals;
import com.mygdx.game.nodes.collisionShapeHelpers.AABB;
import com.mygdx.game.nodes.collisionShapeHelpers.sweepInfo;

public class CollisionShape extends Node {

    Vector2 size = new Vector2(0,0);

    AABB boundingBox;

    public CollisionShape(int sizeX, int sizeY){
        this(sizeX,sizeY,0,0);
    }

    public CollisionShape(int sizeX, int sizeY, int posX, int posY){

        position.set(posX,posY);
        updateGlobalPosition();
        boundingBox = new AABB(new Vector2(globalPosition.x,globalPosition.y),new Vector2(sizeX,sizeY));


    }


    public boolean overlaps(CollisionShape other){
        updateGlobalPosition();
        boundingBox.pos.set(globalPosition);
        return (boundingBox.intersectAABB(other.getAABB()));
    }

    public boolean overlaps(Array<CollisionShape> shapes){
        for (CollisionShape shape : shapes){
            if (overlaps(shape)) return true;
        }
        return false;
    }

    public sweepInfo sweepTest(Vector2 distance, CollisionShape other){

        updateGlobalPosition();

        boundingBox.pos.set(globalPosition);

        return boundingBox.sweepAABB(other.getAABB(),distance) ;

    }

    public sweepInfo sweepTestArray(Vector2 distance,Array<CollisionShape> others){


        sweepInfo returnSweepInfo = null;
        sweepInfo tempSweepInfo;
        for (CollisionShape other : others){
            tempSweepInfo = sweepTest(distance,other);
            if (returnSweepInfo == null || tempSweepInfo.time < returnSweepInfo.time ){
                //System.out.println(tempSweepInfo.time);
                returnSweepInfo = tempSweepInfo;
                //System.out.println("hey");
            }


        }

        return returnSweepInfo;
    }




    public AABB getAABB(){
        boundingBox.pos.set(globalPosition);
        return boundingBox;
    }


    public void debug(){



        if (Globals.showCollision){

            //globals.globalShape.setProjectionMatrix(camera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Globals.globalShape.begin(ShapeRenderer.ShapeType.Filled); //I'm using the Filled ShapeType, but remember you have three of them
            Globals.globalShape.setColor(new Color(0, 0, 1, 0.5f));
            Globals.globalShape.rect( (globalPosition.x - boundingBox.half.x)- Globals.cameraOffset.x + 512,  (globalPosition.y - boundingBox.half.y)- Globals.cameraOffset.y + 300,boundingBox.half.x * 2,boundingBox.half.y * 2);//globalPosition.y-rect.height/2,rect.width,rect.height); //assuming you have created those x, y, width and height variables
            Globals.globalShape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            //System.out.print(globalPosition );
            //System.out.print(" ");
            //System.out.println((globalPosition.x - rect.width/2)-(globals.cameraOffset.x));
        }


    }

}
