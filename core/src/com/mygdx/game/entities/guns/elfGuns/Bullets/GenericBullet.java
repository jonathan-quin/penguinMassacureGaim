package com.mygdx.game.entities.guns.elfGuns.Bullets;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Elf;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.TimeParticle;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.ParticleMaker;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.MovementNode;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;

public class GenericBullet extends MovementNode implements TimeRewindInterface {

    Vector2 vel;


    float damage;

    TextureEntity sprite;

    public GenericBullet(){

        super();
        vel = new Vector2(0,0);
        damage = 0;
        setMaskLayers( getMaskLayers(LayerNames.WALLS),getMaskLayers());

    }

    public void ready(){
        addChild( ( ObjectPool.get(TextureEntity.class) ).init(TextureHolder.smallBulletTexture,0,0,0,0));
        sprite = (TextureEntity) lastChild();

        addChild( ( ObjectPool.get(CollisionShape.class)).init (4,4,0,0));
        lastChild().setName("shape");

        addToGroup("rewind");
        sprite.setRotation(vel.angleDeg());
    }

    public GenericBullet init(float posX, float posY, float velX, float velY, float damage){

        super.init(posX,posY,getMaskLayers(LayerNames.WALLS,LayerNames.ELVES,LayerNames.PLAYER),getMaskLayers());

        vel.set(velX,velY);

        this.damage = damage;

        lastSave = null;

        return this;
    }


    public void update(double delta){


        moveAndSlide(vel,(float) delta);

        sprite.setRotation(vel.angleDeg());

        if (lastCollided){
            if (lastCollider.isOnLayer(LayerNames.WALLS)){
                die();
            }
            if (lastCollider.isOnLayer(LayerNames.PLAYER)){
                ((Player) lastCollider).hit(vel,damage);
                die();
            }
            if (lastCollider.isOnLayer(LayerNames.ELVES)){
                ((Elf) lastCollider).hit(vel,damage);
                die();
            }
        }

        if (!Utils.is_on_screen(globalPosition,10,10)){
            queueFree();
        }

    }

    public void die(){
        queueFree();
        for (TimeParticle t : ParticleMaker.makeDisapearingParticles(sprite, vel)) {
            getParent().addChild(t);
        }
    }



    public void free(){
        super.free();

    }


    @Override
    public ArrayList<Object> save() {

        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add((ArrayList<Object>) ObjectPool.get(ArrayList.class)); // returnArr.get(0).clear();
        ((ArrayList)returnArr.get(0)).clear();
        ((ArrayList)returnArr.get(0)).add(this.getClass());
        ((ArrayList)returnArr.get(0)).add(this.getParent());
        ((ArrayList)returnArr.get(0)).add(lastSave);

        returnArr.add(new Vector2(position));
        returnArr.add(new Vector2(vel));
        returnArr.add(damage);

        lastSave = returnArr;
        return returnArr;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }

    @Override
    public GenericBullet init() {
        init(0,0,0,0,0);
        lastSave = null;
        return  this;
    }

    @Override
    public <T> T load(Object... vars) {

        this.position.set( (Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);
        this.damage = ((float) vars[3]);

        sprite.setRotation(vel.angleDeg());
        updateGlobalPosition();
        return null;
    }





}
