package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.nodes.GroupHandler;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Raycast;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.lerp;

public class ThrowPathIndicator extends Node {

    public boolean visible = true;
    public boolean justInvisible = true;

    public int circleNum = 20;
    public static Color semiTransparent= new Color(1,1,1,0.3f);

    public Raycast ray;

    public ArrayList<TextureEntity> circles = new ArrayList<TextureEntity>();

    public void setVisible(boolean visible){

        if (!this.visible && visible) justInvisible = true;

        this.visible = visible;
        for (int i = 0; i < children.size(); i++){

            if (children.get(i) instanceof TextureEntity){
                TextureEntity child = (TextureEntity) children.get(i);
                child.setVisible(visible);
            }

        }



    }
    public void ready(){
        addToGroup(GroupHandler.RENDERONTOP);
        addChild(ObjectPool.get(Raycast.class).init(0,0,0,0,false,Raycast.getMaskLayers(LayerNames.WALLS,LayerNames.ELVES,LayerNames.EXPLODINGBARREL)));
        ray = (Raycast) lastChild();

        circles.clear();
        while (circleNum > circles.size()){
            addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.greyCircleHighRes, 0, 0, 0, 0));
            circles.add((TextureEntity) lastChild());
        }
    }

    public void display(Vector2 startingPos ,Vector2 vel, double gravity){


        System.out.println(vel);


        double maxTime = 3;
        double impactTime = maxTime;

        ray.position.set(0,0);

        int maxChecks = 100;
        for (int i = 0; i < maxChecks; i++){
            double time = ((float) i /(float) maxChecks ) * maxTime;

            ray.castTo.x = ray.position.x - (float) (startingPos.x + vel.x * time);
            ray.castTo.y = ray.position.y - (float) (startingPos.y + (vel.y * time + 0.5 * -gravity * time * time));

            ray.updateRaycast();
            if (ray.isColliding()){
                impactTime = time;
                System.out.println("impact!");

                break;
            }

            ray.position.set((float) (startingPos.x + vel.x * time),(float) (startingPos.y + (vel.y * time + 0.5 * -gravity * time * time)));

        }

        circleNum = (int) (impactTime * 6) + 5;


        while (circleNum > circles.size()){
            addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.greyCircleHighRes, 0, 0, 0, 0));
            circles.add((TextureEntity) lastChild());
            lastChild().position.set(circles.get(circles.size()-2).position);
        }
        while (circleNum < circles.size()){
            circles.remove(circles.size()-1);
            removeChild(lastChild());
        }


        for (int i = 0; i < circles.size(); i++){

            double time = ((float)(i)/circles.size()) * impactTime;

            if (time == 0){
                time = ((float)(i + 1)/circles.size()) * impactTime;
            }

            TextureEntity child = (TextureEntity) circles.get(i);
            child.setScale( lerp(0.012f,0.004f ,((float)i / circles.size())) );
            child.setMyColor(semiTransparent);

            /*System.out.println("i/size " + (float)i / children.size());
            System.out.println("I: " + i);
            System.out.println("lerp: " + lerp(0.1f,0.001f ,((float)i / children.size())));

*/
            float newX = (float) (startingPos.x + vel.x * time);
            float newY = (float) (startingPos.y + (vel.y * time + 0.5 * -gravity * time * time));

            if (justInvisible){
                child.position.set(newX,newY);
            } else{
                child.position.lerp(ObjectPool.getGarbage(Vector2.class).set(newX,newY),0.3f);
            }



            if (visible) {
                justInvisible = false;
            }

        }

    }







}
