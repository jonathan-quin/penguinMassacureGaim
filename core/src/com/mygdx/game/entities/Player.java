package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.*;
import com.mygdx.game.helpers.utilities.MathUtils;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.*;

import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.min;

public class Player extends MovementNode implements TimeRewindInterface {

    private float MAXSPEED = 180;

    private float speed = 5;
    private Vector2 vel;

    private float health = 100;

    private float JUMPFORCE = 240;

    private float GRAVITY = 400;

    private float ACCEL = 6;

    double rotation = 0;

    private Node bulletHolder;

    private Raycast ray;

    //private double


    public Player() {
        this(0f,0f);
    }

    public Player( float x, float y) {

        super(x, y, getMaskLayers(LayerNames.WALLS),getMaskLayers(LayerNames.PLAYER));

        vel = new Vector2(0,0);

        init(x,y);

    }

    public Player init(float x, float y){

        super.init(x,y, getMaskLayers(LayerNames.WALLS),getMaskLayers(LayerNames.PLAYER));

        name = "player";

        rotation = 0;

        health = 100;

        vel.set(0,0);

        return this;
    }

    public Player init(){
        return init(0,0);
    }



    public <T> T load(Object... vars) {

        this.position.set( (Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);
        this.rotation = ((double) vars[3]);


        if (vel.x != 0){
            ((TextureEntity) getChild("sprite")).setFlip(vel.x < 0,false);
        }
        if (((TextureEntity) getChild("sprite")).getFlipX()) {
            ((TextureEntity) getChild("sprite")).setRotation(rotation);
        } else {
            ((TextureEntity) getChild("sprite")).setRotation(-rotation);
        }

        health = (float) vars[4];

        updateGlobalPosition();
        Globals.cameraOffset.set(globalPosition);


        return null;
    }


    public void ready(){
        bulletHolder = getRootNode().getChild("bulletHolder");

        addToGroup("rewind");

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.penguinTexture,0f,1f,0,0));
        lastChild().setName("sprite");
        addChild(ObjectPool.get(CollisionShape.class).init(8,12,0,0));


        //addChild( ObjectPool.get(Raycast.class).init(0,0,100,-100,true,getMaskLayers(LayerNames.WALLS)) );
        //ray = (Raycast) lastChild();

    }

    private final Vector2 targetSpeed = new Vector2();

    public void update(double delta){

        targetSpeed.set(0.0f,0.0f);

        boolean onFloor = testMove(0,-1);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) targetSpeed.x -= MAXSPEED;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) targetSpeed.x += MAXSPEED;

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) vel.y -= 100;

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && onFloor) vel.y = JUMPFORCE;

        vel.x  = lerp(vel.x,targetSpeed.x, ACCEL * (float)delta);

        if (vel.x != 0){
            ((TextureEntity) getChild("sprite")).setFlip(vel.x < 0,false);
        }

        if (!onFloor){
            //rotation = rotation % 360;
            rotation += (4 + min(10,rotation * 0.01)) * 60 * delta;

        }
        else{
            rotation = rotation % 360;
            rotation += MathUtils.differenceBetweenAngles(Math.toRadians(rotation),0) * 30 * 60 * delta;
            rotation = MathUtils.moveTowardsZero(rotation,0.1);

        }


        if (((TextureEntity) getChild("sprite")).getFlipX()) {
            ((TextureEntity) getChild("sprite")).setRotation(rotation);
        } else {
            ((TextureEntity) getChild("sprite")).setRotation(-rotation);
        }




        vel.y -= GRAVITY * delta;

        //System.out.println("vel before: " + vel);

        if (health <= 0){
            rotation = 90;
            ((TextureEntity) getChild("sprite")).setRotation(rotation);
            ((TextureEntity) getChild("sprite")).setFlip(false,false);
            vel.x = 0;
            vel.y = min(vel.y,0);
        }

        Vector2 tempVector = moveAndSlide( vel,(float) delta) ;
        vel.set(tempVector);


        if (Gdx.input.isKeyJustPressed(Input.Keys.X)){

            bulletHolder.addChild( ( (BulletOLD) ObjectPool.get( BulletOLD.class) ).init(globalPosition.x,globalPosition.y,vel.x*2,vel.y*2) );

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            SceneHandler.goToNextScene();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.V)){
            System.out.println(Utils.getGlobalMousePosition());
        }



        //ray.setCast(Utils.getGlobalMousePosition().sub(globalPosition));


        Globals.cameraOffset.set(position);
        //System.out.println(vel + " delta " + delta);

    }

    private float lerp(float a, float b, float f){
        return a + f * (b - a);
    }


    /*public HashMap<String,Object> save(){
        HashMap<String,Object> returnHash = (HashMap<String, Object>) ObjectPool.get(HashMap.class);

        returnHash.put("class",this.getClass());
        returnHash.put("position",ObjectPool.get(Vector2.class).set(position));


        return returnHash;
    }*/

    public ArrayList<Object> save(){
        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add(this.getClass());
        returnArr.add(ObjectPool.get(Vector2.class).set(position));
        returnArr.add(ObjectPool.get(Vector2.class).set(vel));
        returnArr.add(rotation);
        returnArr.add(health);

        return returnArr;
    }

    @Override


    public void render(SpriteBatch batch){
        //Globals.cameraOffset.set(globalPosition);
    }




    public void hit(Vector2 vel, float damage) {
        health -= damage;
    }
}
