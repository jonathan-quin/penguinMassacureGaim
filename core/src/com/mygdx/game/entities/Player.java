package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.MathHelpers;
import com.mygdx.game.helpers.Globals;
import com.mygdx.game.nodes.*;

public class Player extends MovementNode {

    private float MAXSPEED = 180;

    private float speed = 5;
    private Vector2 vel = new Vector2(0,0);

    private float JUMPFORCE = 400;

    private float GRAVITY = 2000;

    private float ACCEL = 6;

    double rotation = 0;

    //private double


    public Player(Root myRoot) {
        this(myRoot,0f,0f);
    }

    public Player(Root myRoot, float x, float y) {

        super(myRoot, x, y, new int[]{0},new int[]{0});

        Texture penguinTX = new Texture("penguinForNow.png");
        addChild(new TextureEntity(penguinTX,0,2,32,32));
        getNewestChild().name = "sprite";
        addChild(new CollisionShape(8,12,0,0));

        //addChild(new collisionShape(16,16,25,8));


        updateParentPos();

    }

    private final Vector2 targetSpeed = new Vector2();

    public void update(double delta){

        targetSpeed.set(0.0f,0.0f);

        boolean onFloor = testMove(0,-1);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) targetSpeed.x -= MAXSPEED;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) targetSpeed.x += MAXSPEED;

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && onFloor) vel.y = MAXSPEED;

        vel.x  = lerp(vel.x,targetSpeed.x, ACCEL * (float)delta);

        if (vel.x != 0){
            ((TextureEntity) getChild("sprite")).setFlip(vel.x < 0,false);
        }

        if (!onFloor){
            //rotation = rotation % 360;
            rotation += 4 + Math.min(10,rotation * 0.01);
            System.out.println(rotation);
        }
        else{
            rotation = rotation % 360;
            rotation += MathHelpers.differenceBetweenAngles(Math.toRadians(rotation),0) * 30;
            rotation = MathHelpers.moveTowardsZero(rotation,0.1);

        }
        if (((TextureEntity) getChild("sprite")).getFlipX()){
            ((TextureEntity) getChild("sprite")).setRotation(rotation);
        }else{
            ((TextureEntity) getChild("sprite")).setRotation(-rotation);
        }

        vel.y -= GRAVITY/5 * delta;



        Vector2 tempVector = moveAndSlide( vel,(float) delta) ;


        vel.set(tempVector);



        Globals.cameraOffset.set(position);

    }

    private float lerp(float a, float b, float f){
        return a + f * (b - a);
    }




}
