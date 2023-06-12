package com.mygdx.game.entities;

import static java.lang.Math.min;
import static java.lang.Math.signum;
import static java.lang.Math.toRadians;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.elfGuns.thrownGuns.ThrownGun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinGun;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.*;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.GroupHandler;
import com.mygdx.game.nodes.MovementNode;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Particle;
import com.mygdx.game.nodes.Raycast;
import com.mygdx.game.nodes.TextureEntity;
import com.mygdx.game.nodes.TimeRewindRoot;

public class Player extends MovementNode implements TimeRewindInterface {

    private final float MAXSPEED = 180;

    private float speed = 5;
    private Vector2 vel;

    private int health;

    private final int maxHealth = 100;

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

        lastSave = null;

        return this;
    }

    public Player init(){
        return init(0,0);
    }






    public void ready(){
        bulletHolder = getRootNode().getChild("bulletHolder");

        addToGroup("rewind");
        addToGroup("player");

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.penguinTexture,0f,1f,0,0));
        lastChild().setName("sprite");
        addChild(ObjectPool.get(CollisionShape.class).init(8,12,0,0));

        addChild(ObjectPool.get(AmmoCounter.class).init(320,178));
        lastChild().setName("counter");

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.Text.lForLobby,250,100,0,0));
        ((TextureEntity) lastChild()).setScale(1f,1f);
        lastChild().setName("lForLobby");
        lastChild().addToGroup(GroupHandler.RENDERONTOP);

        addChild(ObjectPool.get(ThrowPathIndicator.class).init(0,0));
        lastChild().setName("throwPath");
        ((ThrowPathIndicator) lastChild()).setVisible(false);


        addChild(ObjectPool.get(Label.class).init((float) (5), (float) (595)));
        lastChild().addToGroup(GroupHandler.RENDERONTOP);
        lastChild().addToGroup("debugLabel");
        debugLabel = (Label) lastChild();

        String str = myRoot.audio.debugString;
        debugLabel.setText(str);


        //((TextureEntity) lastChild()).setVisible(false);

        //if (bulletHolder.getChild(""))


        //takeGun(PenguinRevolver.class);
        //addChild( ObjectPool.get(Raycast.class).init(0,0,100,-100,true,getMaskLayers(LayerNames.WALLS)) );
        //ray = (Raycast) lastChild();

    }

    public Label debugLabel;

    private final Vector2 targetSpeed = new Vector2();

    public void update(double delta){


        String str = myRoot.audio.debugString;
        //str = "";
        debugLabel.setText(str);

        //delta *= 2;
        getChild("lForLobby", TextureEntity.class).setVisible(false);

        targetSpeed.set(0.0f,0.0f);

        boolean onFloor = testMove(0,-1);

        if (!dead)
        {
            takeMovementInput(onFloor,delta);


            if (myGun != null && !Globals.sceneJustChanged){

                ThrowPathIndicator pathIndicator = getChild("throwPath", ThrowPathIndicator.class);

                if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.F)){

                    pathIndicator.setVisible(true);

                    ThrownGun tempThrownGun = ObjectPool.getGarbage(myGun.throwClass);

                    pathIndicator.display(tempThrownGun.getInitStart(globalPosition,Utils.getGlobalMousePosition()).sub(position),tempThrownGun.getInitVel(globalPosition,Utils.getGlobalMousePosition(),vel), GRAVITY);

                } else if(pathIndicator.visible){
                    pathIndicator.setVisible(false);
                    playSound(Globals.Sounds.JUMP);

                    bulletHolder.addChild(ObjectPool.get(myGun.throwClass).initThrow(globalPosition,Utils.getGlobalMousePosition(),vel));
                    removeChild(myGun);
                    myGun = null;
                }

                else {
                    myGun.updateTimeUntilNextShot(delta);
                    myGun.aimAt(Utils.getGlobalMousePosition(), delta);
                    myGun.setFlip();

                    if (myGun.canShoot() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                        for (GenericBullet bullet : myGun.shoot()) {
                            bulletHolder.addChild(bullet);
                        }
                        vel.add(ObjectPool.getGarbage(Vector2.class).set(-1,0).rotateRad((float) myGun.rotation).scl((float) myGun.recoil));
                    }
                }

                if (myGun != null){
                    getChild("counter", AmmoCounter.class).display(myGun.ammoLeft,myGun.timeUntilNextShot,1d/myGun.fireRate);
                }else{
                    getChild("counter", AmmoCounter.class).display(0,1,1);
                }

            } else{

                getChild("counter", AmmoCounter.class).display(0,1,1);

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            Globals.ultraSlow = !Globals.ultraSlow;
        }


        takeDebugInputs();
        Globals.cameraOffset.set(position);
        //System.out.println(Globals.cameraOffset);


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
        myGun.setFlip();
    }

    public void beDead(double delta){
        rotation = 90;

        ((TextureEntity) getChild("sprite")).setMyColor(new Color(0,0,0,0));

        removeChild(myGun);
        myGun = null;

        double newSpeed = Globals.gameSpeed/1.04;
        if (Globals.gameSpeed < 0.01){
            newSpeed = 0;
        }

        if (SceneHandler.getCurrentRoot() instanceof TimeRewindRoot )
        ((TimeRewindRoot)SceneHandler.getCurrentRoot()).setNextGameSpeed(newSpeed);

        getChild("counter", AmmoCounter.class).display(0,1,1);

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

        if (Gdx.input.isKeyPressed(Input.Keys.S)) vel.y -= 10;

        if ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) && onFloor){
            playSound(Globals.Sounds.JUMP);
            vel.y = JUMPFORCE;
        }


        vel.x  = (float) lerp(vel.x,targetSpeed.x, ACCEL * delta);


        if (vel.x != 0){
            sprite.setFlip(vel.x < 0,false);
        }

        if (!onFloor){
            //rotation = rotation % 360;

            double rotationChange = (4 + min(10,rotation * 0.01)) * 60 * delta;

            rotation += rotationChange;



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

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            ObjectPool.printTextureEntityParents();
        }


        double speed = 1.4;

//        if (Gdx.input.isKeyJustPressed(Input.Keys.G)){
//            SoundAccelerator.playAtSpeedV2(speed,Globals.timeRewindSounds.get(Globals.Sounds.BULLETSOFT));
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.H)){
//            SoundAccelerator.playAtSpeedV1(speed,Globals.timeRewindSounds.get(Globals.Sounds.BULLETSOFT));
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)){
            Globals.showCollision = !Globals.showCollision;

            if (Globals.showCollision){
                Globals.camera.setToOrtho(false, (float) (Globals.screenSize.x), (float) (Globals.screenSize.y));
            }
            else{
                Globals.camera.setToOrtho(false, (float) (Globals.screenSize.x * 0.65), (float) (Globals.screenSize.y * 0.65));

            }

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)){
            Globals.aiIgnore = !Globals.aiIgnore;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)){
            health += 1000000;
        }



        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            System.out.println(position);
            System.out.println(globalPosition);
            System.out.println(getParent().position);
            System.out.println(getParent());
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

        returnArr.add(getChild("sprite",TextureEntity.class).getFlipY()); //11

        lastSave = returnArr;
        return returnArr;
    }

    public <T> T load(Object... vars) {

        this.position.set( (Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);
        this.rotation = ((double) vars[3]);

        health = (int) vars[4];

        TextureEntity sprite = ((TextureEntity) getChild("sprite"));

        sprite.setFlip((boolean) vars[5],(boolean) vars[11]);

        if ((boolean) vars[5]) {
            sprite.setRotation(rotation);
        } else {
            sprite.setRotation(-rotation);
        }

        dead = (boolean) vars[6];

        if (dead) beDead(0.016);

        if (vars[7] != null){
            takeGun((Class) vars[7],(double)vars[8],(int)vars[9],(double) vars[10]);
            getChild("counter", AmmoCounter.class).display(myGun.ammoLeft,myGun.timeUntilNextShot,1d/myGun.fireRate);
        }
        else{
            getChild("counter", AmmoCounter.class).display(0,1,1);
        }

        updateGlobalPosition();
        Globals.cameraOffset.set(globalPosition);

        if (Globals.timeRootStage != 0){
            getChild("lForLobby", TextureEntity.class).setVisible(true);
        }
        else{
            getChild("lForLobby", TextureEntity.class).setVisible(false);
        }

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




    public void hit(Vector2 bulletVel, float damage) {

        if (health <= 0) return;

        health -= damage;
        if (health <= 0){
            die(bulletVel);
        }

    }

    public void die(Vector2 bulletVel) {

        dead = true;
        //lastSave.set(6,true);
        for (TimeParticle t : ParticleMaker.makeBloodyParticlesFromSprite(getChild("sprite",TextureEntity.class),bulletVel)){
            bulletHolder.addChild(t);
        }

        if (myGun != null) {
            for (TimeParticle t : ParticleMaker.makeDisapearingParticles(myGun.getChild("gunSprite", TextureEntity.class), bulletVel)) {
                bulletHolder.addChild(t);
            }
        }

    }


}
