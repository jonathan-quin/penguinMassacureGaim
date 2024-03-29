package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.elfGuns.ElfGun;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.floorGuns.FloorGun;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.MathUtilsCustom;
import com.mygdx.game.helpers.utilities.ParticleMaker;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.*;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.degreesToRadians;
import static com.badlogic.gdx.math.MathUtils.lerp;
import static com.mygdx.game.helpers.utilities.Utils.is_on_screen;
import static java.lang.Math.*;

public class Elf extends MovementNode implements TimeRewindInterface {

    protected Player player;

    public ElfGun myGun;

    protected Vector2 vel;

    protected int health;
    protected final int maxHealth = 100;

    protected  float wanderSpeed = 60;

    protected  float searchSpeed = 80;
    protected  float wanderAccel = 0.3f * 60;

    protected  double gravity = 400;

    protected boolean wanderJump = false;
    protected double jumpXVelocity = 0;

    protected  Vector2 lastPlayerPos;
    protected  Node bulletHolder;




    protected enum State {
        WANDER,
        CHASE,
        SEARCH,
        ATTACK,
        REPOSITION
    }

    protected enum Direction {
        LEFT,
        RIGHT
    }
    protected State state = State.WANDER;
    protected Direction direction = Direction.LEFT;

    public Elf(){
        super();
        vel = new Vector2(0,0);
        lastPlayerPos = new Vector2(0,0);
    }

    public void ready(){
        player = (Player) getRootNode().getChild("player");
        bulletHolder = getRootNode().getChild("bulletHolder");

        addToGroup("rewind");

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.elfTexture,0f,4f,0,0));
        lastChild().setName("sprite");

        if (myGun != null){
            addChild(ObjectPool.get(TextureEntity.class).init(myGun.tex,0f,1f,myGun.texOffset.x,myGun.texOffset.y));
            lastChild().setName("gunSprite");
        }

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.elfExclaim,0f,30f,0,0));
        lastChild().setName("exclaim");
        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.elfQuestion,0f,30f,0,0));
        lastChild().setName("question");


        addChild(ObjectPool.get(CollisionShape.class).init(8,12,0,0));

        addChild(ObjectPool.get(ColliderObject.class).init(350,0,getMaskLayers(LayerNames.PLAYER),getMaskLayers()));
        lastChild().setName("playerDetect");
        lastChild().addChild(ObjectPool.get(CollisionShape.class).init(400,200,0,0));
        ((CollisionShape)lastChild(). lastChild()).myColor = new Color(0,0,0.5f,0.1f);

        addChild( ObjectPool.get(Raycast.class).init(0,4,0,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("playerLOS");
        ((Raycast)lastChild()).makeDirtyOnUpdate = false;

        addChild( ObjectPool.get(Raycast.class).init(0,0,0,0,false,getMaskLayers(LayerNames.ELVES)) );
        lastChild().setName("elfCheck");
        ((Raycast)lastChild()).makeDirtyOnUpdate = false;

        addChild( ObjectPool.get(Raycast.class).init(0,36,0,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("playerLOS2");
        ((Raycast)lastChild()).makeDirtyOnUpdate = false;

        addChild( ObjectPool.get(Raycast.class).init(15,-30,0,-48,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightLedgeDetect");

        addChild( ObjectPool.get(Raycast.class).init(20,-24,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightGapJump");
        addChild( ObjectPool.get(Raycast.class).init(10,0,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightNoJump");
        addChild( ObjectPool.get(Raycast.class).init(10,32,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightHalfJump");
        addChild( ObjectPool.get(Raycast.class).init(10,64,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightFullJump");

        addChild( ObjectPool.get(Raycast.class).init(-15,-30,0,-48,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftLedgeDetect");

        addChild( ObjectPool.get(Raycast.class).init(-20,-24,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftGapJump");
        addChild( ObjectPool.get(Raycast.class).init(-10,0,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftNoJump");
        addChild( ObjectPool.get(Raycast.class).init(-10,32,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftHalfJump");
        addChild( ObjectPool.get(Raycast.class).init(-10,64,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftFullJump");

        addChild( ObjectPool.get(Raycast.class).init(0,16,0,20,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("roofCast");

        addChild( ObjectPool.get(Raycast.class).init(0,0,0,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("randCast");
        ((Raycast)lastChild()).makeDirtyOnUpdate = false;


        getChild("rightLedgeDetect",Raycast.class).makeDirtyOnUpdate = false;
        getChild("rightNoJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("rightHalfJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("rightFullJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftLedgeDetect",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftNoJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftHalfJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftFullJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("roofCast",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftGapJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("rightGapJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("elfCheck",Raycast.class).makeDirtyOnUpdate = false;

        //ray = (Raycast) getNewestChild();


    }

    boolean repositionJump = false;

    @Override
    public ArrayList<Object> save() {
        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add((ArrayList<Object>) ObjectPool.get(ArrayList.class)); //0
        ((ArrayList)returnArr.get(0)).clear();
        ((ArrayList)returnArr.get(0)).add(this.getClass());
        ((ArrayList)returnArr.get(0)).add(this.getParent());
        ((ArrayList)returnArr.get(0)).add(lastSave);

        returnArr.add(new Vector2(position)); //1
        returnArr.add(new Vector2(vel)); //2
        returnArr.add(myGun.getClass()); //3

        returnArr.add(getChild("sprite",TextureEntity.class).getFlipX()); //4

        returnArr.add(direction == Direction.LEFT); //5


        returnArr.add(new Vector2(lastPlayerPos)); //6
        returnArr.add(state); //7

        returnArr.add(myGun.timeUntilNextShot); //8
        returnArr.add((Double) myGun.rotation); //9
        returnArr.add((Integer)health); // 10

        returnArr.add(repositionJump);
        returnArr.add(wanderJump);
        returnArr.add(jumpXVelocity);

        lastSave = returnArr;
        return returnArr;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }

    @Override
    public <T> T load(Object... vars) {
        this.position.set( (Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);

        myGun = (ElfGun) ObjectPool.get( (Class) vars[3]);
        myGun.init();

        addChild(ObjectPool.get(TextureEntity.class).init(myGun.tex,0f,1f,myGun.texOffset.x,myGun.texOffset.y));
        lastChild().setName("gunSprite");

        getChild("sprite",TextureEntity.class).setFlip((boolean) vars[4],false);

        direction = (boolean) vars[5] ? direction.LEFT : direction.RIGHT;

        lastPlayerPos.set((Vector2) vars[6]);
        state = (State) vars[7];

        myGun.timeUntilNextShot = (double) vars[8];
        myGun.rotation = (double) vars[9];

        health = (int) vars[10];


        getChild("gunSprite",TextureEntity.class).setRotation( toDegrees(myGun.rotation));
        getChild("gunSprite",TextureEntity.class).setFlip(false, myGun.shouldFlipY());

        switch (state){
            case WANDER:
                getChild("exclaim",TextureEntity.class).setVisible(false);
                getChild("question",TextureEntity.class).setVisible(false);
                break;
            case SEARCH:
                getChild("exclaim",TextureEntity.class).setVisible(false);
                getChild("question",TextureEntity.class).setVisible(true);
                break;
            case ATTACK:
            case REPOSITION:
            case CHASE:
                getChild("exclaim",TextureEntity.class).setVisible(true);
                getChild("question",TextureEntity.class).setVisible(false);
                break;
        }

        repositionJump = (boolean) vars[11];
        wanderJump = (boolean) vars[12];
        jumpXVelocity = (double) vars[13];


        return null;
    }

    @Override
    public Elf init() {

        init(0,0,null,false);
        return this;
    }

    public Elf init(float x, float y, ElfGun gun, boolean facingLeft){
        super.init(x,y,getMaskLayers(LayerNames.WALLS),getMaskLayers(LayerNames.ELVES));

        this.myGun = gun;
        if (gun != null) gun.init();

        health = maxHealth;
        vel.set(0,0);

        lastSave = null;

        if (facingLeft) direction = Direction.LEFT;
        else direction = Direction.RIGHT;

        if (gun != null){
            if (facingLeft) {
                gun.rotation = -PI / 2 - 30 * degreesToRadians;
            } else {
                gun.rotation = -PI / 2 + 30 * degreesToRadians;
            }
        }

        state = State.WANDER;

        repositionJump = false;
        wanderJump = false;

        return this;
    }




    public void update(double delta){

        getChild("rightLedgeDetect",Raycast.class).dirty = true;
        getChild("rightNoJump",Raycast.class).dirty = true;
        getChild("rightHalfJump",Raycast.class).dirty = true;
        getChild("rightFullJump",Raycast.class).dirty = true;
        getChild("leftLedgeDetect",Raycast.class).dirty = true;
        getChild("leftNoJump",Raycast.class).dirty = true;
        getChild("leftHalfJump",Raycast.class).dirty = true;
        getChild("leftFullJump",Raycast.class).dirty = true;
        getChild("roofCast",Raycast.class).dirty = true;
        getChild("leftGapJump",Raycast.class).dirty = true;
        getChild("rightGapJump",Raycast.class).dirty = true;
        getChild("elfCheck",Raycast.class).dirty = true;

        vel.y -= gravity * delta;
        boolean onFloor = testMove(0,-1);

        if (onFloor){
            repositionJump = false;
            wanderJump = false;
        }


        getChild("gunSprite",TextureEntity.class).setRotation(toDegrees(myGun.rotation));
        getChild("gunSprite",TextureEntity.class).setFlip(false, myGun.shouldFlipY());

        myGun.updateTimeUntilNextShot(delta);

        switch (state){
            case WANDER:

                Vector2 targetVel = ObjectPool.getGarbage(Vector2.class).set(0,0);

                myGun.aimAt(ObjectPool.getGarbage(Vector2.class).set(0,0),ObjectPool.getGarbage(Vector2.class).set(vel).scl(5),delta);

                getChild("exclaim",TextureEntity.class).setVisible(false);
                getChild("question",TextureEntity.class).setVisible(false);
                
                turnAroundOrJumpV2Wander(onFloor);
                
               

                targetVel.x = (direction == Direction.LEFT) ? -wanderSpeed : wanderSpeed;


                vel.x = lerp(vel.x,targetVel.x, (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );


                if (canSeePlayer()){
                    state = State.CHASE;
                }

                break;

            case CHASE:

                myGun.aimAt(globalPosition,player.globalPosition,delta);

                getChild("exclaim",TextureEntity.class).setVisible(true);
                getChild("question",TextureEntity.class).setVisible(false);

                double playerDistSQR = globalPosition.dst2((player.globalPosition));

                boolean away = (playerDistSQR) < Math.pow(myGun.distanceMin,2);

                double targetX = moveTowardsPoint(player.globalPosition,onFloor,away);

                vel.x = lerp(vel.x, (float) (targetX * myGun.moveSpeed), (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );

                lastPlayerPos.set( player.globalPosition);

                if (!canSeePlayer()){
                    state = State.SEARCH;
                }

                if (playerDistSQR < Math.pow(myGun.distanceMax, 2)){
                    state = State.ATTACK;
                }



                break;

            case SEARCH:

                getChild("exclaim",TextureEntity.class).setVisible(false);
                getChild("question",TextureEntity.class).setVisible(true);

                double targetX2 = moveTowardsPoint(lastPlayerPos,onFloor,false);



                vel.x = lerp(vel.x, (float) (targetX2 * searchSpeed), (float) (wanderAccel * delta));



                vel.set( moveAndSlide(vel,delta) );

                if (canSeePlayer()){
                    state = State.CHASE;
                }

                if (!repositionJump && (MathUtilsCustom.isEqualApprox(vel.x,0,0.1) || MathUtilsCustom.isEqualApprox(globalPosition.x,lastPlayerPos.x,4))){
                    state = State.WANDER;
                }

                break;
            case ATTACK:

                getChild("sprite",TextureEntity.class).setFlip((player.globalPosition.x > globalPosition.x),false);

                if (!hasStraightShot()){
                    state = State.REPOSITION;
                    break;
                }

                getChild("exclaim",TextureEntity.class).setVisible(true);
                getChild("question",TextureEntity.class).setVisible(false);

                myGun.aimAt(globalPosition,player.globalPosition,delta);

                if (myGun.canShoot(globalPosition,player.globalPosition)){

                    for (GenericBullet bullet : myGun.shoot(ObjectPool.getGarbage(Vector2.class).set(globalPosition).sub(0,-2))){
                        bulletHolder.addChild(bullet);
                    }

                    if (myGun.getClass().equals(ElfMiniGun.class)){
                        playSound(Globals.Sounds.MINIGUNSHOOT);
                    }
                    if (myGun.getClass().equals(ElfShotgun.class)){
                        playSound(Globals.Sounds.SHOTGUNSHOOT);
                    }
                    if (myGun.getClass().equals(ElfRevolver.class)){
                        playSound(Globals.Sounds.REVOLVERSHOOT);
                    }

                }

                if (!canSeePlayer()){
                    state = State.SEARCH;
                }

                double playerDistance = globalPosition.dst2((player.globalPosition));

                boolean tooClose = (playerDistance) < Math.pow(myGun.distanceMin,2);

                double targetSpeedX = 0;
                if (tooClose) targetSpeedX = moveTowardsPoint(player.globalPosition,onFloor,tooClose);

                if (!onFloor && willJumpOffLedge((float) targetSpeedX)) targetSpeedX = 0;

                vel.x = lerp(vel.x, (float) (targetSpeedX * myGun.moveSpeed), (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );


                break;

            case REPOSITION:

                getChild("sprite",TextureEntity.class).setFlip((player.globalPosition.x > globalPosition.x),false);

                getChild("exclaim",TextureEntity.class).setVisible(true);
                getChild("question",TextureEntity.class).setVisible(false);

                myGun.aimAt(globalPosition,player.globalPosition,delta);

               if (onFloor){
                   repositionJump = true;
                   jump(48);
               }

                double playerDistance2 = globalPosition.dst2((player.globalPosition));

                boolean tooClose2 = (playerDistance2) < Math.pow(myGun.distanceMin,2);

                double targetSpeedX2 = 0;
                if (tooClose2) targetSpeedX = moveTowardsPoint(player.globalPosition,onFloor,tooClose2);

                if (!onFloor && willJumpOffLedge((float) targetSpeedX2)) targetSpeedX2 = 0;

                vel.x = lerp(vel.x, (float) (targetSpeedX2 * myGun.moveSpeed), (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );

                if (hasStraightShot()){
                    state = State.ATTACK;
                }

                if (!canSeePlayer()){
                    state = State.SEARCH;
                }


                break;




        }



    }

    public boolean hasStraightShot(){

        Raycast elfCheck = getChild("elfCheck",Raycast.class);
        elfCheck.dirty = true;
        Raycast randCast = getChild("randCast",Raycast.class);
        randCast.dirty = true;

        Vector2 playerDir = ObjectPool.getGarbage(Vector2.class).set(player.globalPosition).sub(globalPosition).scl(1,1).nor();

        elfCheck.position .set( ObjectPool.getGarbage(Vector2.class).set(playerDir).scl(20));
        elfCheck.setCast(ObjectPool.getGarbage(Vector2.class).set(playerDir).scl(globalPosition.dst(player.globalPosition)-20));

        randCast.setCast(ObjectPool.getGarbage(Vector2.class).set(player.globalPosition).sub(globalPosition));
        if (randCast.isColliding()){
            return false;
        }


        if (elfCheck.isColliding()) return false;
        elfCheck.dirty = true;

        elfCheck.position.y += 10;
        if (elfCheck.isColliding()) return false;
        elfCheck.dirty = true;

        elfCheck.position.y += 30;
        if (elfCheck.isColliding()) return false;
        elfCheck.dirty = true;

        return true;

    }

    public boolean canSeePlayer(){

        if (Globals.aiIgnore) return false;

        if (state == State.WANDER) {
            switch (direction) {
                case LEFT:
                    getChild("playerDetect").position.x = -350;
                    break;
                case RIGHT:
                    getChild("playerDetect").position.x = 350;
                    break;
            }
        }else{
            getChild("playerDetect").position.x = (globalPosition.x < player.globalPosition.x) ? 350 : - 350;
        }

        getChild("playerDetect",ColliderObject.class).parentPosition.set(globalPosition);
        getChild("playerDetect",ColliderObject.class).updateParentPos();
        getChild("playerDetect",ColliderObject.class).getFirstCollision(ObjectPool.getGarbage(Vector2.class).set(0,0));

        boolean playerInSightBox = getChild("playerDetect",ColliderObject.class).lastCollided;


        if (!playerInSightBox) return false;

        Raycast ray = (Raycast) getChild("playerLOS");
        ray.dirty = true;

        Raycast ray2 = (Raycast) getChild("playerLOS2");
        ray2.dirty = true;




        ray.setCast(player.globalPosition.x - globalPosition.x,player.globalPosition.y - globalPosition.y);

        ray2.setCast(player.globalPosition.x - globalPosition.x,player.globalPosition.y - globalPosition.y - 24);


        return !ray.isColliding() || !ray2.isColliding();

    }

    public void jump(double height){

        vel.y = (float) Math.sqrt(abs(-2 * gravity * height));

        if (is_on_screen(position,20,20)){
            playSound(Globals.Sounds.JUMP);
            System.out.println(this);
            System.out.println(position);
            System.out.println(myGun.getClass());
            System.out.println(state);
        }


        if (state == State.WANDER){
            wanderJump = true;

            switch (direction){
                case RIGHT:
                    jumpXVelocity = wanderSpeed;
                    break;
                case LEFT:
                    jumpXVelocity = -wanderSpeed;
                    break;
            }


        }

    }

    public void turnAroundOrJumpV1(boolean onFloor){
        switch (direction){
            case RIGHT:

                getChild("sprite",TextureEntity.class).setFlip(true,false);


                if (onFloor){

                    if (!getChild("rightLedgeDetect",Raycast.class).isColliding()){
                        direction = Direction.LEFT;
                        if (!getChild("leftNoJump",Raycast.class).isColliding()){
                        }
                        else if (!getChild("leftHalfJump",Raycast.class).isColliding()){
                            jump(36);
                        }
                        else if (!getChild("leftFullJump",Raycast.class).isColliding()){
                            jump(70);
                        }
                        else{
                            direction = Direction.RIGHT;
                        }
                    }

                    else if (!getChild("rightNoJump",Raycast.class).isColliding()){
                    }
                    else if (!getChild("rightHalfJump",Raycast.class).isColliding()){
                        jump(36);
                    }
                    else if (!getChild("rightFullJump",Raycast.class).isColliding()){
                        jump(70);
                    }
                    else{
                        direction = Direction.LEFT;
                    }

                }

                break;

            case LEFT:

                getChild("sprite",TextureEntity.class).setFlip(false,false);



                if (onFloor){

                    if (!getChild("leftLedgeDetect",Raycast.class).isColliding()){
                        direction = Direction.RIGHT;

                        if (!getChild("rightNoJump",Raycast.class).isColliding()){
                        }
                        else if (!getChild("rightHalfJump",Raycast.class).isColliding()){
                            jump(36);
                        }
                        else if (!getChild("rightFullJump",Raycast.class).isColliding()){
                            jump(70);
                        }
                        else{
                            direction = Direction.LEFT;
                        }
                    }

                    else if (!getChild("leftNoJump",Raycast.class).isColliding()){
                    }
                    else if (!getChild("leftHalfJump",Raycast.class).isColliding()){
                        jump(36);
                    }
                    else if (!getChild("leftFullJump",Raycast.class).isColliding()){
                        jump(70);
                    }
                    else{
                        direction = Direction.RIGHT;
                    }

                }

                break;

        }
    }

    public void turnAroundOrJumpV2Wander(boolean onFloor){

        if (!onFloor && repositionJump){
           vel.x = 0;
            return;
        }
        if (!onFloor && wanderJump){
            vel.x = (float) jumpXVelocity;
        }

        Raycast rightLedge = getChild("rightLedgeDetect",Raycast.class);
        Raycast rightGap = getChild("rightGapJump",Raycast.class);
        Raycast rightNoJump = getChild("rightNoJump",Raycast.class);
        Raycast rightHalfJump = getChild("rightHalfJump",Raycast.class);
        Raycast rightFullJump = getChild("rightFullJump",Raycast.class);

        Raycast leftLedge = getChild("leftLedgeDetect",Raycast.class);
        Raycast leftGap = getChild("leftGapJump",Raycast.class);
        Raycast leftNoJump = getChild("leftNoJump",Raycast.class);
        Raycast leftHalfJump = getChild("leftHalfJump",Raycast.class);
        Raycast leftFullJump = getChild("leftFullJump",Raycast.class);

        Raycast roofCast = getChild("roofCast",Raycast.class);

        Raycast randCast = getChild("randCast",Raycast.class);
        randCast.dirty = true;
        
        switch (direction){
            case RIGHT:

                getChild("sprite",TextureEntity.class).setFlip(true,false);
                roofCast.setCast(12,roofCast.castTo.y);


                if (onFloor) {

                    if (!rightLedge.isColliding()){
                        randCast.setCast(50,0);
                        if (!roofCast.isColliding() && rightGap.isColliding() && !randCast.isColliding()){
                           jump(40);
                        }
                        else{
                            if (!leftNoJump.isColliding() || !leftHalfJump.isColliding() || !leftFullJump.isColliding())
                            direction = Direction.LEFT;
                        }

                    }
                    else{
                        if (rightNoJump.isColliding()){
                            jump(36);
                            if (rightHalfJump.isColliding()){
                                jump(70);
                                if (rightFullJump.isColliding()){
                                    direction = Direction.LEFT;
                                    jump(0);
                                }
                            }
                        }
                        else if (rightHalfJump.isColliding() && !rightFullJump.isColliding() && !roofCast.isColliding()){
                            jump(70);
                        }

                    }


                }

                break;

            case LEFT:

                getChild("sprite",TextureEntity.class).setFlip(false,false);
                roofCast.setCast(-12,roofCast.castTo.y);

                if (onFloor) {

                    if (!leftLedge.isColliding()){
                        if (!leftLedge.isColliding()){
                            randCast.setCast(-50,0);
                            if (!roofCast.isColliding() && leftGap.isColliding() && !randCast.isColliding()){
                                jump(40);
                            }
                            else{
                                if (!rightNoJump.isColliding() || !rightHalfJump.isColliding() || !rightFullJump.isColliding())
                                    direction = Direction.RIGHT;
                            }

                        }

                    }
                    else{
                        if (leftNoJump.isColliding()){
                            jump(36);
                            if (leftHalfJump.isColliding()){
                                jump(70);
                                if (leftFullJump.isColliding()){
                                    direction = Direction.RIGHT;
                                    jump(0);
                                }
                            }
                        }
                        else if (leftHalfJump.isColliding() && !leftFullJump.isColliding() && !roofCast.isColliding()){
                            jump(70);
                        }

                    }


                }

                break;

        }
    }
    
    /*
    return speed is 1 to -1
     */
    public double moveTowardsPoint(Vector2 point, boolean onFloor, boolean away){

        if (!onFloor && repositionJump){

            return 0;

        }

        if (!onFloor && wanderJump){
            vel.x = (float) jumpXVelocity;
        }


        Raycast rightLedge = getChild("rightLedgeDetect",Raycast.class);
        Raycast rightGap = getChild("rightGapJump",Raycast.class);
        Raycast rightNoJump = getChild("rightNoJump",Raycast.class);
        Raycast rightHalfJump = getChild("rightHalfJump",Raycast.class);
        Raycast rightFullJump = getChild("rightFullJump",Raycast.class);

        Raycast leftLedge = getChild("leftLedgeDetect",Raycast.class);
        Raycast leftGap = getChild("leftGapJump",Raycast.class);
        Raycast leftNoJump = getChild("leftNoJump",Raycast.class);
        Raycast leftHalfJump = getChild("leftHalfJump",Raycast.class);
        Raycast leftFullJump = getChild("leftFullJump",Raycast.class);

        Raycast roofCast = getChild("roofCast",Raycast.class);

        Raycast randCast = getChild("randCast",Raycast.class);
        randCast.dirty = true;
        
        direction =  (point.x < globalPosition.x) ? Direction.LEFT : Direction.RIGHT;

        
        if (away){
            direction = (direction == Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
        }
        
        boolean prioritizeUp = point.y >= globalPosition.y + 47;
        
        double targetSpeed = (direction == Direction.LEFT) ? -1 : 1;;

        switch (direction) {
            case RIGHT:

                roofCast.setCast(12, roofCast.castTo.y);


                if (onFloor) {

                    if (!rightLedge.isColliding()) {
                        randCast.setCast(50, 0);
                        if (!roofCast.isColliding() && rightGap.isColliding() && !randCast.isColliding()) {
                            jump(40);
                            targetSpeed = 1;
                        } else {
                            targetSpeed = 0;
                        }

                    } else {
                        targetSpeed = 1;
                        if (rightNoJump.isColliding()) {
                            jump(36);
                            if (rightHalfJump.isColliding()) {
                                jump(70);
                                if (rightFullJump.isColliding()) {
                                    targetSpeed = 0;
                                    jump(0);
                                }
                            }
                        } else if (prioritizeUp && rightHalfJump.isColliding() && !rightFullJump.isColliding() && !roofCast.isColliding()) {
                            jump(70);
                        }

                    }


                }

                break;

            case LEFT:

                roofCast.setCast(12, roofCast.castTo.y);


                if (onFloor) {

                    if (!leftLedge.isColliding()) {
                        randCast.setCast(50, 0);
                        if (!roofCast.isColliding() && leftGap.isColliding() && !randCast.isColliding()) {
                            jump(40);
                            targetSpeed = -1;
                        } else {
                            targetSpeed = 0;
                        }

                    } else {
                        targetSpeed = -1;
                        if (leftNoJump.isColliding()) {
                            jump(36);
                            if (leftHalfJump.isColliding()) {
                                jump(70);
                                if (leftFullJump.isColliding()) {
                                    targetSpeed = 0;
                                    jump(0);
                                }
                            }
                        } else if (prioritizeUp && leftHalfJump.isColliding() && !leftFullJump.isColliding() && !roofCast.isColliding()) {
                            jump(70);
                        }

                    }


                }

                break;

        }
        
        return targetSpeed;
        
    }

    public boolean willJumpOffLedge(float speed){
        Raycast leftLedge = getChild("leftLedgeDetect",Raycast.class);
        Raycast rightLedge = getChild("rightLedgeDetect",Raycast.class);

        if (speed < 0 && !leftLedge.isColliding()) return true;
        if (speed > 0 && !rightLedge.isColliding()) return true;

        return false;
    }

    public void hit(Vector2 bulletVel, float damage) {

        if (health <= 0) return;

        health -= damage;
        if (health <= 0){
            die(bulletVel);
        }

    }

    public void die(Vector2 bulletVel) {

        queueFree();

        playSound(Globals.Sounds.ELFDIE);

        bulletHolder.addChild(((FloorGun) ObjectPool.get(myGun.floorClass)).init(position.x,position.y,this.vel.x,this.vel.y));

        for (TimeParticle t : ParticleMaker.makeBloodyParticlesFromSprite(getChild("sprite",TextureEntity.class),bulletVel)){
            bulletHolder.addChild(t);
        }

    }

    @Override
    public void free() {
        super.free();
        ObjectPool.removeBackwards(myGun);
    }
    
}
