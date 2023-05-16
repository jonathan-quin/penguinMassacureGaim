package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.Particle;

import java.util.ArrayList;

public class TimeParticle extends Particle implements TimeRewindInterface {

    public TimeParticle(){
        super();
    }




    @Override
    public TimeParticle init() {
        init(ObjectPool.getGarbage(Vector2.class).set(0,0),0,0,0,0,ObjectPool.getGarbage(Vector2.class).set(0,0),0.01,0,1,false, Color.WHITE,Color.WHITE,0,0);
        return this;
    }

    public TimeParticle init(Vector2 vel, double rotationVel, double rotation, double lifeSpan, double gravity, Vector2 accel, double damping, double speedForward, double bounce, boolean collide, Color color, Color targetColor, double lerpToColor, double moveToColor) {
        super.init(0,0,getMaskLayers(),getMaskLayers());
        this.vel.set(vel);
        this.rotationVel = rotationVel;
        this.rotation = rotation;
        this.lifeSpan = lifeSpan;
        this.gravity = gravity;
        this.accel.set(accel);
        this.damping = damping;
        this.speedForward = speedForward;
        this.collide = collide;
        this.color.set(color);
        this.targetColor.set( targetColor);
        this.lerpToColor = lerpToColor;
        this.moveToColor = moveToColor;
        this.bounce = bounce;

        lastSave = null;

        return this;

    }


    @Override
    public ArrayList<Object> save() {
        ArrayList<Object> a = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        a.clear();

        a.add((ArrayList<Object>) ObjectPool.get(ArrayList.class)); //0
        ((ArrayList)a.get(0)).clear();
        ((ArrayList)a.get(0)).add(this.getClass());
        ((ArrayList)a.get(0)).add(this.getParent());
        ((ArrayList)a.get(0)).add(lastSave);

        a.add(new Vector2(position)); //1
        a.add(new Vector2(vel)); //2
        a.add(new Color(color)); //3
        a.add(rotation); //4

        if (lastSave == null){ //5
            a.add(makeInfo());
        }
        else{
            a.add(lastSave.get(5));
        }


        if (mask.size() > 0){ //16
            a.add(mask.get(0));
        }else{
            a.add(null);
        }

        lastSave = a;
        return a;
    }

    @Override
    public <T> T load(Object... vars) {
        position.set((Vector2) vars[1]);
        vel.set((Vector2) vars[2]);
        color.set((Color) vars[3]);
        rotation = (double) vars[4];

        set((TimeParticleInfo) vars[5]);

        if (vars[6] != null)mask.add((Integer) vars[6]);

        sprite.setMyColor(color);
        sprite.setRotation(rotation);

        return null;
    }

    public void set(TimeParticleInfo T){
        targetColor.set(T.targetColor);
        accel.set(T.accel);
        rotationVel = T.rotationVel;
        lifeSpan = T.lifeSpan;
        gravity = T.gravity;
        damping = T.damping;
        speedForward = T.speedForward;
        collide = T.collide;
        bounce = T.bounce;
        lerpToColor = T.lerpToColor;
        moveToColor = T.moveToColor;
        paint = T.paint;
    }

    public TimeParticleInfo makeInfo(){
        return new TimeParticleInfo().init(rotationVel,lifeSpan,gravity,accel,damping,speedForward,bounce,collide,targetColor,lerpToColor,moveToColor,paint);
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }
}
