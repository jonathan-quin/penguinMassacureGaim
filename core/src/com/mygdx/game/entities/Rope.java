package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.MathUtilsCustom;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;
public class Rope extends Node{

    public int numPoints;

    public Point[] points;


    public Rope init(float x, float y, int numPoints){
        super.init(x,y);

        points = new Point[numPoints];

        for (int i = 0; i < numPoints; i++){
            if (i == 0){
                points[i] = new Point(MathUtilsCustom.newVec().set(0,0),10,1,5,40,true);
            }else points[i] = new Point(MathUtilsCustom.newVec().set(i * 10,0),10,1,5,40,false);
        }

        for (int i = 0; i < numPoints; i++){
            points[i].setNextPrevious(i + 1 < points.length ? points[i+1] : null , i - 1 > -1 ? points[i-1] : null  );
        }




        return this;
    }

    public void debug(){
        //System.out.println("DEBUGGING");
        for (int i = 0; i < points.length; i++){
            //System.out.println("hey");
            points[i].render();
        }
    }


    public void update(double delta){


        for (int i = 0; i < points.length; i++){
            points[i].update(delta);
        }

        /*for (int i = 0; i < points.length; i++){
            points[i].resolveWithNext(delta);
        }
        for (int i = 0; i < points.length; i++){
            points[i].resolveWithPrevious(delta);
        }*/


    }



    private class Point{

        Vector2 position;
        Vector2 prevPosition;

        double age = 0;

        float prevDelta;

        public Point next;
        public Point previous;

        public float lengthToNext;
        float weight;

        float extensionRigidity;
        float compressionRigidity;
        boolean immobile;

        float gravity;

        public Point(Vector2 position, float lengthToNext, float weight, float extensionRigidity,  float gravity, boolean immobile) {
            this.position = new Vector2( position);
            this.prevPosition = new Vector2(position);

            next = null;
            previous = null;

            this.lengthToNext = lengthToNext;
            this.weight = weight;
            this.extensionRigidity = extensionRigidity;
            this.compressionRigidity = extensionRigidity;

            this.gravity = gravity;
            this.immobile = immobile;

            prevDelta = 1f/60f;
        }

        public Point (Vector2 position, float gravity){
            this(position,10,1,1,gravity,false);

        }

        public void update(double delta){

            age += delta;

            if (immobile) return;




            Vector2 oldPos = newVec().set(position);

            position.sub(newVec().set(prevPosition.x-position.x,prevPosition.y-position.y).scl(1f/prevDelta).scl((float) delta));//.scl((float) delta));

            position.add((float) 0, (float) (-gravity * delta));


            prevPosition.set(oldPos);
            prevDelta = (float) delta;

            System.out.println("age: " + age + " speed: " + prevPosition.dst(position) * (1f/delta));

        }

        public void updateVel(){
            
        }

        public void resolveWithNext(double delta){

            if (next == null) return;

            Vector2 dir = newVec().set(next.position.x - position.x, next.position.y-position.y);

            float distance2Next = position.dst(next.position);

            dir.nor();

            Vector2 dir2 = newVec().set(dir);

            if (!immobile){
            dir.scl((float) Math.max(weight/(weight + (next.immobile ? 0 : next.weight)) * extensionRigidity * delta,distance2Next-lengthToNext));

            dir.scl(distance2Next-lengthToNext < 0 ? -1 : 1);

            position.add(dir);
            }

            if (!next.immobile){
                dir2.scl(Math.max(weight / (weight + next.weight), distance2Next - lengthToNext));

                dir2.scl(distance2Next - lengthToNext < 0 ? 1 : -1);

                next.position.add(dir2);
            }

        }

        public Vector2 newVec(){
            return ObjectPool.getGarbage(Vector2.class);
        }

        public void resolveWithPrevious(double delta){
            if (previous == null) return;

            previous.resolveWithNext(delta);
        }

        public void render(){



            Color tempColor = Globals.globalShape.getColor().cpy();

            Globals.globalShape.setColor(Color.BLUE);

            Globals.globalShape.rect((float) ((globalPosition.x + position.x - 3)- Globals.cameraOffset.x + (512f * 0.65)), (float) ((globalPosition.y + position.y - 3)- Globals.cameraOffset.y + (300*0.65)),3 * 2,3* 2);

            Globals.globalShape.setColor(tempColor);
        }

        public void setNextPrevious(Point next, Point previous) {
            this.next = next;
            this.previous = previous;
        }
    }

}
