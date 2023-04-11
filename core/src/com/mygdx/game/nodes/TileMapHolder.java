package com.mygdx.game.nodes;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMapHolder extends Node{
    TiledMap map;
    OrthogonalTiledMapRenderer 
    public void ready(){
        map = new TmxMapLoader().load("iceyTestOutput.tmx");
    }
    public void render(){
        map.
    }

}
