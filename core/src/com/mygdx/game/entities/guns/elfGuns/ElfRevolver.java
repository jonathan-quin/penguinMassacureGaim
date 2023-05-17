package com.mygdx.game.entities.guns.elfGuns;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.ElfBullet;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.floorGuns.FloorRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;

public class ElfRevolver extends ElfGun {

    public void init(){
        moveSpeed = 100;
        distanceMin = 200;
        distanceMax = 300;

        rotation = 0;

        aimedTolerance = Math.toRadians(7);
        aimSpeed = 0.1;
        fixedAimSpeed = Math.toDegrees(3);

        tex = TextureHolder.redRevolver;
        floorClass = FloorRevolver.class;
        texOffset = new Vector2(10, -2);

        timeUntilNextShot = 0;
        fireRate = 1.2;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 100;
        float bulletFromPlayer = 17;
        float bulletSpeed = 500;

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
