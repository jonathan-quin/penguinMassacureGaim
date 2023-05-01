package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.MovementNode;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;

public class BulletOLD extends MovementNode implements TimeRewindInterface {

    Vector2 vel;


    TextureEntity sprite;

    public BulletOLD(){

        super();
        vel = new Vector2(0,0);
        setMaskLayers( getMaskLayers(LayerNames.WALLS),getMaskLayers());

    }

    public void ready(){
        addChild( ( ObjectPool.get(TextureEntity.class) ).init(TextureHolder.bulletTexture,0,0,0,0));
        sprite = (TextureEntity) lastChild();

        addChild( ( ObjectPool.get(CollisionShape.class)).init (10,10,0,0));
        lastChild().setName("shape");

        addToGroup("rewind");
        sprite.setRotation(vel.angleDeg());
    }

    public BulletOLD init(float posX, float posY, float velX, float velY){

        super.init(posX,posY,getMaskLayers(LayerNames.WALLS),getMaskLayers());

        vel.set(velX,velY);


        return this;
    }


    public void update(double delta){


        moveAndSlide(vel,(float) delta);

        sprite.setRotation(vel.angleDeg());

        if (lastCollided){
            if (lastCollider.isOnLayer(LayerNames.WALLS)){
                queueFree();

            }
        }

        if (!Utils.is_on_screen(globalPosition,10,10)){
            queueFree();
        }

    }


    public void free(){
        super.free();

    }


    @Override
    public ArrayList<Object> save() {

        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add(this.getClass());
        returnArr.add(new Vector2(position));
        returnArr.add(new Vector2(vel));

        return returnArr;
    }

    @Override
    public BulletOLD init() {
        init(0,0,0,0);
        return  this;
    }

    @Override
    public <T> T load(Object... vars) {

        this.position.set( (Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);

        sprite.setRotation(vel.angleDeg());
        updateGlobalPosition();
        return null;
    }
}
