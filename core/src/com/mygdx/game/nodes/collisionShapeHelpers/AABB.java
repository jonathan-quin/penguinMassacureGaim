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

       // float len = lineStart.dst(offset);

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
            if (topIntXpos > pos.x - (half.x + paddingX) && topIntXpos < pos.x + (half.x + paddingX)) {
                intercepts = true;
                returnVector.set(topIntXpos, pos.y + (half.y + paddingY));
            }
            if (bottomIntXpos > pos.x - (half.x + paddingX) && bottomIntXpos < pos.x + (half.x + paddingX)) {
                intercepts = true;
                float tempX = bottomIntXpos;
                float tempY = pos.y - (half.y + paddingY);
                if (!intercepts || lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector)) {
                    System.out.println("CASE ONE HAPPENED");
                    returnVector.set(tempX, tempY);
                }
            }
        }


        if (offset.x != 0) {
            if (leftIntYpos > pos.y - (half.y + paddingY) && leftIntYpos < pos.y + (half.y + paddingY)) {
                intercepts = true;
                float tempX = pos.x - (half.x + paddingX);
                float tempY = leftIntYpos;
                if (!intercepts || lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector)) {
                    System.out.println("CASE TWO HAPPENED");
                    returnVector.set(tempX, tempY);
                }
            }
            if (rightIntYpos > pos.y - (half.y + paddingY) && rightIntYpos < pos.y + (half.y + paddingY)) {
                intercepts = true;
                float tempX = pos.x + (half.x + paddingX);
                float tempY = rightIntYpos;
                if (!intercepts || lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector)) {
                    System.out.println("CASE THREE HAPPENED");
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

    public sweepInfo sweepAABB(AABB otherBox,Vector2 offset) {

        sweepInfo info = new sweepInfo();

        info.length = offset.len();

        if (info.length == 0){
            info.firstImpact = new Vector2(pos);
            info.offset = new Vector2(0,0);
            info.time = 1;
            return info;
        }

        Vector2 intersectPoint = otherBox.intersectSegment(pos,offset,half.x,half.y);
        info.firstImpact = otherBox.intersectSegment(pos,offset,half.x,half.y);

        info.collides = true;
        if (info.firstImpact == null){
            info.collides = false;
            info.firstImpact = new Vector2(pos.x + offset.x,pos.y + offset.y);
        }

        //System.out.println(info.collides);
        System.out.println(otherBox.intersectSegment(pos,offset.cpy().scl(100),half.x,half.y) != null);

        info.offset = new Vector2(info.firstImpact.x - pos.x,info.firstImpact.y-pos.y);



        info.time = (info.offset.len())/info.length;
       // System.out.println(offset + " " + info.offset + " time: " + info.time);

       // if (info.time < 0.99) System.out.println("gfnejsnbojgesbkjbtehgewbjh lre");

        return info;
    }



}



