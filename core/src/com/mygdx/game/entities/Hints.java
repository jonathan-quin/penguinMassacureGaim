package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.GroupHandler;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;

public class Hints extends Node implements TimeRewindInterface {


    Texture myTexture;

    Vector2 offset;

    public Hints(){
        offset = new Vector2(0,0);
    }
    public void ready(){


        addChild(ObjectPool.get(TextureEntity.class).init(myTexture,0,0,0,0));
        ((TextureEntity) lastChild()).setScale(0.5f,0.5f);

        addToGroup(GroupHandler.RENDERONTOP);
        addToGroup("rewind");

    }

    public Hints init(float x, float y, Texture texture){
        init(x,y);

        offset.set(x,y);

        myTexture = texture;
        lastSave = null;

        return this;
    }

    public void update(double delta){

        Vector2 newPos  = ObjectPool.getGarbage(Vector2.class).set(0,0);

        try{
            newPos = ObjectPool.getGarbage(Vector2.class).set(getGroupHander().getNodesInGroup("player").get(0).position);

        }catch (Exception ex){

        }

        position.set(newPos).add(offset);




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
        a.add(myTexture);
        a.add(new Vector2(offset));


        lastSave = a;
        return a;
    }

    @Override
    public <T> T init() {
        init(0,0,TextureHolder.whitePixel);

        lastSave = null;
        return null;
    }

    @Override
    public <T> T load(Object... vars) {
        position.set((Vector2) vars[1]);
        myTexture = ((Texture) vars[2]);
        offset.set((Vector2) vars[3]);



        addChild(ObjectPool.get(TextureEntity.class).init(myTexture,0,0,0,0));
        ((TextureEntity) lastChild()).setScale(0.5f,0.5f);

        return null;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }


}
