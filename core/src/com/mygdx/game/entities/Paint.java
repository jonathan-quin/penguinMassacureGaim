package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;

public class Paint extends Node implements TimeRewindInterface {


    Color myColor;
    protected TextureEntity sprite;

    public Paint(){
        myColor = new Color(0,0,0,0);
    }
    public void ready(){

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.whitePixel,0,0,0,0));

        sprite = (TextureEntity) lastChild();
        sprite.setMyColor(myColor);
        addToGroup("rewind");

    }

    public Paint init(float x, float y, Color color){
        init(x,y);
        myColor.set(color);

        return this;
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
        a.add(new Color(myColor));

        lastSave = a;
        return a;
    }

    @Override
    public <T> T init() {
        init(0,0,Color.WHITE);
        return null;
    }

    @Override
    public <T> T load(Object... vars) {
        position.set((Vector2) vars[1]);

        myColor.set((Color) vars[2]);
        sprite.setMyColor(myColor);
        return null;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }

}
