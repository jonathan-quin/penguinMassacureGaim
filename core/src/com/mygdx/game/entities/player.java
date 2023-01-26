package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.globals;
import com.mygdx.game.nodes.*;

public class player extends movementNode {

    private float speed = 5;

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

    public void update(){

        targetSpeed.set(0,0);

        //System.out.println("ghfdsjkgealk");

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) targetSpeed.x -= speed;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) targetSpeed.x += speed;

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) targetSpeed.y += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) targetSpeed.y -= speed;

        targetSpeed.set( targetSpeed.nor().scl(speed));

        moveAndSlide(targetSpeed);

        globals.cameraOffset.set(position);

    }




}
