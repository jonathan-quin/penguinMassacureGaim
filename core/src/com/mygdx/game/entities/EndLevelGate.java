package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.*;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.StaticNode;
import com.mygdx.game.nodes.TextureEntity;

public class EndLevelGate extends StaticNode {

    public String nextScene = "";

    public String name = "";

    public boolean open = false;

    public EndLevelGate(){
        this(0,0);
    }
    public EndLevelGate( float x, float y) {

        super( x, y, getMaskLayers(LayerNames.PLAYER), getMaskLayers());

    }

    public EndLevelGate init(float x, float y,String name,String nextScene){

        super.init(x,y,getMaskLayers(),getMaskLayers(LayerNames.WALLS));

        this.nextScene = nextScene;

        addChild( ObjectPool.get(TextureEntity.class).init(TextureHolder.endGate,0,0,0,0));
        lastChild().setName("open");


        addChild( ObjectPool.get(TextureEntity.class).init(TextureHolder.endGateClosed,0,0,0,0));
        lastChild().setName("closed");


        updateSprite();

        ( (TextureEntity) lastChild()).setFlip(false,false);

        addChild( ObjectPool.get(CollisionShape.class).init(8,16,0,0));



        return this;
    }

    public void ready(){
        addToGroup("icePlatform");
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        getFirstCollision(ObjectPool.getGarbage(Vector2.class).set(0,0));

        if (lastCollided){
            SceneHandler.setCurrentScene(nextScene);
        }

    }

    public void updateSprite(){

        if (Globals.lobbyDoorsOpen.get(name) != null){
            open = Globals.lobbyDoorsOpen.get(name);

            getChild("open",TextureEntity.class).setVisible(open);
            getChild("closed",TextureEntity.class).setVisible(!open);
        } else {
            open = true;
        }


    }


}
