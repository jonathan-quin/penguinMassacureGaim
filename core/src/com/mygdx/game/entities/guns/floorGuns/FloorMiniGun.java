package com.mygdx.game.entities.guns.floorGuns;

import com.mygdx.game.entities.guns.penguinGuns.PenguinMiniGun;
import com.mygdx.game.helpers.constants.TextureHolder;

public class FloorMiniGun extends FloorGun{

    public FloorMiniGun() {
        super();
        myGun = PenguinMiniGun.class;
        myTex = TextureHolder.Guns.greenMiniGun;
    }

}
