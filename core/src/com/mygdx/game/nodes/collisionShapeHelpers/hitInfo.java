package com.mygdx.game.nodes.collisionShapeHelpers;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.nodes.node;

public class hitInfo {


    public AABB collider;
    public Vector2 pos;
    public Vector2 delta;
    public Vector2 normal;
    public float time;

    public hitInfo(AABB collider) {
        this.collider = collider;
        this.pos = new Vector2();
        this.delta = new Vector2();
        this.normal = new Vector2();
        this.time = 0;


    }


}
