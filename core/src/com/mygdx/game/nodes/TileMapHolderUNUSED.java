package com.mygdx.game.nodes;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.helpers.constants.Globals;

public class TileMapHolderUNUSED extends Node {
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    public void ready(){

        map = new TmxMapLoader().load("simpleSnowyLevel1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1);

    }
    public void update(){
        renderer.setView(Globals.camera);
        renderer.render();
    }

}
