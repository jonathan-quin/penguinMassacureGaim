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

    public hitInfo intersectPoint(Vector2 point) {
        float dx = point.x - this.pos.x;
        float px = this.half.x - abs(dx);
            if (px <= 0) {
                return null;
            }

        float dy = point.y - this.pos.y;
        float py = this.half.y - abs(dy);
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

    public hitInfo intersectSegment(Vector2 pos, Vector2 offset, float paddingX, float paddingY) {

        float scaleX = 1.0f / offset.x;
        float scaleY = 1.0f / offset.y;
        float signX = sign(scaleX);
        float signY = sign(scaleY);
        float nearTimeX = (this.pos.x - signX * (this.half.x + paddingX) - pos.x) * scaleX;
        float nearTimeY = (this.pos.y - signY * (this.half.y + paddingY) - pos.y) * scaleY;
        float farTimeX = (this.pos.x + signX * (this.half.x + paddingX) - pos.x) * scaleX;
        float farTimeY = (this.pos.y + signY * (this.half.y + paddingY) - pos.y) * scaleY;

        if (nearTimeX > farTimeY || nearTimeY > farTimeX) {
            return null;
        }

        float nearTime = Math.max(nearTimeX, nearTimeY);
        float farTime = Math.min(farTimeX, farTimeY);

        if (nearTime >= 1 || farTime <= 0) {
            return null;
        }

        hitInfo hit = new hitInfo(this);
        hit.time = clamp(nearTime, 0, 1);
        if (nearTimeX > nearTimeY) {
            hit.normal.x = -signX;
            hit.normal.y = 0;
        } else {
            hit.normal.x = 0;
            hit.normal.y = -signY;
        }
        hit.delta.x = (1.0f - hit.time) * -offset.x;
        hit.delta.y = (1.0f - hit.time) * -offset.y;
        hit.pos.x = pos.x + offset.x * hit.time;
        hit.pos.y = pos.y + offset.y * hit.time;
        return hit;
    }

    public hitInfo intersectAABB(AABB box) {

        float dx = box.pos.x - this.pos.x;
        float px = (box.half.x + this.half.x) - abs(dx);
            if (px <= 0) {
                //System.out.println("HEY");
                return null;
            }

        float dy = box.pos.y - this.pos.y;
        float py = (box.half.y + this.half.y) - abs(dy);
            if (py <= 0) {
                //System.out.println("HEY2");
                return null;
            }

        hitInfo hit = new hitInfo(this);
            if (px < py) {
                float sx = sign(dx);
                hit.delta.x = px * sx;
                hit.normal.x = sx;
                hit.pos.x = this.pos.x + (this.half.x * sx);
                hit.pos.y = box.pos.y;
            } else {
                float sy = sign(dy);
                hit.delta.y = py * sy;
                hit.normal.y = sy;
                hit.pos.x = box.pos.x;
                hit.pos.y = this.pos.y + (this.half.y * sy);
            }
            //System.out.println("not hey");
            return hit;

    }

    public sweepInfo sweepAABB(AABB box,Vector2 offset) {
        sweepInfo sweep = new sweepInfo();

        if (offset.x == 0 && offset.y == 0) {
            sweep.pos.x = box.pos.x;
            sweep.pos.y = box.pos.y;
            sweep.hit = this.intersectAABB(box);
            sweep.time = (sweep.hit != null) ? (sweep.hit.time = 0) : 1;
            return sweep;
        }

        sweep.hit = this.intersectSegment(box.pos, offset, box.half.x, box.half.y);
        if (sweep.hit != null) {
            sweep.time = clamp(sweep.hit.time - Math.ulp(sweep.hit.time) /*0.0000001f*/, 0, 1);
            sweep.pos.x = box.pos.x + offset.x * sweep.time;
            sweep.pos.y = box.pos.y + offset.y * sweep.time;
      Vector2 direction = offset.cpy();
            direction.nor();
            sweep.hit.pos.x = clamp(
                    sweep.hit.pos.x + direction.x * box.half.x,
                    this.pos.x - this.half.x, this.pos.x + this.half.x);
            sweep.hit.pos.y = clamp(
                    sweep.hit.pos.y + direction.y * box.half.y,
                    this.pos.y - this.half.y, this.pos.y + this.half.y);
        } else {
            sweep.pos.x = box.pos.x + offset.x;
            sweep.pos.y = box.pos.y + offset.y;
            sweep.time = 1;
        }
        return sweep;
    }



}



