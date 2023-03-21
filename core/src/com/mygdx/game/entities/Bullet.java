package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.ObjectPool;
import com.mygdx.game.helpers.TextureHolder;
import com.mygdx.game.helpers.Utils;
import com.mygdx.game.nodes.MovementNode;
import com.mygdx.game.nodes.TextureEntity;

public class Bullet extends MovementNode {

    Vector2 vel;

    public Bullet(){

        super(null);
        vel = (Vector2) ObjectPool.get(Vector2.class);
        setMaskLayers(getMaskLayers(1),getMaskLayers(1));
        addChild(new TextureEntity(TextureHolder.penguinTexture,0,0,10,10));
    }

    public Bullet init(float posX, float posY, float velX, float velY){

        vel.set(velX,velY);

        position.set(posX,posY);

        return this;
    }


    public void update(double delta){

        moveAndSlide(vel,(float) delta);

        if (!Utils.is_on_screen(position.x,position.y,10,10)){
            free();
        };

    }

    public void free(){



    }


}
