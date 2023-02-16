package com.mygdx.game.nodes.collisionShapeHelpers;

import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.clamp;

import static java.lang.Math.abs;

public class AABB {


    public Vector2 pos = new Vector2();
    public Vector2 half = new Vector2();

    public AABB (Vector2 pos,Vector2 half){
        this.pos.set(pos);
        this.half.set(half);
    }



    public int sign(float value) {


        return value < 0 ? -1 : 1;
    }

    public Vector2 intersectSegment(Vector2 position, Vector2 offset, float paddingX, float paddingY) {

        float slope = 0;
        if (offset.x != 0) slope = offset.y/offset.x;
        float yInt = slope * position.x + position.y;

        float len = position.dst(offset);

        float leftBound = pos.x - (half.x + paddingX);
        float rightBound = pos.x - (half.x + paddingX);
        float upperBound = (pos.y + half.y + paddingY) - yInt;
        float lowerBound = (pos.y - (half.y + paddingY)) - yInt;

        if (slope != 0){
            leftBound = slope * (pos.x - (half.x + paddingX));
            rightBound = slope * (pos.x - (half.x + paddingX));

            upperBound = ((pos.y + half.y + paddingY) - yInt  )/slope;
            lowerBound = ((pos.y - (half.y + paddingY)) - yInt  )/slope;

        }

        if (Math.abs(leftBound - position.x) > Math.abs(offset.x) &&
                Math.abs(rightBound - position.x) > Math.abs(offset.x) &&

                Math.abs(upperBound - position.y) > Math.abs(offset.y) &&
                Math.abs(lowerBound - position.y) > Math.abs(offset.y)
        ) return null;

        Vector2 returnVector = new Vector2();
        
        if ()


        return Vector2.Zero;

    }

    public boolean intersectAABB(AABB box) {
        if (pos.x + half.x < box.pos.x - box.half.x) return false;
        if (pos.x - half.x > box.pos.x + box.half.x) return false;
        if (pos.y + half.y < box.pos.y - box.half.y) return false;
        if (pos.y - half.y > box.pos.y + box.half.y) return false;
        return true;
    }

    public sweepInfo sweepAABB(AABB box,Vector2 offset) {

    }



}



