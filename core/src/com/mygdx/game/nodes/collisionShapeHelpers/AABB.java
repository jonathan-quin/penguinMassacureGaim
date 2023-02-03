package com.mygdx.game.nodes.collisionShapeHelpers;

import com.badlogic.gdx.math.Vector2;

public class AABB {

    Vector2 pos = new Vector2();
    Vector2 half = new Vector2();

    public AABB (Vector2 pos,Vector2 half){
        this.pos.set(pos);
        this.half.set(half);
    }

    public hitInfo intersectPoint(Vector2 point) {
        float dx = point.x - this.pos.x;
        float px = this.half.x - Math.abs(dx);
            if (px <= 0) {
                return null;
            }

        float dy = point.y - this.pos.y;
        float py = this.half.y - Math.abs(dy);
            if (py <= 0) {
                return null;
            }

        hitInfo hit = new hitInfo(this);
            if (px < py) {
                float sx = sign(dx);
                hit.delta.x = px * sx;
                hit.normal.x = sx;
                hit.pos.x = this.pos.x + (this.half.x * sx);
                hit.pos.y = point.y;
            } else {
                float sy = sign(dy);
                hit.delta.y = py * sy;
                hit.normal.y = sy;
                hit.pos.x = point.x;
                hit.pos.y = this.pos.y + (this.half.y * sy);
            }
            return hit;
    }

    public int sign(float value) {
        return value < 0 ? -1 : 1;
    }


}
