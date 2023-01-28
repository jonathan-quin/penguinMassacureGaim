package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.globals;
import com.mygdx.game.nodes.*;

public class player extends movementNode {

    private float MAXSPEED = 180;

    private float speed = 5;
    private Vector2 vel = new Vector2(0,0);

    private float JUMPFORCE = 200;

    private float GRAVITY = 500;

    private float ACCEL = 6;

    //private double


    public player(root myRoot) {
        this(myRoot,0f,0f);
    }

    public player(root myRoot, float x, float y) {

        super(myRoot, x, y);

        Texture penguinTX = new Texture("penguinForNow.png");
        addChild(new textureEntity(penguinTX,0,2,32,32));
        addChild(new collisionShape(16,24));
        //addChild(new collisionShape(16,16,25,8));

    }

    private final Vector2 targetSpeed = new Vector2();

    public void update(double delta){



        targetSpeed.set(0.0f,0.0f);




        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) targetSpeed.x -= MAXSPEED;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) targetSpeed.x += MAXSPEED;



        vel.x  = lerp(vel.x,targetSpeed.x, ACCEL * (float)delta);

        vel.y -= GRAVITY * delta;

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && testMove(0,-10)) vel.y = (float) JUMPFORCE;

        //move(vel);
        System.out.println("vel.x:" + vel.x);
        //System.out.println("target.x:" + targetSpeed.x);

        System.out.println("vel.y:" + vel.y);

        System.out.println(delta + " " + 1/delta);

        float inverseDelta  = 1f/(float)delta;

        vel = moveAndSlide( vel.cpy().scl((float)delta) ).cpy().scl( 60 );

        globals.cameraOffset.set(position);



    }

    private float lerp(float a, float b, float f){
        return a + f * (b - a);
    }




}
