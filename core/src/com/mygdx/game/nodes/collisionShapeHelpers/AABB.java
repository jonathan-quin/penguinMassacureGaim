package com.mygdx.game.nodes.collisionShapeHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.globals;

import static com.badlogic.gdx.math.MathUtils.clamp;

import static java.lang.Math.abs;

public class AABB {


    public Vector2 pos = new Vector2();
    public Vector2 half = new Vector2();

    public AABB (Vector2 pos,Vector2 half){
        this.pos.set(pos);
        this.half.set(half);
    }

    static int count = 0;

    public int sign(float value) {


        return value < 0 ? -1 : 1;
    }

    public AABBIntersectSegmentInfo intersectSegment(Vector2 lineStart, Vector2 offset, float paddingX, float paddingY) {

        AABBIntersectSegmentInfo returnInfo = new AABBIntersectSegmentInfo(false,false,0,0);

        if (containsPoint(lineStart,paddingX,paddingY,0f) ){

            Vector2 endPoint = lineStart.cpy().add(offset);

           /* if (edgeContainsPoint(endPoint,paddingX,paddingY)){
                returnInfo.collides = false;
                returnInfo.resolves = false;

                returnInfo.x = lineStart.x + offset.x;
                returnInfo.y = lineStart.y + offset.y;

                return returnInfo;
            }


           else if (!containsPoint(endPoint, paddingX, paddingY, -0.001f)) {

               returnInfo.collides = false;
               returnInfo.resolves = false;

               returnInfo.x = lineStart.x + offset.x;
               returnInfo.y = lineStart.y + offset.y;

               return returnInfo;

            }
            else{

               float yResolution = endPoint.y;
               float xResolution = endPoint.x;

               if (endPoint.y > pos.y) {
                   yResolution = (float) ((pos.y + half.y + paddingY));
               } else {
                   yResolution = (float) ((pos.y - half.y - paddingY));
               }

               if (endPoint.x > pos.x) {
                   xResolution = (float) ((pos.x + half.x + paddingX));
               } else {
                   xResolution = (float) ((pos.x - half.x - paddingX));
               }

               if (abs(xResolution - endPoint.x) > abs(yResolution - endPoint.y)) {
                   returnInfo.x = endPoint.x;
                   returnInfo.y = yResolution;
               } else {
                   returnInfo.x = xResolution;
                   returnInfo.y = endPoint.y;
               }


               returnInfo.collides = true;
               returnInfo.resolves = false;
               //returnInfo.resolves = containsPoint(lineStart,paddingX,paddingY,-0.001f);

               System.out.println("goto" + returnInfo.x);

               return returnInfo;

            }*/

            if (edgeContainsPoint(endPoint,paddingX,paddingY)){
                returnInfo.collides = false;
                returnInfo.resolves = false;

                returnInfo.x = lineStart.x + offset.x;
                returnInfo.y = lineStart.y + offset.y;

                return returnInfo;
            }


            else if (!containsPoint(endPoint, paddingX, paddingY, -0.00f)) {

                returnInfo.collides = false;
                returnInfo.resolves = false;

                returnInfo.x = lineStart.x + offset.x;
                returnInfo.y = lineStart.y + offset.y;

                return returnInfo;

            }
            else if (containsPoint(lineStart,paddingX,paddingY,-0.00f)){

                float yResolution = endPoint.y;
                float xResolution = endPoint.x;

                if (endPoint.y > pos.y) {
                    yResolution = (float) ((pos.y + half.y + paddingY));
                } else {
                    yResolution = (float) ((pos.y - half.y - paddingY));
                }

                if (endPoint.x > pos.x) {
                    xResolution = (float) ((pos.x + half.x + paddingX));
                } else {
                    xResolution = (float) ((pos.x - half.x - paddingX));
                }

                if (abs(xResolution - endPoint.x) > abs(yResolution - endPoint.y)) {
                    returnInfo.x = endPoint.x;
                    returnInfo.y = yResolution;
                } else {
                    returnInfo.x = xResolution;
                    returnInfo.y = endPoint.y;
                }


                returnInfo.collides = true;
                returnInfo.resolves = true;
                //returnInfo.resolves = containsPoint(lineStart,paddingX,paddingY,-0.001f);



                return returnInfo;

            }
            else{
                returnInfo.collides = true;
                returnInfo.resolves = false;

                returnInfo.x = lineStart.x;
                returnInfo.y = lineStart.y;

                return returnInfo;
            }


        }

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




        Vector2 returnVector = new Vector2(lineStart.x + offset.x, lineStart.y + offset.y);

        //check if each intercept is within bounds and then set the vector there if it is, also make sure that it's the closest

        boolean intercepts = false;

        if (offset.y != 0){
            if (topIntXpos > pos.x - (half.x + paddingX) && topIntXpos < pos.x + (half.x + paddingX)) {


                float tempX = topIntXpos;
                float tempY = pos.y + (half.y + paddingY);

                if (lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector) && sign(lineStart.x - tempX) == sign(lineStart.x - returnVector.x ) && sign(lineStart.y - tempY) == sign(lineStart.y - returnVector.y )){
                    intercepts = true;
                    returnVector.set(tempX,tempY);
                }

            }
            if (bottomIntXpos > pos.x - (half.x + paddingX) && bottomIntXpos < pos.x + (half.x + paddingX)) {

                float tempX = bottomIntXpos;
                float tempY = pos.y - (half.y + paddingY);
                if (lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector) && sign(lineStart.x - tempX) == sign(lineStart.x - returnVector.x ) && sign(lineStart.y - tempY) == sign(lineStart.y - returnVector.y ) ) {
                    intercepts = true;
                    returnVector.set(tempX, tempY);
                }
            }
        }


        if (offset.x != 0) {
            if (leftIntYpos > pos.y - (half.y + paddingY) && leftIntYpos < pos.y + (half.y + paddingY)) {

                float tempX = pos.x - (half.x + paddingX);
                float tempY = leftIntYpos;
                if (lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector) && sign(lineStart.x - tempX) == sign(lineStart.x - returnVector.x ) && sign(lineStart.y - tempY) == sign(lineStart.y - returnVector.y )) {
                    intercepts = true;
                    returnVector.set(tempX, tempY);
                }
            }
            if (rightIntYpos > pos.y - (half.y + paddingY) && rightIntYpos < pos.y + (half.y + paddingY)) {

                float tempX = pos.x + (half.x + paddingX);
                float tempY = rightIntYpos;
                if (lineStart.dst2(tempX, tempY) < lineStart.dst2(returnVector) && sign(lineStart.x - tempX) == sign(lineStart.x - returnVector.x ) && sign(lineStart.y - tempY) == sign(lineStart.y - returnVector.y )) {
                    intercepts = true;
                    returnVector.set(tempX, tempY);
                }
            }
        }



        //if (!intercepts) return null;


        if (globals.showCollision ){

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            globals.globalShape.begin(ShapeRenderer.ShapeType.Filled);
            globals.globalShape.setColor(new Color(1, 1, 1, 1f));

            globals.globalShape.circle( (returnVector.x )-globals.cameraOffset.x + 512,
                    (returnVector.y )-globals.cameraOffset.y + 300,4);

            globals.globalShape.line( (returnVector.x )-globals.cameraOffset.x + 512,
                    (returnVector.y )-globals.cameraOffset.y + 300,
                    (pos.x )-globals.cameraOffset.x + 512,
                    (pos.y )-globals.cameraOffset.y + 300);

            globals.globalShape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } //debugging nonsense


        returnInfo.x = returnVector.x;
        returnInfo.y = returnVector.y;
        returnInfo.collides = intercepts;
        if (intercepts) System.out.println("intercepts: " + intercepts);
        returnInfo.resolves = false;

        if (containsPoint(returnVector,paddingX,paddingY,-0.1f)) System.out.println("WELL THAT'S A PROBLEM " + intercepts);

        return returnInfo;

    }

    public boolean containsPoint(Vector2 point,float paddingX, float paddingY, float tolerance){

        if (point.x > pos.x + paddingX + half.x + tolerance) return false;
        if (point.x < pos.x - paddingX - half.x - tolerance) return false;
        if (point.y > pos.y + paddingY + half.y + tolerance) return false;
        if (point.y < pos.y - paddingY - half.y - tolerance) return false;

        return true;
    }

    public boolean edgeContainsPoint(Vector2 point,float paddingX, float paddingY){

        if (point.x > pos.x + paddingX + half.x) return false;
        if (point.x < pos.x - paddingX - half.x) return false;
        if (point.y > pos.y + paddingY + half.y) return false;
        if (point.y < pos.y - paddingY - half.y) return false;

        if (point.x == pos.x + paddingX + half.x) return true;
        if (point.x == pos.x - paddingX - half.x) return true;
        if (point.y == pos.y + paddingY + half.y) return true;
        if (point.y == pos.y - paddingY - half.y) return true;

        return false;
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


        AABBIntersectSegmentInfo segmentInfo = otherBox.intersectSegment(pos,offset,half.x,half.y);

        info.collides = segmentInfo.collides || segmentInfo.resolves;

        if (!segmentInfo.collides && !segmentInfo.resolves){
            info.collides = false;
            info.firstImpact = new Vector2(pos.x + offset.x,pos.y + offset.y);
            info.offset = new Vector2(info.firstImpact.x - pos.x,info.firstImpact.y-pos.y);
            info.time = 1;
            return info;
        }

        info.firstImpact = new Vector2(segmentInfo.x,segmentInfo.y);
        info.offset = new Vector2(info.firstImpact.x - pos.x,info.firstImpact.y-pos.y);
        info.time = 1; //(info.offset.len())/info.length;

        if (segmentInfo.resolves){
            info.time = 0;
        }
        else if (segmentInfo.collides){
            info.time = (info.offset.len())/info.length;
        }


        //if (info.time > 1) System.out.println("time is out of wack " + info.time);

        //System.out.println("AABB first" + info.firstImpact);

        return info;
    }



}



