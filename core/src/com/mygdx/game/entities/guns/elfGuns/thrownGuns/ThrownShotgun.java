package com.mygdx.game.entities.guns.elfGuns.thrownGuns;

import com.mygdx.game.entities.guns.penguinGuns.PenguinShotgun;
import com.mygdx.game.helpers.constants.TextureHolder;

public class ThrownShotgun extends ThrownGun {

    public ThrownShotgun(){
        mySize = 4;
        myTex = TextureHolder.greenShotgun;
        throwSpeed = 300;
        distanceFromPlayer = 4;
        myGun = PenguinShotgun.class;
    }

}