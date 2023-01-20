package com.mygdx.game.nodes;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class collisionShape extends node{

    Vector2 size = new Vector2(0,0);
    Rectangle rect = new Rectangle();

    public collisionShape(int sizeX,int sizeY){
        rect.width = sizeX;
        rect.height = sizeY;
    }


    public boolean overlaps(collisionShape other){
        updateGlobalPosition();
        rect.x = globalPosition.x - rect.width/2;
        rect.y = globalPosition.y - rect.width/2;
        return (rect.overlaps(other.getRect()));
    }

    public boolean overlaps(Array<collisionShape> others){
        updateGlobalPosition();
        rect.x = globalPosition.x - rect.width/2;
        rect.y = globalPosition.y - rect.width/2;

        for (collisionShape other : others){
            if (rect.overlaps(other.getRect())) return true;
        }

        return false;

    }

    public Rectangle getRect(){
        updateGlobalPosition();
        rect.x = globalPosition.x - rect.width/2;
        rect.y = globalPosition.y - rect.width/2;
        return rect;

    }

}
