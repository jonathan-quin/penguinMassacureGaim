package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.helpers.ObjectPool;
import com.mygdx.game.helpers.poolableSprite;


public class TextureEntity extends Node {

   // Texture sprite;

    Sprite sprite;
    Vector2 size;

    public TextureEntity(){
        this(null,0,0,0,0);
    }

    public TextureEntity(Texture image, int posX, int posY, int width, int height){
        super(posX,posY);
        sprite = ((poolableSprite) ObjectPool.get(poolableSprite.class)).init(image);

//        if (image != null){
//            float srcWidth = image.getWidth();
//            float srcHeight = image.getHeight();
//            sprite.setTexture(image);
//            sprite.setRegion(0, 0, srcWidth, srcHeight);
//            sprite.setColor(1, 1, 1, 1);
//            sprite.setSize(Math.abs(srcWidth), Math.abs(srcHeight));
//            sprite.setOrigin(sprite.getWidth() / 2,  sprite.getHeight() / 2);
//        }

        //sprite = new Sprite(image);
        size = ((Vector2) ObjectPool.get(Vector2.class)).set(width,height);
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
        //sprite.setBounds(globalPosition.x - size.x/2,globalPosition.y-size.y/2,size.x,size.y);

        sprite.draw(batch);

        //super.render(batch);
    }

    public void free(){
        ObjectPool.remove(sprite);
        ObjectPool.remove(size);

    }




}
