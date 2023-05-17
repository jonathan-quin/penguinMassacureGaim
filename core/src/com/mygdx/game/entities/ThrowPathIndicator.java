package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.nodes.GroupHandler;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;

public class ThrowPathIndicator extends Node {

    public boolean visible = true;

    public void setVisible(boolean visible){
        this.visible = visible;
        for (int i = 0; i < children.size(); i++){

            TextureEntity child = (TextureEntity) children.get(i);
            child.setVisible(visible);
        }
    }
    public void ready(){
        addToGroup(GroupHandler.RENDERONTOP);
    }

    public void display(Vector2 startingPos ,Vector2 vel, double gravity){

        while (10 > children.size()){
            addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.countBullet, -children.size() * 16, 0, 0, 0));
            //((TextureEntity)lastChild()).setScale(2,2);
        }
        while (10 < children.size()){
            removeChild(lastChild());
        }

        for (int i = 0; i < children.size(); i++){

            

            TextureEntity child = (TextureEntity) children.get(i);

            child.position.x = startingPos.x + vel.x * i;

            child.position.y = 0; // (float) (startingPos.y + (vel.y * i + 0.5 * gravity * i*i));

        }

    }





}
