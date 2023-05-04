package com.mygdx.game.entities.guns.floorGuns;

import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.entities.guns.penguinGuns.PenguinShotgun;
import com.mygdx.game.helpers.constants.TextureHolder;

public class FloorShotgun extends FloorGun{

    public FloorShotgun() {
        super();
        myGun = PenguinShotgun.class;
        myTex = TextureHolder.shotgun;
    }

}
