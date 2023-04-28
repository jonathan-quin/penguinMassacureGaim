package com.mygdx.game.entities.guns.elfGuns.thrownGuns;

import com.mygdx.game.helpers.constants.TextureHolder;

public class ThrownRevolver extends ThrownGun {

    public ThrownRevolver(){
        mySize = 4;
        myTex = TextureHolder.revolver;
        throwSpeed = 400;
        distanceFromPlayer = 4;

    }

}
