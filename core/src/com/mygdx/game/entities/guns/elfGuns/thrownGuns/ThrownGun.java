package com.mygdx.game.entities.guns.elfGuns.thrownGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Elf;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.MovementNode;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;

public class ThrownGun extends MovementNode implements TimeRewindInterface {

    Vector2 vel;


    float damage = 100;
    float gravity = 400;

    TextureEntity sprite;
    public Texture myTex;
    public int mySize;
    public float distanceFromPlayer;
    public float throwSpeed;

    public double deadFrames = 0;
    public double maxDeadFrames = 0.1;

    public ThrownGun() {

        super();
        vel = new Vector2(0, 0);
        damage = 100;
        setMaskLayers(getMaskLayers(LayerNames.WALLS), getMaskLayers());

    }

    public void ready() {
        addChild((ObjectPool.get(TextureEntity.class)).init(myTex, 0, 0, 0, 0));
        sprite = (TextureEntity) lastChild();

        addChild((ObjectPool.get(CollisionShape.class)).init(mySize, mySize, 0, 0));
        lastChild().setName("shape");

        addToGroup("rewind");
        sprite.setRotation(vel.angleDeg());
    }

    public ThrownGun init(float posX, float posY, float velX, float velY) {

        super.init(posX, posY, getMaskLayers(LayerNames.WALLS, LayerNames.ELVES, LayerNames.PLAYER), getMaskLayers());

        vel.set(velX, velY);

        deadFrames = maxDeadFrames;

        return this;
    }

    public ThrownGun initThrow(Vector2 start,Vector2 target){
        Vector2 startPos = ObjectPool.getGarbage(Vector2.class).set(start);
        startPos.add( ObjectPool.getGarbage(Vector2.class).set(target.x-start.x,target.y-start.y).nor().scl(distanceFromPlayer));

        Vector2 dir = ObjectPool.get(Vector2.class).set(target.x-start.x,target.y-start.y).nor().scl(throwSpeed);

        return init(startPos.x,startPos.y,dir.x,dir.y);
    }


    public void update(double delta) {

        vel.y -= gravity * delta;

        moveAndSlide(vel, (float) delta);

        sprite.setRotation(vel.angleDeg());

        if (deadFrames >= -0.1)
        deadFrames -= delta;

        if (lastCollided && deadFrames < 0) {
            if (lastCollider.isOnLayer(LayerNames.WALLS)) {
                queueFree();
            }
            if (lastCollider.isOnLayer(LayerNames.PLAYER)) {
                ((Player) lastCollider).hit(vel, damage);
                queueFree();
            }
            if (lastCollider.isOnLayer(LayerNames.ELVES)) {
                ((Elf) lastCollider).hit(vel, damage);
                queueFree();
            }
        }



    }


    public void free() {
        super.free();

    }


    @Override
    public ArrayList<Object> save() {

        ArrayList<Object> returnArr = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        returnArr.clear();

        returnArr.add((ArrayList<Object>) ObjectPool.get(ArrayList.class)); //0
        ((ArrayList)returnArr.get(0)).clear();
        ((ArrayList)returnArr.get(0)).add(this.getClass());
        ((ArrayList)returnArr.get(0)).add(this.getParent());

        returnArr.add(new Vector2(position));
        returnArr.add(new Vector2(vel));
        returnArr.add(damage);

        return returnArr;
    }

    @Override
    public ThrownGun init() {
        init(0, 0, 0, 0);
        return this;
    }

    @Override
    public <T> T load(Object... vars) {

        this.position.set((Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);
        this.damage = ((float) vars[3]);

        sprite.setRotation(vel.angleDeg());
        updateGlobalPosition();
        return null;
    }
}