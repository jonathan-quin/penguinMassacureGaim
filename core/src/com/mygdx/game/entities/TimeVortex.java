package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.*;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.StaticNode;
import com.mygdx.game.nodes.TextureEntity;
import com.mygdx.game.nodes.TimeRewindRoot;

import java.util.ArrayList;

public class TimeVortex extends StaticNode implements TimeRewindInterface {


    Vector2 vel;


    public TimeVortex(){
        this(0,0);
    }
    public TimeVortex( float x, float y) {

        super( x, y, getMaskLayers(LayerNames.PLAYER), getMaskLayers());
        vel = new Vector2(0,0);
    }

    public TimeVortex init(float x, float y,Vector2 vel){

        super.init( x, y, getMaskLayers(LayerNames.PLAYER), getMaskLayers());



        addChild( ObjectPool.get(TextureEntity.class).init(TextureHolder.timeVortex,0,0,0,0));
        lastChild().setName("sprite");


        this.vel.set(vel);




        ( (TextureEntity) lastChild()).setFlip(false,false);

        addChild( ObjectPool.get(CollisionShape.class).init(1958,1024,0,0));



        return this;
    }

    public void ready(){
        addToGroup("rewind");
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        position.add((float) (vel.x * delta), (float) (vel.y * delta));

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

        returnArr.add(this.getClass()); //0
        returnArr.add(ObjectPool.get(Vector2.class).set(position)); //1
        returnArr.add(ObjectPool.get(Vector2.class).set(vel)); //2

        return returnArr;
    }

    @Override
    public TimeVortex init() {
        init(0,0,ObjectPool.getGarbage(Vector2.class).set(0,0));
        return this;
    }

    @Override
    public TimeVortex load(Object... vars) {
        this.position.set( (Vector2) vars[1]);
        this.vel.set( (Vector2) vars[2]);
        return null;
    }
}
