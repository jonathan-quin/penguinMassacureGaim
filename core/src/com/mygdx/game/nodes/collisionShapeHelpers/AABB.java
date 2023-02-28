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

    public Vector2 intersectSegment(Vector2 lineStart, Vector2 offset, float paddingX, float paddingY) {

        float slope = 0;
        if (offset.x != 0) slope = offset.y/offset.x;
        float yInt = slope * lineStart.x + lineStart.y;

        float len = lineStart.dst(offset);

        float leftIntYpos = lineStart.y;
        float rightIntYpos = lineStart.y;
        float topIntXpos = lineStart.x;
        float bottomIntXpos = lineStart.x;

        if (slope != 0){

            leftIntYpos = slope * (pos.x - (half.x + paddingX));
            rightIntYpos = slope * (pos.x - (half.x + paddingX));

            topIntXpos = ((pos.y + half.y + paddingY) - yInt  )/slope;
            bottomIntXpos = ((pos.y - (half.y + paddingY)) - yInt  )/slope;

        }


/*
        if (Math.abs(leftIntYpos - lineStart.x) > Math.abs(offset.x) &&
                Math.abs(rightIntYpos - lineStart.x) > Math.abs(offset.x) &&

                Math.abs(topIntXpos - lineStart.y) > Math.abs(offset.y) &&
                Math.abs(bottomIntXpos - lineStart.y) > Math.abs(offset.y)
        ) return null;
*/

        Vector2 returnVector = new Vector2(lineStart.x + offset.x, lineStart.y + offset.y);

        //check if each intercept is within bounds and then set the vector there if it is, also make sure that it's the closest

        boolean intercepts = false;

        if (offset.y != 0){
            if (topIntXpos > pos.x - half.x && topIntXpos < pos.x + half.x) {
                intercepts = true;
                returnVector.set(topIntXpos, pos.y + half.y);
            }
            if (bottomIntXpos > pos.x - half.x && bottomIntXpos < pos.x + half.x) {
                intercepts = true;
                float tempX = bottomIntXpos;
                float tempY = pos.y - half.y;
                if (!intercepts || lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector)) {
                    returnVector.set(tempX, tempY);
                }
            }
        }


        if (offset.x != 0) {
            if (leftIntYpos > pos.y - half.y && leftIntYpos < pos.y + half.y) {
                intercepts = true;
                float tempX = pos.x - half.x;
                float tempY = leftIntYpos;
                if (!intercepts || lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector)) {
                    returnVector.set(tempX, tempY);
                }
            }
            if (rightIntYpos > pos.y - half.y && rightIntYpos < pos.y + half.y) {
                intercepts = true;
                float tempX = pos.x + half.x;
                float tempY = rightIntYpos;
                if (!intercepts || lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector)) {
                    returnVector.set(tempX, tempY);
                }
            }
        }

        if (!intercepts) return null;

        return returnVector;

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



