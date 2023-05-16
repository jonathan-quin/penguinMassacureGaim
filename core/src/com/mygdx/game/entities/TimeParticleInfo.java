package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class TimeParticleInfo {

    protected double rotationVel;

    protected double lifeSpan;

    protected double gravity;

    protected Vector2 accel; //it will add this number to its velocity on "real" frames
    protected double damping; //it will slow its velocity by this percent on "real" frames. Making this value above one will change it to acceleration
    protected double speedForward; //speed in direction it is facing. ignores velocity entirely.

    protected boolean collide; //whether this particle will collide

    protected double bounce;

    protected Color targetColor;

    protected double lerpToColor; //changes the color to target color by this percent of the difference
    protected double moveToColor; //changes the color to target color by this number

    public boolean paint = false;

    protected boolean firstFrame = true;

    public TimeParticleInfo(){
        accel = new Vector2(0,0);
        targetColor = new Color(0,0,0,0);
    }

    public TimeParticleInfo init(double rotationVel,  double lifeSpan, double gravity, Vector2 accel, double damping, double speedForward, double bounce, boolean collide, Color targetColor, double lerpToColor, double moveToColor, boolean paint) {

        this.rotationVel = rotationVel;

        this.lifeSpan = lifeSpan;
        this.gravity = gravity;
        this.accel.set(accel);
        this.damping = damping;
        this.speedForward = speedForward;
        this.collide = collide;

        this.targetColor.set( targetColor);
        this.lerpToColor = lerpToColor;
        this.moveToColor = moveToColor;
        this.bounce = bounce;
        this.paint = paint;



        return this;

    }

}
