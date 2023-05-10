package com.mygdx.game.entities.guns.elfGuns.Bullets;

import com.mygdx.game.helpers.constants.TextureHolder;

public class PenguinBullet extends GenericBullet{

    public PenguinBullet(){
        super();
        myTexture = TextureHolder.greenBullet;
    }

    @Override
    public void update(double delta) {
        killPlayer = false;
        super.update(delta);
    }

}