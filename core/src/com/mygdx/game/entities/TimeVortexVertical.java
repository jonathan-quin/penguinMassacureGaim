package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.StaticNode;
import com.mygdx.game.nodes.TextureEntity;
import com.mygdx.game.nodes.TimeRewindRoot;

public class TimeVortexVertical extends StaticNode implements TimeRewindInterface {


    Vector2 vel;

    Vector2 targetVel;

    double accel = 1;


    public TimeVortexVertical(){
        this(0,0);
    }
    public TimeVortexVertical(float x, float y) {

        super( x, y, getMaskLayers(LayerNames.PLAYER), getMaskLayers());
        vel = new Vector2(0,0);
        targetVel = new Vector2(0,0);
    }

    public TimeVortexVertical init(float x, float y, Vector2 vel){
        return init(x,y,vel,vel,1);
    }

    public TimeVortexVertical init(float x, float y, Vector2 vel, Vector2 targetVel, double accel){

        super.init( x, y, getMaskLayers(LayerNames.PLAYER), getMaskLayers(LayerNames.VORTEX));



        addChild( ObjectPool.get(TextureEntity.class).init(TextureHolder.timeVortex,0,0,0,0));
        lastChild().setName("sprite");


        this.vel.set(vel);
        this.targetVel.set(targetVel);
        this.accel = accel;



        ( (TextureEntity) lastChild()).setFlip(false,false);

        addChild( ObjectPool.get(CollisionShape.class).init(1024,1958,0,0));

        updateParentPos();

        lastSave = null;

        return this;
    }

    public void ready(){
        addToGroup("rewind");
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        vel.lerp(targetVel, (float) (accel * 60 * delta));

        getChild("sprite",TextureEntity.class).setRotation(vel.angleDeg());

        position.add(0,(float) (vel.y * delta));

        position.x = Globals.cameraOffset.x - Globals.cameraOffset.x % 64;

        getFirstCollision(ObjectPool.getGarbage(Vector2.class).set(0,0));

        if (lastCollided){

            double newSpeed = Globals.gameSpeed/1.2;


            if (Globals.gameSpeed < 0.01){
                newSpeed = 0;
            }

            ((TimeRewindRoot)SceneHandler.getCurrentRoot()).setNextGameSpeed(newSpeed);

        }

    }


    @Override
    public ArrayList<Object> save() {
        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add((ArrayList<Object>) ObjectPool.get(ArrayList.class)); // 0
        ((ArrayList)returnArr.get(0)).clear();
        ((ArrayList)returnArr.get(0)).add(this.getClass());
        ((ArrayList)returnArr.get(0)).add(this.getParent());
        ((ArrayList)returnArr.get(0)).add(lastSave);

        returnArr.add(new Vector2(position)); //1
        returnArr.add(new Vector2(vel)); //2
        returnArr.add(new Vector2(targetVel));
        returnArr.add(accel);


        returnArr.add((int) position.x);


        lastSave = returnArr;
        return returnArr;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }

    @Override
    public TimeVortexVertical init() {
        init(0,0,ObjectPool.getGarbage(Vector2.class).set(0,0));
        return this;
    }

    @Override
    public TimeVortexVertical load(Object... vars) {
        this.position.set( (Vector2) vars[1]);
        this.vel.set( (Vector2) vars[2]);
        this.targetVel.set( (Vector2) vars[3]);
        this.accel = (double) vars[4];

        position.x = (int) vars[5];

        getChild("sprite",TextureEntity.class).setRotation(vel.angleDeg());

        return null;
    }
}
