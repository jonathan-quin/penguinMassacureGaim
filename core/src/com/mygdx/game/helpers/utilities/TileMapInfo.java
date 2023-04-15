package com.mygdx.game.helpers.utilities;

import com.badlogic.gdx.graphics.Texture;

public class TileMapInfo {

    public Texture mapTexture;

    public int[] collisionInfo;

    public int width;
    public int height;
    public int tileSize;

    public TileMapInfo(Texture mapTexture, int width, int height, int tileSize, int[] collisionInfo) {
        this.mapTexture = mapTexture;
        this.collisionInfo = collisionInfo;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
    }



}
