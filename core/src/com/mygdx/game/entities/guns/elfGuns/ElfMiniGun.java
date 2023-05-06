package com.mygdx.game.entities.guns.elfGuns;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.ElfBullet;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.floorGuns.FloorMiniGun;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;

public class ElfMiniGun extends ElfGun {

    public void init(){
        moveSpeed = 100;
        distanceMin = 200;
        distanceMax = 700;

        rotation = 0;

        aimedTolerance = Math.toRadians(7);
        aimSpeed = 0.1;
        fixedAimSpeed = Math.toDegrees(7);

        tex = TextureHolder.redMiniGun;
        floorClass = FloorMiniGun.class;
        texOffset = new Vector2(13,-0.5f);

        timeUntilNextShot = 0;
        fireRate = 20;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 100;
        float bulletFromPlayer = 21;
        float bulletSpeed = 300;

        Vector2 newDir = ObjectPool.getGarbage(Vector2.class).set(bulletSpeed,0);
        newDir.rotateRad((float) rotation);

        Vector2 startOffset = ObjectPool.getGarbage(Vector2.class).set(newDir).nor().scl(bulletFromPlayer).add(pos);


        GenericBullet[] returnArr = new GenericBullet[1];

        returnArr[0] = ObjectPool.get( ElfBullet.class );
        returnArr[0].init(startOffset.x, startOffset.y,newDir.x, newDir.y, damage);
        returnArr[0].deadFramesElves = true;

        return returnArr;
    }

}