package com.mygdx.game.nodes;

import com.badlogic.gdx.math.Vector2;

public class collisionShape extends node{

    Vector2 size = new Vector2(0,0);

    public collisionShape(int sizeX,int sizeY){
        size.x = sizeX;
        size.y = sizeY;
    }


    public boolean overlaps(collisionShape other){
        updateGlobalPosition();
        if (globalPosition.x + size.x > other.globalPosition.x && true )

    }


}
