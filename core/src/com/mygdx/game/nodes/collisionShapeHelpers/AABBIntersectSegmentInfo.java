package com.mygdx.game.nodes.collisionShapeHelpers;

public class AABBIntersectSegmentInfo {
    boolean collides; //whether the line intersects the box
    boolean resolves; //true if both ends of the segment are within the box and the result is the nearest way out

    boolean startsWithin;
    float x = 0;
    float y = 0;

    public AABBIntersectSegmentInfo(boolean collides,boolean resolves,float x, float y){
        this.collides = collides;
        this.resolves = resolves;
        startsWithin = false;
        this.x = x;
        this.y = y;

    }

    public AABBIntersectSegmentInfo(){
        collides = false;
        resolves = false;
        startsWithin = false;
        x = 0;
        y = 0;
    }

    public AABBIntersectSegmentInfo init(boolean collides,boolean resolves,float x, float y){
        this.collides = collides;
        this.resolves = resolves;
        startsWithin = false;
        this.x = x;
        this.y = y;
        return this;
    }

}
