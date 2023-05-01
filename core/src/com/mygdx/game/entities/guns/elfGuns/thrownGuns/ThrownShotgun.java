package com.mygdx.game.entities.guns.elfGuns.thrownGuns;

import com.mygdx.game.helpers.constants.TextureHolder;

public class ThrownShotgun extends ThrownGun {

    public ThrownShotgun(){
        mySize = 4;
        myTex = TextureHolder.shotgun;
        throwSpeed = 400;
        distanceFromPlayer = 4;

    }

}