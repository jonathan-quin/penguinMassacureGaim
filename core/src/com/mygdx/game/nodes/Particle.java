package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Paint;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;

import static com.badlogic.gdx.math.MathUtils.lerp;
import static com.mygdx.game.helpers.utilities.MathUtilsCustom.moveTowardsNum;


public class Particle extends MovementNode {

    public Vector2 vel; //changes

    protected double rotation; //changes

    protected Color color; //changes



    //all of these are constant
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

    protected TextureEntity sprite;

    public void ready(){

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.whitePixel,0,0,0,0));

        sprite = (TextureEntity) lastChild();

        addChild(ObjectPool.get(CollisionShape.class).init(0.5f,0.5f,0,0));

        addToGroup("rewind");

    }

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
        super.init(0,0,getMaskLayers(),getMaskLayers());
        this.vel.set(vel);
        this.rotationVel = rotationVel;
        this.rotation = rotation;
        this.lifeSpan = lifeSpan;
        this.gravity = gravity;
        this.accel.set(accel);
        this.damping = damping;
        this.speedForward = speedForward;
        this.collide = collide;
        this.color.set(color);
        this.targetColor.set( targetColor);
        this.lerpToColor = lerpToColor;
        this.moveToColor = moveToColor;
        this.bounce = bounce;

        firstFrame = true;
        paint = false;

        return this;

    }

    public void update(double delta){

        updateGlobalPosition();

        //System.out.println(vel + " " + this);

        lifeSpan -= delta;
        if (lifeSpan < 0) queueFree();

        vel.set(lerp(vel.x, (float) 0, (float) (damping*60*delta)),lerp(vel.y, (float) 0, (float) (damping*60*delta)));

        vel.y -= delta * gravity;

        vel.add((float) (accel.x * delta), (float) (accel.y * delta));

        rotation += rotationVel * delta;

        Vector2 tempVel = ObjectPool.getGarbage(Vector2.class).set(vel);

        Vector2 forwardVel = ObjectPool.getGarbage(Vector2.class).set((float) speedForward,0).rotateRad((float) rotation);

        tempVel.add(forwardVel);

        if (collide ){

            lastCollided = false;
            lastCollider = null;

            Vector2 newVel = moveAndSlide(tempVel,delta);

            if (lastCollided && !newVel.epsilonEquals(vel, 0.1F)){



                if (paint){
                    getParent().addChild(ObjectPool.get(Paint.class).init(position.x,position.y,targetColor));
//                    System.out.println("old " +tempVel);
//                    System.out.println("new " +newVel);
//                    System.out.println(testMove(tempVel));
                    queueFree();
                }

                firstFrame = false;

                if (!MathUtils.isEqual(newVel.x, tempVel.x, 0.01F)) {
                    vel.x *= -bounce;
                }
                if (!MathUtils.isEqual(newVel.y, tempVel.y, 0.01F)) {
                    vel.y *= -bounce;
                }
            }

        } else {
            position.add(tempVel.scl((float) delta));
            firstFrame = false;
        }

        color.lerp(targetColor, (float) ((float) lerpToColor * delta));
        color.set((float) moveTowardsNum(color.r,targetColor.r,moveToColor * delta),
                (float) moveTowardsNum(color.g,targetColor.g,moveToColor * delta),
                (float) moveTowardsNum(color.b,targetColor.b,moveToColor * delta),
                (float) moveTowardsNum(color.a,targetColor.a,moveToColor * delta));

        sprite.setRotation(rotation);
        sprite.setMyColor(color);




    }





}
