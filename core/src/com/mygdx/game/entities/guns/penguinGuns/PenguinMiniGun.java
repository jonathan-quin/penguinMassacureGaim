package com.mygdx.game.entities.guns.penguinGuns;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.elfGuns.Bullets.PenguinBullet;
import com.mygdx.game.entities.guns.elfGuns.thrownGuns.ThrownMiniGun;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;

public class PenguinMiniGun extends PenguinGun{

    public PenguinMiniGun init(){

        super.init();

        moveSpeed = 100;

        rotation = 0;

        aimSpeed = 0.2;
        fixedAimSpeed = Math.toDegrees(6);


        startingAmmo = 30;
        ammoLeft = startingAmmo;

        recoil = 75;

        tex = TextureHolder.greenMiniGun;
        texOffset = new Vector2(13,-0.5f);

        timeUntilNextShot = 0;
        fireRate = 20;

        throwClass = ThrownMiniGun.class;

        updateGlobalPosition();

        return this;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 100;
        float bulletFromPlayer = 30;
        float bulletSpeed = 700;

        Vector2 newDir = ObjectPool.getGarbage(Vector2.class).set(bulletSpeed,0);
        newDir.rotateRad((float) rotation);

        Vector2 startOffset = ObjectPool.getGarbage(Vector2.class).set(newDir).nor().scl(bulletFromPlayer).add(pos);


        GenericBullet[] returnArr = new GenericBullet[1];

        returnArr[0] = ObjectPool.get( PenguinBullet.class );
        returnArr[0].init(startOffset.x, startOffset.y,newDir.x, newDir.y, damage);
        returnArr[0].deadFramesPlayer = true;

        return returnArr;
    }

}