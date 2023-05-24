package com.mygdx.game.entities.guns.elfGuns.thrownGuns;

import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.TextureHolder;

public class ThrownRevolver extends ThrownGun {

    public ThrownRevolver(){
        mySize = 4;

        throwSpeed = 400; //250;
        distanceFromPlayer = 4;
        myTex = TextureHolder.Guns.greenRevolver;
        myGun = PenguinRevolver.class;

    }

}
