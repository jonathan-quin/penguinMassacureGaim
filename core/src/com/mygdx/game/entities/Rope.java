package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;
public class Rope extends Node{

    public int numPoints;






    private class Point{

        Vector2 position;
        Vector2 prevPosition;

        public Point next;
        public Point previous;

        public float lengthToNext;
        float weight;
        boolean immobile;

        float gravity;

        public Point (Vector2 position,float gravity){
            this.position = new Vector2(position);
            this.gravity = gravity;
        }

        public void update(double delta){

            position.add(ObjectPool.getGarbage(Vector2.class).set(prevPosition.x-position.x,prevPosition.y-position.y).scl((float) delta));

            prevPosition.set(position);

            position.add(0,-gravity);
        }

        public void render(){
            Color tempColor = Globals.globalShape.getColor().cpy();

            Globals.globalShape.setColor(Color.BLUE);

            Globals.globalShape.rect((float) ((position.x - 3)- Globals.cameraOffset.x + (512f * 0.65)), (float) ((position.y - 3)- Globals.cameraOffset.y + (300*0.65)),3 * 2,3* 2);

            Globals.globalShape.setColor(tempColor);
        }

    }

}
