package com.mygdx.game.entities.guns.penguinGuns;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.elfGuns.Bullets.PenguinBullet;
import com.mygdx.game.entities.guns.elfGuns.thrownGuns.ThrownShotgun;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;

public class PenguinShotgun extends PenguinGun{

    public PenguinShotgun init(){

        super.init();

        moveSpeed = 100;

        rotation = 0;

        aimSpeed = 0.2;
        fixedAimSpeed = Math.toDegrees(6);


        startingAmmo = 2;
        ammoLeft = startingAmmo;

        recoil = 500;

        tex = TextureHolder.greenShotgun;
        texOffset = new Vector2(12,-3.5f);

        timeUntilNextShot = 0;
        fireRate = 1.2;

        throwClass = ThrownShotgun.class;

        updateGlobalPosition();

        return this;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 100;
        float bulletFromPlayer = 19;
        float bulletSpeed = 400;

        int numBullets = 5;
        int spread = 30;

        Vector2 newDir = ObjectPool.getGarbage(Vector2.class).set(bulletSpeed,0);
        newDir.rotateRad((float) rotation);

        Vector2 startOffset = ObjectPool.getGarbage(Vector2.class).set(newDir).nor().scl(bulletFromPlayer).add(pos);


        GenericBullet[] returnArr = new GenericBullet[numBullets];

        newDir.rotateRad((float) (Math.toRadians(spread)/-2));

        for (int i = 0; i < numBullets; i++){
            returnArr[i] = ObjectPool.get( PenguinBullet.class ).init(startOffset.x, startOffset.y,newDir.x, newDir.y, damage);
            returnArr[i].deadFramesPlayer = true;
            newDir.rotateRad((float) (Math.toRadians(spread)/numBullets));
        }


        return returnArr;
    }


}
