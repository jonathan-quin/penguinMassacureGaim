package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TileMapInfo;

public class TileMapProcessor extends ColliderObject{

    TileMapInfo tileMapInfo;

    public static final Color edg32Black = new Color(24f/256,20f/256,37f/256,1  ); //new Color(181425);

    public TileMapProcessor(){
        super.init(0,0,getMaskLayers(LayerNames.DEFAULT),getMaskLayers(LayerNames.WALLS));
    }

    public TileMapProcessor init(TileMapInfo tileMapInfo){
        super.init(0,0,getMaskLayers(),getMaskLayers(LayerNames.WALLS));

        this.tileMapInfo = tileMapInfo;

        return this;
    }

    public void ready(){

        addChild( ObjectPool.get(TextureEntity.class).init(tileMapInfo.mapTexture,tileMapInfo.mapTexture.getWidth()/2,-tileMapInfo.mapTexture.getHeight()/2,0,0));
        lastChild().setName("sprite");

        ( (TextureEntity) lastChild()).setFlip(false,false);

        for (CollisionShape shape : tileMapInfo.getShapes()){
            addChild(shape);
        }

        addChild( ObjectPool.get(TextureEntity.class).init(TextureHolder.whitePixel,tileMapInfo.mapTexture.getWidth()/2,-tileMapInfo.mapTexture.getHeight()/2,0,0));
        lastChild().setName("underground");
        getChild("underground",TextureEntity.class).setScale(tileMapInfo.width * tileMapInfo.tileSize,700);
        getChild("underground",TextureEntity.class).setMyColor(edg32Black);
        getChild("underground",TextureEntity.class).position.set(tileMapInfo.width * tileMapInfo.tileSize/2f,-tileMapInfo.height * tileMapInfo.tileSize - 350);

        ( (TextureEntity) lastChild()).setFlip(false,false);

    }

    public void render(SpriteBatch batch){
    }

    public void debug(){


    }


}
