package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.globals;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.nodes.collisionShapeHelpers.AABB;

public class collisionShape extends node{

    Vector2 size = new Vector2(0,0);

    AABB boundingBox;

    public collisionShape(int sizeX,int sizeY){
        this(sizeX,sizeY,0,0);
    }

    public collisionShape(int sizeX,int sizeY,int posX, int posY){

        boundingBox = new AABB(new Vector2(posX,posY),new Vector2(sizeX,sizeY));

    }


    public boolean overlaps(collisionShape other){
        updateGlobalPosition();
        boundingBox.pos.set(globalPosition);
        return (boundingBox.intersectAABB(other.getAABB()) != null);
    }

    public boolean overlaps(Array<collisionShape> shapes){
        for (collisionShape shape : shapes){
            if (overlaps(shape)) return true;
        }
        return false;
    }





    public AABB getAABB(){
        return boundingBox;
    }


    public void debug(){



        if (globals.showCollision){

            //globals.globalShape.setProjectionMatrix(camera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            globals.globalShape.begin(ShapeRenderer.ShapeType.Filled); //I'm using the Filled ShapeType, but remember you have three of them
            globals.globalShape.setColor(new Color(0, 0, 1, 0.5f));
            globals.globalShape.rect( (globalPosition.x - boundingBox.half.x)-globals.cameraOffset.x + 512,  (globalPosition.y - boundingBox.half.y)-globals.cameraOffset.y + 300,boundingBox.half.x * 2,boundingBox.half.y * 2);//globalPosition.y-rect.height/2,rect.width,rect.height); //assuming you have created those x, y, width and height variables
            globals.globalShape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            //System.out.print(globalPosition );
            //System.out.print(" ");
            //System.out.println((globalPosition.x - rect.width/2)-(globals.cameraOffset.x));


        }


    }

}
