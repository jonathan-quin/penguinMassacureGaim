package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.ElfGun;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.*;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.lerp;
import static java.lang.Math.abs;

public class Elf extends MovementNode implements TimeRewindInterface {

    Player player;

    ElfGun myGun;

    Vector2 vel;

    private float wanderSpeed = 60;
    private float wanderAccel = 0.3f * 60;

    private double gravity = 400;


    enum State {
        WANDER,
        CHASE,
        ATTACK,
        REPOSITION
    }

    enum Direction {
        LEFT,
        RIGHT
    }
    State state = State.WANDER;
    Direction direction = Direction.LEFT;

    public Elf(){
        super();
        vel = new Vector2(0,0);

    }

    public void ready(){
        player = (Player) getRootNode().getChild("player");

        addToGroup("rewind");

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.elfTexture,0f,4f,0,0));
        lastChild().setName("sprite");

        if (myGun != null){
            addChild(ObjectPool.get(TextureEntity.class).init(myGun.tex,0f,1f,myGun.texOffset.x,myGun.texOffset.y));
            lastChild().setName("gunSprite");
        }


        addChild(ObjectPool.get(CollisionShape.class).init(8,12,0,0));

        addChild(ObjectPool.get(ColliderObject.class).init(350,0,getMaskLayers(LayerNames.PLAYER),getMaskLayers()));
        lastChild().setName("playerDetect");
        lastChild().addChild(ObjectPool.get(CollisionShape.class).init(400,200,0,0));
        ((CollisionShape)lastChild(). lastChild()).myColor = new Color(0,0,0.5f,0.1f);

        addChild( ObjectPool.get(Raycast.class).init(0,4,0,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("playerLOS");
        ((Raycast)lastChild()).makeDirtyOnUpdate = false;

        addChild( ObjectPool.get(Raycast.class).init(15,-30,0,-36,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightLedgeDetect");

        addChild( ObjectPool.get(Raycast.class).init(30,0,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightNoJump");
        addChild( ObjectPool.get(Raycast.class).init(30,32,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightHalfJump");
        addChild( ObjectPool.get(Raycast.class).init(30,64,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightFullJump");

        addChild( ObjectPool.get(Raycast.class).init(-15,-30,0,-36,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftLedgeDetect");

        addChild( ObjectPool.get(Raycast.class).init(-30,0,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftNoJump");
        addChild( ObjectPool.get(Raycast.class).init(-30,32,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftHalfJump");
        addChild( ObjectPool.get(Raycast.class).init(-30,64,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftFullJump");


        getChild("rightLedgeDetect",Raycast.class).makeDirtyOnUpdate = false;
        getChild("rightNoJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("rightHalfJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("rightFullJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftLedgeDetect",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftNoJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftHalfJump",Raycast.class).makeDirtyOnUpdate = false;
        getChild("leftFullJump",Raycast.class).makeDirtyOnUpdate = false;

        //ray = (Raycast) getNewestChild();


    }

    @Override
    public ArrayList<Object> save() {
        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add(this.getClass());
        returnArr.add(ObjectPool.get(Vector2.class).set(position));
        returnArr.add(ObjectPool.get(Vector2.class).set(vel));
        returnArr.add(myGun.getClass());

        returnArr.add(getChild("sprite",TextureEntity.class).getFlipX());

        returnArr.add(direction == Direction.LEFT);


        return returnArr;
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

        return null;
    }

    @Override
    public Elf init() {

        init(0,0,null);
        return this;
    }

    public Elf init(float x, float y, ElfGun gun){
        super.init(x,y,getMaskLayers(LayerNames.WALLS),getMaskLayers(LayerNames.ELVES));

        this.myGun = gun;
        if (gun != null) gun.init();

        vel.set(0,0);

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

        vel.y -= gravity * delta;


        switch (state){
            case WANDER:

                Vector2 targetVel = ObjectPool.getGarbage(Vector2.class).set(0,0);

                boolean onFloor = testMove(0,-1);
                switch (direction){

                    case RIGHT:



                        targetVel.x = wanderSpeed;
                        getChild("sprite",TextureEntity.class).setFlip(true,false);

                        if (!getChild("rightLedgeDetect",Raycast.class).isColliding()){
                            direction = Direction.LEFT;
                        }



                        if (onFloor){

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

                        break;

                    case LEFT:
                        targetVel.x = -wanderSpeed;
                        getChild("sprite",TextureEntity.class).setFlip(false,false);

                        if (!getChild("leftLedgeDetect",Raycast.class).isColliding()){
                            direction = Direction.RIGHT;
                        }



                        if (onFloor){

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

                        break;




                }

                vel.x = lerp(vel.x,targetVel.x, (float) (wanderAccel * delta));

                vel.set( moveAndSlide(vel,delta) );


                if (canSeePlayer()){
                    //state = State.CHASE;
                }

                break;







        }


    }

    public boolean canSeePlayer(){

        switch (direction){
            case LEFT:
                getChild("playerDetect").position.x = -350;
                break;
            case RIGHT:
                getChild("playerDetect").position.x = 350;
                break;
        }

        getChild("playerDetect",ColliderObject.class).getFirstCollision(ObjectPool.getGarbage(Vector2.class).set(0,1));
        boolean playerInSightBox = getChild("playerDetect",ColliderObject.class).lastCollided;

        Raycast ray = (Raycast) getChild("playerLOS");
        ray.dirty = true;

        if (!playerInSightBox) return false;


        ray.setCast(player.globalPosition.x - globalPosition.x,player.globalPosition.y - globalPosition.y);

        return !ray.isColliding();

    }

    public void jump(double height){

        vel.y = (float) Math.sqrt(abs(-2 * gravity * height));

    }

    @Override
    public void free() {
        super.free();
        ObjectPool.removeBackwards(myGun);
    }
}
