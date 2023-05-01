package com.mygdx.game.entities.guns.floorGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Elf;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.penguinGuns.PenguinGun;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.MovementNode;
import com.mygdx.game.nodes.StaticNode;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static com.badlogic.gdx.math.MathUtils.lerp;

public class FloorGun extends MovementNode implements TimeRewindInterface {

    Vector2 vel;

    float gravity = 400;
    TextureEntity sprite;

    StaticNode playerDetect;

    protected Class myGun;
    protected Texture myTex;

    public FloorGun() {

        super();
        vel = new Vector2(0, 0);
        setMaskLayers(getMaskLayers(LayerNames.WALLS), getMaskLayers());

    }

    public void ready() {
        addChild((ObjectPool.get(TextureEntity.class)).init(myTex, 0, 0, 0, 0));
        sprite = (TextureEntity) lastChild();

        addChild((ObjectPool.get(CollisionShape.class)).init(4, 4, 0, 0));
        lastChild().setName("shape");

        addChild((ObjectPool.get(StaticNode.class)).init(0,0,getMaskLayers(LayerNames.PLAYER),getMaskLayers()));
        lastChild().addChild((ObjectPool.get(CollisionShape.class)).init(8, 4, 0, 2));
        playerDetect = (StaticNode) lastChild();

        addToGroup("rewind");
        sprite.setRotation(vel.angleDeg());
    }

    public FloorGun init(float posX, float posY, float velX, float velY) {

        super.init(posX, posY, getMaskLayers(LayerNames.WALLS), getMaskLayers());

        vel.set(velX, velY);


        return this;
    }


    public void update(double delta) {

        vel.y -= gravity * delta;

        vel.x = lerp(vel.x, (float) 0, (float) (6*delta));
        if (isEqual(vel.x, (float) 0, 0.01f)) vel.x = 0;

        vel.set(moveAndSlide(vel, (float) delta));

        if (!lastCollided){
            sprite.setRotation(sprite.getRotation() + (Math.signum(vel.x) * delta * -300));
        }

        playerDetect.getFirstCollision(ObjectPool.getGarbage(Vector2.class).set(0,0));
        if (playerDetect.lastCollided) {
            Player player = (Player) playerDetect.lastCollider;
            if (player.getGun() == null){
                player.takeGun(myGun);
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
        returnArr.add((float) sprite.getRotation());

        return returnArr;
    }

    @Override
    public FloorGun init() {
        init(0, 0, 0, 0);
        return this;
    }

    @Override
    public <T> T load(Object... vars) {

        this.position.set((Vector2) vars[1]);
        this.vel.set((Vector2) vars[2]);


        sprite.setRotation((Float) vars[3]);
        updateGlobalPosition();
        return null;
    }

}
