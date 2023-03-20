package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Root {

    public Array<ColliderObject> colliders = new Array<ColliderObject>();

    public Node rootNode;

    public Root(){

    }

    public Array<ColliderObject> getCollidersInLayers(ArrayList<Integer> mask){
        Array<ColliderObject> returnArray = new Array<>();
        for (ColliderObject obj : colliders){
            for (int n : obj.layers){
                boolean added = false;
                for (int o : mask){
                    if (n == o){
                        returnArray.add(obj);
                        added = true;
                        break;
                    }
                }
                if (added) break;
            }
        }
        return returnArray;
    }

    public void update(){
        rootNode.updateCascade();
    }
    public void render(SpriteBatch batch){
        rootNode.renderCascade(batch);
    }

    public void debug(){rootNode.debugCascade();}


}
