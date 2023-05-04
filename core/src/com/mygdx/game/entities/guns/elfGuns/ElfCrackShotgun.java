package com.mygdx.game.entities.guns.elfGuns;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.helpers.constants.ObjectPool;

public class ElfCrackShotgun extends ElfShotgun{


    public void init(){
        super.init();
        fireRate = 10;
        distanceMax = 600;
        aimSpeed = 0.2;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 1;
        float bulletFromPlayer = 19;
        float bulletSpeed = 450;

        int numBullets = 15;
        int spread = 40;

        Vector2 newDir = ObjectPool.getGarbage(Vector2.class).set(bulletSpeed,0);
        newDir.rotateRad((float) rotation);

        Vector2 startOffset = ObjectPool.getGarbage(Vector2.class).set(newDir).nor().scl(bulletFromPlayer).add(pos);


        GenericBullet[] returnArr = new GenericBullet[numBullets];

        newDir.rotateRad((float) (Math.toRadians(spread)/-2));

        for (int i = 0; i < numBullets; i++){
            returnArr[i] = ObjectPool.get( GenericBullet.class ).init(startOffset.x, startOffset.y,newDir.x, newDir.y, damage);
            newDir.rotateRad((float) (Math.toRadians(spread)/numBullets));
        }




        return returnArr;
    }

}
