package com.mygdx.game.entities;

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

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.elfTexture,0f,1f,0,0));
        lastChild().setName("sprite");

        addChild(ObjectPool.get(TextureEntity.class).init(myGun.tex,0f,1f,myGun.texOffset.x,myGun.texOffset.y));
        lastChild().setName("gunSprite");

        addChild(ObjectPool.get(CollisionShape.class).init(8,12,0,0));

        addChild(ObjectPool.get(ColliderObject.class).init(100,0,getMaskLayers(),getMaskLayers(LayerNames.PLAYER)));
        lastChild().setName("playerDetect");
        lastChild().addChild(ObjectPool.get(CollisionShape.class).init(100,50,0,0));



        addChild( ObjectPool.get(Raycast.class).init(30,0,0,-30,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightLedgeDetect");

        addChild( ObjectPool.get(Raycast.class).init(30,0,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightNoJump");
        addChild( ObjectPool.get(Raycast.class).init(30,32,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightHalfJump");
        addChild( ObjectPool.get(Raycast.class).init(30,64,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("rightFullJump");

        addChild( ObjectPool.get(Raycast.class).init(-30,0,0,-30,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftLedgeDetect");

        addChild( ObjectPool.get(Raycast.class).init(-30,0,30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftNoJump");
        addChild( ObjectPool.get(Raycast.class).init(-30,32,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftHalfJump");
        addChild( ObjectPool.get(Raycast.class).init(-30,64,-30,0,false,getMaskLayers(LayerNames.WALLS)) );
        lastChild().setName("leftFullJump");


        //ray = (Raycast) getNewestChild();


    }

    @Override
    public ArrayList<Object> save() {
        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add(this.getClass());
        returnArr.add(ObjectPool.get(Vector2.class).set(position));
        returnArr.add(ObjectPool.get(Vector2.class).set(vel));



        return returnArr;
    }

    @Override
    public Elf init() {
        return this;
    }

    public Elf init(float x, float y, ElfGun gun){
        super.init(x,y,getMaskLayers(LayerNames.WALLS),getMaskLayers(LayerNames.ELVES));

        this.myGun = gun;
        gun.init();

        return this;
    }

    @Override
    public <T> T load(Object... vars) {
        this.position.set( (Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);
        

        if (vel.x != 0){
            ((TextureEntity) getChild("sprite")).setFlip(vel.x < 0,false);
        }
        if (((TextureEntity) getChild("sprite")).getFlipX()) {
            ((TextureEntity) getChild("sprite")).setRotation(rotation);
        } else {
            ((TextureEntity) getChild("sprite")).setRotation(-rotation);
        }

        updateGlobalPosition();
        Globals.cameraOffset.set(globalPosition);


        return null;
    }


    public void update(double delta){

        vel.y -= gravity * delta;

        switch (state){
            case WANDER:

                Vector2 targetVel = ObjectPool.getGarbage(Vector2.class).set(0,0);
                boolean onFloor = testMove(0,-1);
                switch (direction){

                    case RIGHT:
                        targetVel.x = wanderSpeed;
                        getChild("sprite",TextureEntity.class).setFlip(true,false);

                        if (getChild("rightLedgeDetect",Raycast.class).isColliding()){
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
                        getChild("sprite",TextureEntity.class).setFlip(true,false);

                        if (getChild("leftLedgeDetect",Raycast.class).isColliding()){
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

                vel = moveAndSlide(vel,delta);

                break;







        }


    }

    public void jump(double height){
        vel.y = (float) Math.sqrt(-2 * gravity * height);
    }





}
