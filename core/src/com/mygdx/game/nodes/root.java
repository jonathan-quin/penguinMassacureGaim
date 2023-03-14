package com.mygdx.game.nodes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class root {

    public Array<colliderObject> colliders = new Array<colliderObject>();

    public node rootNode;

    public root(){

    }

    public Array<colliderObject> getCollidersInLayers(int[] mask){
        Array<colliderObject> returnArray = new Array<>();
        for (colliderObject obj : colliders){
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
