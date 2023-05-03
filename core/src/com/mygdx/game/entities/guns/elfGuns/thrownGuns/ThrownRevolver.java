package com.mygdx.game.entities.guns.elfGuns.thrownGuns;

import com.mygdx.game.helpers.constants.TextureHolder;

public class ThrownRevolver extends ThrownGun {

    public ThrownRevolver(){
        mySize = 4;
        myTex = TextureHolder.revolver;
        throwSpeed = 250;
        distanceFromPlayer = 4;

    }

}
