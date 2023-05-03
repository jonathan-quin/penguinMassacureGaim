package com.mygdx.game.entities.guns.elfGuns.thrownGuns;

import com.mygdx.game.helpers.constants.TextureHolder;

public class ThrownMiniGun extends ThrownGun {

    public ThrownMiniGun(){
        mySize = 6;
        myTex = TextureHolder.miniGun;
        throwSpeed = 150;
        distanceFromPlayer = 5;

    }

}