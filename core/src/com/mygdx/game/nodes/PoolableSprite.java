package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PoolableSprite extends Sprite {

    public PoolableSprite(){
        setColor(1,1,1,1);
    }

    public PoolableSprite init(Texture texture){
        if (texture == null) throw new IllegalArgumentException("texture cannot be null.");
        this.setTexture(texture);
        setRegion(0, 0, texture.getWidth(),  texture.getHeight());
        setColor(1, 1, 1, 1);
        setSize(Math.abs(texture.getWidth()), Math.abs( texture.getHeight()));
        setOrigin(texture.getWidth() / 2, texture.getWidth() / 2);

        return this;
    }



}
