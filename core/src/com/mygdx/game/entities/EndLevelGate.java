package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.*;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.StaticNode;
import com.mygdx.game.nodes.TextureEntity;
import com.mygdx.game.nodes.TimeRewindRoot;

import java.util.ArrayList;

public class EndLevelGate extends StaticNode implements TimeRewindInterface {

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

        lastSave = null;

        return this;
    }

    public void ready(){

        addToGroup("icePlatform");
        addToGroup("rewind");
    }

    @Override
    public void update(double delta) {
        super.update(delta);

         updateVIPStatus();

         if (Globals.sceneJustChanged) return;

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

    @Override
    public ArrayList<Object> save() {
        ArrayList<Object> a = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        a.clear();

        a.add((ArrayList<Object>) ObjectPool.get(ArrayList.class)); //0
        ((ArrayList)a.get(0)).clear();
        ((ArrayList)a.get(0)).add(this.getClass());
        ((ArrayList)a.get(0)).add(this.getParent());
        ((ArrayList)a.get(0)).add(lastSave);

        a.add(new Vector2(position)); //1
        a.add(open);
        a.add(nextScene);
        a.add(openWhenEntered);

        lastSave = a;
        return a;
    }

    @Override
    public <T> T init() {
        init(0,0,"","","");

        lastSave = null;
        return null;
    }

    @Override
    public <T> T load(Object... vars) {
        position.set((Vector2) vars[1]);
        open = (boolean) vars[2];

        getChild("open",TextureEntity.class).setVisible(open);
        getChild("closed",TextureEntity.class).setVisible(!open);

        nextScene = (String) vars[3];
        openWhenEntered = (String) vars[4];

        return null;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }


}
