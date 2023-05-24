package com.mygdx.game.entities.guns.floorGuns;

import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.TextureHolder;

public class FloorSniper extends FloorGun{

    public FloorSniper() {
        super();
        myGun = PenguinRevolver.class;
        myTex = TextureHolder.Guns.greenRevolver;
    }

}