package com.mygdx.game.entities.guns.elfGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ElfRevolver extends ElfGun {

    public void init(){
        moveSpeed = 100;
        distanceMin = 200;
        distanceMax = 300;

        rotation = 0;

        aimedTolerance = Math.toRadians(10);
        aimSpeed = 0.2;

        tex = new Texture("revolverForNow.png");
        texOffset = new Vector2(0,0);

        timeUntilNextShot = 0;
        fireRate = 2;
    }

}
