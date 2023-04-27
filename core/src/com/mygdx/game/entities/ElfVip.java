package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.elfGuns.ElfGun;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.MathUtils;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.*;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.lerp;
import static java.lang.Math.abs;
import static java.lang.Math.toDegrees;

public class ElfVip extends Elf implements TimeRewindInterface {



    public ElfVip(){
        super();

    }



    @Override
    public ElfVip init() {

        init(0,0,null);
        return this;
    }

    public ElfVip init(float x, float y, ElfGun gun){
        super.init(x,y,getMaskLayers(LayerNames.WALLS),getMaskLayers(LayerNames.ELVES));


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

        vel.y -= gravity * delta;
        boolean onFloor = testMove(0,-1);


        getChild("gunSprite",TextureEntity.class).setRotation(toDegrees(myGun.rotation));
        getChild("gunSprite",TextureEntity.class).setFlip(false, myGun.shouldFlipY());

        myGun.updateTimeUntilNextShot(delta);

        switch (state){
            case WANDER:

                Vector2 targetVel = ObjectPool.getGarbage(Vector2.class).set(0,0);

                getChild("exclaim",TextureEntity.class).setVisible(false);
                getChild("question",TextureEntity.class).setVisible(false);

                turnAroundOrJumpV2Wander(onFloor);



                targetVel.x = (direction == Elf.Direction.LEFT) ? -wanderSpeed : wanderSpeed;


                vel.x = lerp(vel.x,targetVel.x, (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );


                if (canSeePlayer()){
                    state = Elf.State.CHASE;
                }

                break;

            case CHASE:

                myGun.aimAt(globalPosition,player.globalPosition,delta);

                getChild("exclaim",TextureEntity.class).setVisible(true);
                getChild("question",TextureEntity.class).setVisible(false);

                double playerDistSQR = globalPosition.dst2((player.globalPosition));

                boolean away = (playerDistSQR) < Math.pow(myGun.distanceMin,2);

                double targetX = moveTowardsPoint(player.globalPosition,onFloor,true);

                vel.x = lerp(vel.x, (float) (targetX * myGun.moveSpeed), (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );

                lastPlayerPos.set( player.globalPosition);

                if (!canSeePlayer()){
                    state = Elf.State.SEARCH;
                }

                myGun.aimAt(globalPosition,player.globalPosition,delta);

                if (myGun.canShoot(globalPosition,player.globalPosition) && playerDistSQR < 1.2*(myGun.distanceMax*myGun.distanceMax)){

                    for (GenericBullet bullet : myGun.shoot(ObjectPool.getGarbage(Vector2.class).set(globalPosition).sub(0,-2))){
                        bulletHolder.addChild(bullet);
                    }

                }



                break;

            case SEARCH:

                getChild("exclaim",TextureEntity.class).setVisible(false);
                getChild("question",TextureEntity.class).setVisible(true);

                double targetX2 = moveTowardsPoint(lastPlayerPos,onFloor,true);

                vel.x = lerp(vel.x, (float) (targetX2 * searchSpeed), (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );

                if (canSeePlayer()){
                    state = Elf.State.CHASE;
                }

                if (MathUtils.isEqualApprox(vel.x,0,0.1) || MathUtils.isEqualApprox(globalPosition.x,lastPlayerPos.x,4)){
                    state = Elf.State.WANDER;
                }

                break;






        }


    }


}
