package com.mygdx.game.nodes.collisionShapeHelpers;

import com.badlogic.gdx.math.Vector2;

public class Segment {

    Vector2 posStart = new Vector2();
    Vector2 posEnd = new Vector2();

    public Segment(Vector2 posStart, Vector2 posEnd){
        this.posStart.set(posStart);
        this.posEnd.set(posEnd);
    }

}
