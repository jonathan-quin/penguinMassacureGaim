package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.Particle;

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

        a.add(new Color(targetColor)); //5
        a.add(new Vector2(accel)); //6
        a.add(rotationVel); //7
        a.add(lifeSpan); //8
        a.add(gravity); //9
        a.add(damping); //10
        a.add(speedForward); //11
        a.add(collide); //12
        a.add(bounce); //13
        a.add(lerpToColor); //14
        a.add(moveToColor); //15

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

        targetColor.set((Color) vars[5]);
        accel.set((Vector2) vars[6]);
        rotationVel = (double) vars[7];
        lifeSpan = (double) vars[8];
        gravity = (double) vars[9];
        damping = (double) vars[10];
        speedForward = (double) vars[11];
        collide = (boolean) vars[12];
        bounce = (double) vars[13];
        lerpToColor = (double) vars[14];
        moveToColor = (double) vars[15];

        if (vars[16] != null)mask.add((Integer) vars[16]);

        sprite.setMyColor(color);
        sprite.setRotation(rotation);

        return null;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }
}
