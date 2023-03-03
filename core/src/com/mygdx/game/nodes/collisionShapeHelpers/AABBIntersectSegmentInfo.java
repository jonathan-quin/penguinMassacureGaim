package com.mygdx.game.nodes.collisionShapeHelpers;

public class AABBIntersectSegmentInfo {
    boolean collides; //whether the line intersects the box
    boolean resolves; //true if both ends of the segment are within the box and the result is the nearest way out

    float x = 0;
    float y = 0;

    public AABBIntersectSegmentInfo(boolean collides,boolean resolves,float x, float y){
        this.collides = collides;
        this.resolves = resolves;
        this.x = x;
        this.y = y;

    }

}
