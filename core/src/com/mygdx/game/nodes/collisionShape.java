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

public class collisionShape extends node{

    Vector2 size = new Vector2(0,0);
    Rectangle rect = new Rectangle();

    public collisionShape(int sizeX,int sizeY){
        this(sizeX,sizeY,0,0);
    }

    public collisionShape(int sizeX,int sizeY,int posX, int posY){
        rect.width = sizeX;
        rect.height = sizeY;
        position.x = posX;
        position.y = posY;

    }


    public boolean overlaps(collisionShape other){
        updateGlobalPosition();
        rect.x = globalPosition.x - rect.width/2;
        rect.y = globalPosition.y - rect.height/2;
        return (rect.overlaps(other.getRect()));
    }

    public boolean overlaps(Array<collisionShape> others){
        updateGlobalPosition();
        rect.x = globalPosition.x - rect.width/2;
        rect.y = globalPosition.y - rect.height/2;

        for (collisionShape other : others){
            if (rect.overlaps(other.getRect())) return true;
        }

        return false;

    }

    public Rectangle getRect(){
        updateGlobalPosition();
        rect.x = globalPosition.x - rect.width/2;
        rect.y = globalPosition.y - rect.height/2;
        return rect;

    }

    public void debug(){



        if (globals.showCollision){

            //globals.globalShape.setProjectionMatrix(camera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            globals.globalShape.begin(ShapeRenderer.ShapeType.Filled); //I'm using the Filled ShapeType, but remember you have three of them
            globals.globalShape.setColor(new Color(0, 0, 1, 0.5f));
            globals.globalShape.rect( globalPosition.x - rect.width/2,  globalPosition.y-rect.height/2,rect.width,rect.height); //assuming you have created those x, y, width and height variables
            globals.globalShape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }


    }

}
