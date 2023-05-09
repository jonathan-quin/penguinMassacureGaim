package com.mygdx.game.entities.guns.elfGuns.thrownGuns;

import com.mygdx.game.entities.guns.penguinGuns.PenguinMiniGun;
import com.mygdx.game.helpers.constants.TextureHolder;

public class ThrownMiniGun extends ThrownGun {

    public ThrownMiniGun(){
        mySize = 6;
        myTex = TextureHolder.greenMiniGun;
        throwSpeed = 250;
        distanceFromPlayer = 5;
        myGun = PenguinMiniGun.class;

    }

}