package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.TextureEntity;

public class MouseFollowSprite extends TextureEntity {


    public void update(double delta){

        Vector2 tempValue = Utils.getGlobalMousePosition();

        tempValue.set(16 * (int) (tempValue.x/16),16 * (int) (tempValue.y/16));

        position = tempValue;

    }

}
