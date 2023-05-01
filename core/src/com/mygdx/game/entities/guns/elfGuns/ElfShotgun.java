package com.mygdx.game.entities.guns.elfGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Elf;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.floorGuns.FloorRevolver;
import com.mygdx.game.entities.guns.floorGuns.FloorShotgun;
import com.mygdx.game.helpers.constants.ObjectPool;

public class ElfShotgun extends ElfGun {


    public void init(){
        moveSpeed = 100;
        distanceMin = 50;
        distanceMax = 200;

        rotation = 0;

        aimedTolerance = Math.toRadians(10);
        aimSpeed = 0.04;
        Math.toDegrees(1.5);

        tex = new Texture("edg32Shotgun.png");
        texOffset = new Vector2(10,0);

        timeUntilNextShot = 0;
        fireRate = 0.5;

        floorClass = FloorShotgun.class;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 100;
        float bulletFromPlayer = 19;
        float bulletSpeed = 250;

        int numBullets = 5;
        int spread = 30;

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
