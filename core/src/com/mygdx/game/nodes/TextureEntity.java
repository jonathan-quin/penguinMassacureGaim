package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SpritePool;


public class TextureEntity extends Node {

   // Texture sprite;

    public Sprite sprite;

    public Vector2 offset;

    private boolean visible = true;

    public Color getMyColor() {
        return myColor;
    }

    public void setMyColor(Color myColor) {
        this.myColor.set(myColor);
    }

    public Color myColor;



    public TextureEntity(){
        this(null,0,0);
    }

    public TextureEntity(Texture image, float posX, float posY){
        this(image,posX,posY,0,0);
    }

    public TextureEntity(Texture image, float posX, float posY,float offsetX,float offsetY){
        super(posX,posY);
        if (image != null)
            objectGetSprite(image);
        this.offset = new Vector2(offsetX,offsetY);
        myColor = new Color(0,0,0,0);
    }

    public TextureEntity init(Texture image, float posX, float posY,float offsetX,float offsetY){
        super.init(posX,posY);

        visible = true;

        myColor.set( Color.WHITE );


        objectGetSprite(image);

        this.offset.set(offsetX,offsetY);

        setScale(ObjectPool.getGarbage(Vector2.class).set(1,1));

        setRotation(0);

        return this;
    }

    public void objectGetSprite(Texture tex){
        SpritePool.remove(sprite);
        sprite = null;
        sprite = SpritePool.get(tex);

        sprite.setScale(1,1);
        setVisible(true);
        setRotation(0);
        setMyColor(Color.WHITE);
        setFlip(false,false);
    }


    public void setScale(Vector2 scale){
        setScale(scale.x,scale.y);
    }
    public void setScale(float x, float y){
        sprite.setScale(x,y);
    }
    public void setFlip(boolean flipX,boolean flipY)
    {
        sprite.setFlip(flipX,flipY);
    }

    public void setRotation(float degrees){

        sprite.setRotation(degrees);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public double getRotation(){
        return sprite.getRotation();
    }


    public void render(SpriteBatch batch){

        if (visible){
            updateGlobalPosition();


            sprite.setCenter(globalPosition.x + offset.x, globalPosition.y + offset.y);

            sprite.setOrigin(sprite.getWidth() / 2 - offset.x, sprite.getHeight() / 2 - offset.y);

            //batch.setColor(myColor);
            sprite.setColor(myColor);

            sprite.draw(batch);
            //batch.setColor(Color.WHITE);

        }


    }

    public void free(){
        super.free();
    }




}
