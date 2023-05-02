package com.mygdx.game.entities.guns.penguinGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.elfGuns.thrownGuns.ThrownRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;

public class PenguinRevolver extends PenguinGun{

    public PenguinRevolver init(){

        super.init();

        moveSpeed = 100;

        rotation = 0;

        aimSpeed = 0.2;
        fixedAimSpeed = Math.toDegrees(6);


        startingAmmo = 300;
        ammoLeft = startingAmmo;

        recoil = 100;

        tex =TextureHolder.revolver;
        texOffset = new Vector2(3,-3.5f);

        timeUntilNextShot = 0;
        fireRate = 2;

        throwClass = ThrownRevolver.class;

        updateGlobalPosition();

        return this;
    }

    protected GenericBullet[] getBullets(Vector2 pos) {

        float damage = 100;
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
