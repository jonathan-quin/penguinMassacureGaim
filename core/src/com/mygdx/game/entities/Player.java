package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.penguinGuns.PenguinGun;
import com.mygdx.game.helpers.constants.*;
import com.mygdx.game.helpers.utilities.MathUtilsCustom;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.*;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Player extends MovementNode implements TimeRewindInterface {

    private final float MAXSPEED = 180;

    private float speed = 5;
    private Vector2 vel;

    private int health;

    private final int maxHealth = 1000000000;

    private final float JUMPFORCE = 240;

    private final float GRAVITY = 400;

    private final double ACCEL = 6;

    double rotation = 0;

    private Node bulletHolder;

    private Raycast ray;

    boolean dead = false;

    public ArrayList<Object> lastSave = null;

    public PenguinGun getGun() {
        return myGun;
    }

    PenguinGun myGun;

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

        health = maxHealth;

        vel.set(0,0);

        dead = false;

        myGun = null;

        return this;
    }

    public Player init(){
        return init(0,0);
    }






    public void ready(){
        bulletHolder = getRootNode().getChild("bulletHolder");

        addToGroup("rewind");

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.penguinTexture,0f,1f,0,0));
        lastChild().setName("sprite");
        addChild(ObjectPool.get(CollisionShape.class).init(8,12,0,0));

        //takeGun(PenguinRevolver.class);
        //addChild( ObjectPool.get(Raycast.class).init(0,0,100,-100,true,getMaskLayers(LayerNames.WALLS)) );
        //ray = (Raycast) lastChild();

    }

    private final Vector2 targetSpeed = new Vector2();

    public void update(double delta){

        targetSpeed.set(0.0f,0.0f);

        boolean onFloor = testMove(0,-1);

        if (!dead)
        {
            takeMovementInput(onFloor,delta);


            if (myGun != null){

                if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
                    bulletHolder.addChild(ObjectPool.get(myGun.throwClass).initThrow(globalPosition,Utils.getGlobalMousePosition()));
                    removeChild(myGun);
                    myGun = null;
                }

                else {
                    myGun.updateTimeUntilNextShot(delta);
                    myGun.aimAt(Utils.getGlobalMousePosition(), delta);
                    myGun.setFlip();

                    if (myGun.canShoot() && Gdx.input.isTouched()) {
                        for (GenericBullet bullet : myGun.shoot()) {
                            bulletHolder.addChild(bullet);
                        }
                        vel.add(ObjectPool.get(Vector2.class).set(-1,0).rotateRad((float) myGun.rotation).scl((float) myGun.recoil));
                    }
                }
            }

        }

        if (dead){
            beDead(delta);
        }
        else{
            ((TextureEntity) getChild("sprite")).setMyColor(Color.WHITE);
        }

        float tempVelY = vel.y;
        vel.set(moveAndSlide( vel,(float) delta) );
        if (signum(tempVelY) != signum(vel.y)) vel.y = 0;


        takeDebugInputs();
        Globals.cameraOffset.set(position);



        if (false && myRoot instanceof TimeRewindRoot && ((TimeRewindRoot) myRoot).isSaveFrame()) {
            //bulletHolder.addChild(ObjectPool.get(TimeParticle.class).init(ObjectPool.getGarbage(Vector2.class).set(0, 0), toRadians(2) * 60, rotation, 2, 0, ObjectPool.getGarbage(Vector2.class).set(0, 0), 0.1, 70, 0.8, true, Color.WHITE, Color.BLUE, 0.2, 0.2));
            bulletHolder.addChild(ObjectPool.get(TimeParticle.class).init(ObjectPool.getGarbage(Vector2.class).set(vel).scl(-1), toRadians(2) * 60, rotation, 1, 400, ObjectPool.getGarbage(Vector2.class).set(0, 0), 0.1, 0, 0.8, true, Color.WHITE, Color.RED, 3, 0.2));

            ((Particle) bulletHolder.lastChild()).setMaskLayers(getMaskLayers(LayerNames.WALLS), getMaskLayers());
            ((Particle) bulletHolder.lastChild()).position.set(position);
        }



    }

    public void takeGun(Class type){
        if (type == null || myGun != null) return;

        addChild( ((PenguinGun)ObjectPool.get(type)).init() );
        myGun = (PenguinGun) lastChild();
        myGun.aimAt(Utils.getGlobalMousePosition(),10);
    }

    public void takeGun(Class type,double rotation,int ammoLeft,double timeUntilNextShot){

        if (type == null) return;

        addChild( ((PenguinGun)ObjectPool.get(type)).init() );
        myGun = (PenguinGun) lastChild();
        myGun.setRotation(rotation);
        myGun.ammoLeft = ammoLeft;
        myGun.timeUntilNextShot = timeUntilNextShot;
    }

    public void beDead(double delta){
        rotation = 90;
        ((TextureEntity) getChild("sprite")).setRotation(rotation);
        ((TextureEntity) getChild("sprite")).setFlip(false,false);
        ((TextureEntity) getChild("sprite")).setMyColor(Color.RED);
        vel.x = lerp(vel.x,0,0.1f);
        vel.y -= GRAVITY * delta;
    }

    private float lerp(float a, float b, float f){
        return a + f * (b - a);
    }

    private double lerp(double a, double b, double f){
        return a + f * (b - a);
    }

    private void takeMovementInput(boolean onFloor,double delta){

        TextureEntity sprite = ((TextureEntity) getChild("sprite"));

        if (Gdx.input.isKeyPressed(Input.Keys.A)) targetSpeed.x -= MAXSPEED;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) targetSpeed.x += MAXSPEED;

        if (Gdx.input.isKeyPressed(Input.Keys.S)) vel.y -= 100;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && onFloor) vel.y = JUMPFORCE;


        vel.x  = (float) lerp(vel.x,targetSpeed.x, ACCEL * delta);


        if (vel.x != 0){
            sprite.setFlip(vel.x < 0,false);
        }

        if (!onFloor){
            //rotation = rotation % 360;

            double rotationChange = (4 + min(10,rotation * 0.01)) * 60 * delta;

            rotation += rotationChange;

            if (myGun != null){
                if (sprite.getFlipX()) {
                    myGun.rotation += toRadians(rotationChange);
                } else {
                    myGun.rotation -= toRadians(rotationChange);
                }
            }

        }
        else{
            rotation = rotation % 360;
            rotation += MathUtilsCustom.differenceBetweenAngles(Math.toRadians(rotation),0) * 30 * 60 * delta;
            rotation = MathUtilsCustom.moveTowardsZero(rotation,0.1);

        }


        if (sprite.getFlipX()) {
            sprite.setRotation(rotation);
        } else {
            sprite.setRotation(-rotation);
        }


        vel.y -= GRAVITY * delta;

    }

    public void takeDebugInputs(){


        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            SceneHandler.goToNextScene();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.V)){

            if (getRootNode().getChild("debugElf") == null){
                getRootNode().addChild(ObjectPool.get(MouseFollowSprite.class).init(TextureHolder.elfTexture, 0, 0, 0, 0));
                getRootNode().lastChild().setName("debugElf");
            }

            Vector2 tempValue = Utils.getGlobalMousePosition();

            tempValue.set(16 * (int) (tempValue.x/16),16 * (int) (tempValue.y/16));

            //add(poolGet(Elf.class).init(200,-180,ObjectPool.get(ElfRevolver.class)));

            System.out.println("add(poolGet(Elf.class).init(" + tempValue.x + "f , " + tempValue.y + "f ,ObjectPool.get(ElfRevolver.class)));" );
        }
    }

    public ArrayList<Object> save(){
        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add((ArrayList<Object>) ObjectPool.get(ArrayList.class)); //0
        ((ArrayList)returnArr.get(0)).clear();
        ((ArrayList)returnArr.get(0)).add(this.getClass());
        ((ArrayList)returnArr.get(0)).add(this.getParent());
        ((ArrayList)returnArr.get(0)).add(lastSave);

        returnArr.add(new Vector2(position)); //1
        returnArr.add(new Vector2(vel)); //2
        returnArr.add(rotation); //3
        returnArr.add(health); //4

        returnArr.add(getChild("sprite",TextureEntity.class).getFlipX()); //5
        returnArr.add(dead); //6

        if (myGun != null){

            returnArr.add(myGun.getClass()); //7
            returnArr.add((Double) myGun.rotation);//8
            returnArr.add(myGun.ammoLeft);//9
            returnArr.add(myGun.timeUntilNextShot); //10
        }
        else{
            returnArr.add(null);//7
            returnArr.add(null);
            returnArr.add(null);
            returnArr.add(null);//10
        }

        lastSave = returnArr;
        return returnArr;
    }

    public <T> T load(Object... vars) {

        this.position.set( (Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);
        this.rotation = ((double) vars[3]);

        health = (int) vars[4];

        TextureEntity sprite = ((TextureEntity) getChild("sprite"));

        sprite.setFlip((boolean) vars[5],false);

        if ((boolean) vars[5]) {
            sprite.setRotation(rotation);
        } else {
            sprite.setRotation(-rotation);
        }

        dead = (boolean) vars[6];

        if (dead) beDead(0.016);

        if (vars[7] != null){
            takeGun((Class) vars[7],(double)vars[8],(int)vars[9],(double) vars[10]);
        }

        updateGlobalPosition();
        Globals.cameraOffset.set(globalPosition);


        return null;
    }

    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }

    @Override


    public void render(SpriteBatch batch){
        //Globals.cameraOffset.set(globalPosition);
    }




    public void hit(Vector2 vel, float damage) {
        health -= damage;

        if (health <= 0){
            dead = true;
        }

    }
}
