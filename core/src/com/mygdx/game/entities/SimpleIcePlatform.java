package com.mygdx.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.helpers.TextureHolder;
import com.mygdx.game.helpers.Utils;
import com.mygdx.game.nodes.*;

public class SimpleIcePlatform extends StaticNode {

    public SimpleIcePlatform(Root myRoot, float x, float y) {

        super(myRoot, x, y, getMaskLayers(0), getMaskLayers(0));

        Texture iceTexture = TextureHolder.iceTexture;


        addChild(new TextureEntity(iceTexture,0,0));
        getNewestChild().name = "sprite";

        addChild(new CollisionShape(96/2,16,0,0));

        addToGroup("icePlatform");

        //addChild(new collisionShape(96/2,16/2,8,4));
        //addChild(new collisionShape(96/2,160/2,-80,4));

    }

    @Override
    public void update(double delta) {
       super.update(delta);

       if (Utils.is_on_screen(position,0,0)){
           ((TextureEntity) getChild("sprite")).setRotation(0);
       }
       else{
           ((TextureEntity) getChild("sprite")).setRotation(180);
       }
    }


}
