package com.mygdx.game.entities.guns.penguinGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.helpers.constants.ObjectPool;

public class PenguinRevolver extends PenguinGun{

    public PenguinRevolver init(){

        super.init();

        moveSpeed = 100;

        rotation = 0;

        aimSpeed = 0.08;
        fixedAimSpeed = Math.toDegrees(2);


        startingAmmo = 3;
        ammoLeft = startingAmmo;

        recoil = 20;

        tex = new Texture("revolverForNow.png");
        texOffset = new Vector2(3,-3.5f);

        timeUntilNextShot = 0;
        fireRate = 1.2;

        updateGlobalPosition();

        return this;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 50;
        float bulletFromPlayer = 19;
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
