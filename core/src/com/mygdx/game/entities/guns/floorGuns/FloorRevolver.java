package com.mygdx.game.entities.guns.floorGuns;

import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.TextureHolder;

public class FloorRevolver extends FloorGun{

    public FloorRevolver() {
        super();
        myGun = PenguinRevolver.class;
        myTex = TextureHolder.greenRevolver;
    }

}
