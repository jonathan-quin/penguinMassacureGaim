package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.utilities.TileMapInfo;

import java.util.ArrayList;

public class TileMapProcessor extends ColliderObject{

    TileMapInfo tileMapInfo;

    public TileMapProcessor(){
        super.init(0,0,getMaskLayers(LayerNames.DEFAULT),getMaskLayers(LayerNames.WALLS));
    }

    public TileMapProcessor init(TileMapInfo tileMapInfo){
        super.init(0,0,getMaskLayers(LayerNames.DEFAULT),getMaskLayers(LayerNames.WALLS));
        //setMaskLayers(getMaskLayers(LayerNames.DEFAULT),getMaskLayers(LayerNames.WALLS) );

        this.tileMapInfo = tileMapInfo;

        return this;
    }

    public void ready(){

        addChild( ObjectPool.get(TextureEntity.class).init(tileMapInfo.mapTexture,tileMapInfo.mapTexture.getWidth()/2,-tileMapInfo.mapTexture.getHeight()/2,0,0));
        getNewestChild().setName("sprite");

        ( (TextureEntity) getNewestChild()).setFlip(false,false);

        for (CollisionShape shape : tileMapInfo.getShapes()){
            addChild(shape);
        }





    }

    public void render(SpriteBatch batch){
    }


}
