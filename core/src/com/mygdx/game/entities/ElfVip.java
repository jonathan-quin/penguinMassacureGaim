package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.elfGuns.ElfGun;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.MathUtilsCustom;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.*;

import static com.badlogic.gdx.math.MathUtils.lerp;
import static java.lang.Math.abs;
import static java.lang.Math.toDegrees;

public class ElfVip extends Elf implements TimeRewindInterface {



    public ElfVip(){
        super();

    }



    @Override
    public ElfVip init() {

        init(0,0,null,false);
        return this;
    }

    public ElfVip init(float x, float y, ElfGun gun,boolean facingLeft){
        super.init(x,y,gun,facingLeft);


        return this;
    }

    public void ready(){
        player = (Player) getRootNode().getChild("player");
        bulletHolder = getRootNode().getChild("bulletHolder");

        addToGroup("rewind");
        addToGroup("VIP");

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.elfVipTexture,0f,4f,0,0));
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

        addChild( ObjectPool.get(Raycast.class).init(0,0,0,0,false,getMaskLayers(LayerNames.ELVES)) );
        lastChild().setName("elfCheck");
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

                boolean away = true;

                double targetX = moveTowardsPoint(player.globalPosition,onFloor,away);

                vel.x = lerp(vel.x, (float) (targetX * myGun.moveSpeed), (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );

                lastPlayerPos.set( player.globalPosition);

                if (!canSeePlayer()){
                    state = State.SEARCH;
                }

                if (playerDistSQR < Math.pow(myGun.distanceMax * 1.2, 2)){
                    state = State.ATTACK;
                }



                break;

            case SEARCH: {

                getChild("exclaim", TextureEntity.class).setVisible(false);
                getChild("question", TextureEntity.class).setVisible(true);

                double targetX2;

                if (onFloor) {
                    targetX2 = moveTowardsPoint(lastPlayerPos, onFloor, true);
                } else {
                    targetX2 = 0;
                }

                Raycast randCast = getChild("randCast", Raycast.class);
                randCast.dirty = true;
                randCast.setCast(0, -64);
                if (!onFloor && !randCast.isColliding()) vel.x *= 0.2 * 60 * delta;


                vel.x = lerp(vel.x, (float) (targetX2 * searchSpeed), (float) (wanderAccel * delta));

                vel.set(moveAndSlide(vel, delta));

                if (canSeePlayer()) {
                    state = State.CHASE;
                }

                if (!repositionJump && (MathUtilsCustom.isEqualApprox(vel.x, 0, 0.1) || MathUtilsCustom.isEqualApprox(globalPosition.x, lastPlayerPos.x, 4))) {
                    state = State.WANDER;
                }

                break;
            }
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

                }

                if (!canSeePlayer()){
                    state = State.SEARCH;

                    Raycast randCast = getChild("randCast",Raycast.class);
                    randCast.dirty = true;
                    randCast.setCast(0,-64);
                    if (!onFloor && randCast.isColliding()) vel.x *= 0.2;

                }

                double playerDistance = globalPosition.dst2((player.globalPosition));

                boolean tooClose = (playerDistance) < Math.pow(myGun.distanceMin,2);

                double targetSpeedX = 0;
                if (tooClose) targetSpeedX = moveTowardsPoint(player.globalPosition,onFloor,tooClose);

                if (!onFloor && willJumpOffLedge((float) targetSpeedX)){
                    targetSpeedX = 0;
                    vel.x = lerp(vel.x, (float) 0, (float) (0.2 * 60 * delta));
                }

                vel.x = lerp(vel.x, (float) (targetSpeedX * myGun.moveSpeed), (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );


                break;

            case REPOSITION:

                getChild("sprite",TextureEntity.class).setFlip((player.globalPosition.x > globalPosition.x),false);

                getChild("exclaim",TextureEntity.class).setVisible(true);
                getChild("question",TextureEntity.class).setVisible(false);

                myGun.aimAt(globalPosition,player.globalPosition,delta);

                if (onFloor) {
                    jump(48);
                    repositionJump = true;
                }

                double playerDistance2 = globalPosition.dst2((player.globalPosition));

                boolean tooClose2 = (playerDistance2) < Math.pow(myGun.distanceMin,2);

                double targetSpeedX2 = 0;
                if (tooClose2) targetSpeedX = moveTowardsPoint(player.globalPosition,onFloor,tooClose2);

                if (!onFloor && willJumpOffLedge((float) targetSpeedX2)){
                    targetSpeedX = 0;
                    vel.x = lerp(vel.x, (float) 0, (float) (0.2 * 60 * delta));
                }

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


}
