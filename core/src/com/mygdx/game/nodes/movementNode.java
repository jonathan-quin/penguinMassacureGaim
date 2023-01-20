package com.mygdx.game.nodes;

import com.badlogic.gdx.math.Vector2;

public class movementNode extends colliderObject{

    public movementNode(root myRoot){
        super(myRoot);
    }

    public movementNode(root myRoot,float x, float y){
        super(myRoot,x,y);
    }


    public Vector2 moveAndSlide(Vector2 distance){

        //Vector2 returnVal = Vector2.Zero;

        position.add(distance);
        updateParentPos();
        if (!isColliding()) return distance;

        position.y = position.y - distance.y;
        updateParentPos();
        if (!isColliding()) return new Vector2(distance.x,0);

        position.y = position.y + distance.y;
        position.x = position.x - distance.x;
        updateParentPos();
        if (!isColliding()) return new Vector2(0,distance.y);

        position.y = position.y - distance.y;

        return Vector2.Zero;

    }


}
