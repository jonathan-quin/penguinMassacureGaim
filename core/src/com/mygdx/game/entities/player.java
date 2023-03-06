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

    private float JUMPFORCE = 400;

    private float GRAVITY = 2000;

    private float ACCEL = 6;

    //private double


    public player(root myRoot) {
        this(myRoot,0f,0f);
    }

    public player(root myRoot, float x, float y) {

        super(myRoot, x, y);

        Texture penguinTX = new Texture("penguinForNow.png");
        addChild(new textureEntity(penguinTX,0,2,32,32));
        addChild(new collisionShape(8,12));

        updateGlobalPosition();
        updateParentPos();

        //addChild(new collisionShape(16,16,25,8));

    }

    private final Vector2 targetSpeed = new Vector2();

    public void update(double delta){

        targetSpeed.set(0.0f,0.0f);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) targetSpeed.x -= MAXSPEED;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) targetSpeed.x += MAXSPEED;

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && testMove(0,-10)) vel.y = MAXSPEED;
//        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) vel.y = -MAXSPEED;
//        else vel.y = 0;


        vel.x  = lerp(vel.x,targetSpeed.x, ACCEL * (float)delta);

        vel.y -= GRAVITY/5 * delta;

       // if(Gdx.input.isKeyPressed(Input.Keys.UP) && testMove(0,-10)) vel.y = (float) JUMPFORCE;

        //System.out.println(position + " delta: " + delta);
        System.out.println("before: " + vel);

        //System.out.println("vel before:" + vel);

        Vector2 tempVector = moveAndCollide( vel,(float) delta) ;

        vel.set(tempVector);



        System.out.println("after: " + vel);

        //if (Math.abs(vel.x) < 0.1) vel.x = 0;

        globals.cameraOffset.set(position);

        //System.out.println(position);

    }

    private float lerp(float a, float b, float f){
        return a + f * (b - a);
    }




}
