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
    public ArrayList<Object> save() {
        return null;
    }

    @Override
    public TimeParticle init() {
        init(ObjectPool.get(Vector2.class).set(0,0),0,0,0,0,ObjectPool.get(Vector2.class).set(0,0),0,0,0,false, Color.WHITE,Color.WHITE,0,0);
        return this;
    }

    @Override
    public <T> T load(Object... vars) {
        return null;
    }
}
