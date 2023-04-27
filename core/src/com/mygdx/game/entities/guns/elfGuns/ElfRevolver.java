package com.mygdx.game.entities.guns.elfGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.helpers.constants.ObjectPool;

public class ElfRevolver extends ElfGun {

    public void init(){
        moveSpeed = 100;
        distanceMin = 200;
        distanceMax = 300;

        rotation = 0;

        aimedTolerance = Math.toRadians(10);
        aimSpeed = 0.08;
        Math.toDegrees(2);

        tex = new Texture("revolverForNow.png");
        texOffset = new Vector2(0,0);

        timeUntilNextShot = 0;
        fireRate = 1.2;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 100;
        float bulletFromPlayer = 17;
        float bulletSpeed = 300;

        Vector2 newDir = ObjectPool.getGarbage(Vector2.class).set(bulletSpeed,0);
        newDir.rotateRad((float) rotation);

        Vector2 startOffset = ObjectPool.getGarbage(Vector2.class).set(newDir).nor().scl(bulletFromPlayer).add(pos);


        GenericBullet[] returnArr = new GenericBullet[1];

        returnArr[0] = ObjectPool.get( GenericBullet.class );
        returnArr[0].init(startOffset.x, startOffset.y,newDir.x, newDir.y, damage);

        return returnArr;
    }

}
