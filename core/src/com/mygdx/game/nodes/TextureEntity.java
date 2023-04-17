package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class TextureEntity extends Node {

    Texture sprite;
    Vector2 size;

    public TextureEntity(Texture image, int posX, int posY, int width, int height){
        sprite = image;
        position = new Vector2(posX,posY);
        size = new Vector2(width,height);
    }



    public void render(SpriteBatch batch){

        updateGlobalPosition();

        batch.draw(sprite,globalPosition.x - size.x/2,globalPosition.y-size.y/2);


        //super.render(batch);
    }




}
