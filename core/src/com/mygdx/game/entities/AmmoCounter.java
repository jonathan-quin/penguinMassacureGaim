package com.mygdx.game.entities;

import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.nodes.GroupHandler;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;



public class AmmoCounter extends Node {

    public void ready(){
        addToGroup(GroupHandler.RENDERONTOP);
    }

    public void display(int num, double reloadTimeLeft, double maxReload){

        while (num > children.size()){
            addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.countBullet, -children.size() * 16, 0, 0, 0));
            //((TextureEntity)lastChild()).setScale(2,2);
        }
        while (num < children.size()){
            removeChild(lastChild());
        }

        for (int i = 0; i < children.size(); i++){
            TextureEntity child = (TextureEntity) children.get(i);

            if (i != children.size() -1){
                child.setRotation(90);
            }
            else{
                child.setRotation(90 - (90 * (maxReload - reloadTimeLeft)/maxReload));
            }



        }

    }


}
