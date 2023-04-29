package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.MovementNode;

import java.util.ArrayList;


public class Particle extends MovementNode {

    Vector2 vel;

    double rotationVel;

    double rotation;

    double lifeSpan;

    double gravity;

    Vector2 accel; //it will add this number to its velocity on "real" frames
    double damping; //it will slow its velocity by this percent on "real" frames. Making this value above one will change it to acceleration
    double speedForward; //speed in direction it is facing. ignores velocity entirely.

    boolean collide; //whether this particle will collide

    double bounce;

    Color color;

    Color targetColor;

    double lerpToColor; //changes the color to target color by this percent of the difference
    double moveToColor; //changes the color to target color by this number

    public Particle() {
        super(0, 0, getMaskLayers(), getMaskLayers());
        this.vel = new Vector2(0,0);
        this.rotationVel = 0;
        this.rotation = 0;
        this.lifeSpan = 0;
        this.gravity = 0;
        this.accel = new Vector2(0,0);
        this.damping = 0;
        this.speedForward = 0;
        this.collide = false;
        this.color = new Color(0,0,0,0);
        this.targetColor = new Color(0,0,0,0);
        this.lerpToColor = 0;
        this.moveToColor = 0;
        this.bounce = 0;
    }

    public Particle init(Vector2 vel, double rotationVel, double rotation, double lifeSpan, double gravity, Vector2 accel, double damping, double speedForward, double bounce, boolean collide, Color color, Color targetColor, double lerpToColor, double moveToColor) {
        this.vel.set(vel);
        this.rotationVel = rotationVel;
        this.rotation = rotation;
        this.lifeSpan = lifeSpan;
        this.gravity = gravity;
        this.accel.set(accel);
        this.damping = damping;
        this.speedForward = speedForward;
        this.collide = collide;
        this.color.set( color);
        this.targetColor.set( targetColor);
        this.lerpToColor = lerpToColor;
        this.moveToColor = moveToColor;
        this.bounce = bounce;

        return this;

    }

    public void update(double delta){

        lifeSpan -= delta;
        if (lifeSpan < 0) queueFree();

        vel.scl((float) damping);
        vel.y -= gravity;
        vel.add((float) (accel.x * delta), (float) (accel.y * delta));

        rotation += rotationVel * delta;

        Vector2 tempVel = ObjectPool.getGarbage(Vector2.class).set(vel);

        Vector2 forwardVel = ObjectPool.getGarbage(Vector2.class).set((float) speedForward,0).rotateRad((float) rotation);

        tempVel.add(forwardVel);

        if (collide){
            Vector2 newVel = moveAndSlide(tempVel,delta);

            if (!MathUtils.isEqual(newVel.x,tempVel.x, 0.01F)){
                vel.x *= -bounce;
            }
            if (!MathUtils.isEqual(newVel.y,tempVel.y, 0.01F)){
                vel.y *= -bounce;
            }

        } else {
            position.add(tempVel);
        }


    }



}