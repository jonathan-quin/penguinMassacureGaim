package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.ObjectPool;
import com.mygdx.game.helpers.TextureHolder;
import com.mygdx.game.helpers.Utils;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.MovementNode;
import com.mygdx.game.nodes.TextureEntity;

public class Bullet extends MovementNode {

    Vector2 vel;

    TextureEntity sprite;

    public Bullet(){

        super(null);
        vel = new Vector2(0,0);
        setMaskLayers( getMaskLayers(0,2),getMaskLayers(1,2));





    }

    public Bullet init(float posX, float posY, float velX, float velY){

        super.init(posX,posY);
        vel.set(velX,velY);
        position.set(posX,posY);

        addChild( ((TextureEntity) ObjectPool.get(TextureEntity.class) ).init(TextureHolder.bulletTexture,0,0,0,0));
        sprite = (TextureEntity) getNewestChild();

        addChild( ((CollisionShape) ObjectPool.get(CollisionShape.class)).init (10,10,0,0));
        getNewestChild().name = "shape";


        return this;
    }


    public void update(double delta){

        moveAndSlide(vel,(float) delta);

        sprite.setRotation(vel.angleDeg());

        if (lastCollided){
            if (lastCollider.isInGroup("icePlatform")) queueFree();
        }

        if (!Utils.is_on_screen(globalPosition,10,10)){
            queueFree();
        }

    }


    public void free(){
        super.free();

    }


}
