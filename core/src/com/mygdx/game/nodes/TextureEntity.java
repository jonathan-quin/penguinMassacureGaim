package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class TextureEntity extends Node {

   // Texture sprite;

    Sprite sprite;
    Vector2 size;

    public TextureEntity(Texture image, int posX, int posY, int width, int height){
        sprite = new Sprite(image);
        position = new Vector2(posX,posY);
        size = new Vector2(width,height);
    }

    public void setFlip(boolean flipX,boolean flipY)
    {
        sprite.setFlip(flipX,flipY);
    }

    public void setRotation(float degrees){

        sprite.setRotation(degrees);
    }

    public boolean getFlipX(){
        return sprite.isFlipX();
    }

    public boolean getFlipY(){
        return sprite.isFlipY();
    }

    public void setRotation(double degrees){
        setRotation((float)degrees);
    }


    public void render(SpriteBatch batch){

        updateGlobalPosition();

        //batch.draw(sprite,globalPosition.x - size.x/2,globalPosition.y-size.y/2);
        sprite.setPosition(globalPosition.x - size.x/2,globalPosition.y-size.y/2);
        sprite.draw(batch);

        //super.render(batch);
    }




}
