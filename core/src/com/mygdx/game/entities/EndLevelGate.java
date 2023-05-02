package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.*;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.StaticNode;
import com.mygdx.game.nodes.TextureEntity;
import com.mygdx.game.nodes.TimeRewindRoot;

public class EndLevelGate extends StaticNode {

    public String nextScene = "";

    public String doorName = "";
    public String openWhenEntered = "";

    public boolean open = false;

    public EndLevelGate(){
        this(0,0);
    }
    public EndLevelGate( float x, float y) {

        super( x, y, getMaskLayers(LayerNames.PLAYER), getMaskLayers());

    }

    public EndLevelGate init(float x, float y,String doorName,String openWhenEntered,String nextScene){

        super.init( x, y, getMaskLayers(LayerNames.PLAYER), getMaskLayers());

        this.nextScene = nextScene;

        this.doorName = doorName;
        this.openWhenEntered = openWhenEntered;

        addChild( ObjectPool.get(TextureEntity.class).init(TextureHolder.endGate,0,0,0,0));
        lastChild().setName("open");


        addChild( ObjectPool.get(TextureEntity.class).init(TextureHolder.endGateClosed,0,0,0,0));
        lastChild().setName("closed");


        updateSprite();

        ( (TextureEntity) lastChild()).setFlip(false,false);

        addChild( ObjectPool.get(CollisionShape.class).init(8,16,0,-16));



        return this;
    }

    public void ready(){
        addToGroup("icePlatform");
    }

    @Override
    public void update(double delta) {
        super.update(delta);

         updateVIPStatus();

        if (open)
            getFirstCollision(ObjectPool.getGarbage(Vector2.class).set(0,0));

        if (lastCollided && !((Player) lastCollider).dead && open){

            if (!(SceneHandler.getCurrentRoot() instanceof TimeRewindRoot)){
                SceneHandler.setCurrentScene(nextScene);
            }else{
                ((TimeRewindRoot) SceneHandler.getCurrentRoot()).playBackAndChangeScene(nextScene);
            }


            if (Globals.lobbyDoorsOpen.containsKey(openWhenEntered)){
                Globals.lobbyDoorsOpen.put(openWhenEntered,true);
                //System.out.println("opened a door!");
            }

        }

    }

        public void updateSprite(){

            if (Globals.lobbyDoorsOpen.containsKey(doorName)){
                open = Globals.lobbyDoorsOpen.get(doorName);
                //System.out.println("found myself " + doorName);

            } else {
                open = true;
            }

            getChild("open",TextureEntity.class).setVisible(open);
            getChild("closed",TextureEntity.class).setVisible(!open);


        }

    public void updateVIPStatus(){

        if (!Globals.lobbyDoorsOpen.containsKey(doorName)){
            open = getGroupHander().getNodesInGroup("VIP").size() == 0;
            //System.out.println("found myself " + doorName);

        }

        getChild("open",TextureEntity.class).setVisible(open);
        getChild("closed",TextureEntity.class).setVisible(!open);


    }


}
