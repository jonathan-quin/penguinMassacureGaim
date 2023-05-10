package com.mygdx.game.entities.guns.elfGuns.Bullets;

import com.mygdx.game.helpers.constants.TextureHolder;

public class ElfBullet extends GenericBullet{

    public ElfBullet(){
        super();
        myTexture = TextureHolder.redBullet;
    }

    public void update(double delta) {
        killPlayer = true;
        super.update(delta);
    }

}
