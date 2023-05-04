package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;

import static java.lang.Math.signum;

public class ParalaxBackground extends Node implements TimeRewindInterface {


    Texture myTexture;

    public ParalaxBackground(){

    }
    public void ready(){

        addChild(ObjectPool.get(TextureEntity.class).init(myTexture,-myTexture.getWidth() + 1,0,0,0));

        addChild(ObjectPool.get(TextureEntity.class).init(myTexture,0,0,0,0));

        addChild(ObjectPool.get(TextureEntity.class).init(myTexture,myTexture.getWidth() - 1,0,0,0));

        addToGroup("rewind");

    }

    public ParalaxBackground init(float x, float y, Texture texture){
        init(x,y);

        myTexture = texture;
        lastSave = null;

        return this;
    }

    public void update(double delta){

        Vector2 newPos = ObjectPool.getGarbage(Vector2.class).set(Globals.cameraOffset.x,Globals.cameraOffset.y).scl(0.2F,0);

        position.set(newPos);

        for (Node child : children) {
            child = (TextureEntity) child;

            if (!Utils.is_on_screen(child.globalPosition.x,child.globalPosition.y,myTexture.getWidth(),100000)){
                child.position.x += myTexture.getWidth() * 3 * signum(Globals.cameraOffset.x - child.globalPosition.x);
            }
        }


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

        a.add(children.get(0).position.x);
        a.add(children.get(1).position.x);
        a.add(children.get(2).position.x);

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

        for (int i = 2; i >= 0; i--){
            removeChild(children.get(i));
        }

        addChild(ObjectPool.get(TextureEntity.class).init(myTexture,-myTexture.getWidth(),0,0,0));

        addChild(ObjectPool.get(TextureEntity.class).init(myTexture,0,0,0,0));

        addChild(ObjectPool.get(TextureEntity.class).init(myTexture,myTexture.getWidth(),0,0,0));


        children.get(0).position.x = (float) vars[3];
        children.get(1).position.x = (float) vars[4];
        children.get(2).position.x = (float) vars[5];

        return null;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }


}
