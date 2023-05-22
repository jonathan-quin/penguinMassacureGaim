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
                points[i] = new Point(MathUtilsCustom.newVec().set(0,0),50,1,5,4,true);
            }else points[i] = new Point(MathUtilsCustom.newVec().set(i * 10,0),50,1,5,4,false);
        }

        for (int i = 0; i < numPoints; i++){
            points[i].setNextPrevious(i + 1 < points.length ? points[i+1] : null , i - 1 > -1 ? points[i-1] : null  );
        }

        points[2].debug = true;



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

        points[0].position.set(globalPosition).scl(0.3f);

        for (int i = 0; i < points.length; i++){
            points[i].update(delta);
        }

        for (int i = 0; i < points.length; i++){
            points[i].resolveWithNext(delta);
        }
        for (int i = 0; i < points.length; i++){
            points[i].resolveWithPrevious(delta);
        }

        for (int i = 0; i < points.length; i++) {
            points[i].vel.set(points[i].prevPosition.x - position.x, points[i].prevPosition.y - position.y).scl((float) Globals.inverse(delta));
        }

    }



    private class Point{

        Vector2 position;
        Vector2 prevPosition;

        Vector2 vel;

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

        public boolean debug = false;

        public Point(Vector2 position, float lengthToNext, float weight, float extensionRigidity,  float gravity, boolean immobile) {
            this.position = new Vector2( position);
            this.prevPosition = new Vector2(position);

            vel = new Vector2(0,0);

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


             // * delta;
//            if (debug){
//                System.out.println("age: " + age + " speed: " + prevPosition.dst(position));// * (1f/delta));
//            }


            updateVel(delta);


            if (false && debug){
                System.out.println("pos: " + position);
                System.out.println();
            }

        }

        public void updateVel(double delta){

            //delta = 0.0166666;

            prevPosition.set(position);

            position.sub(vel.scl((float) delta));
            position.y -= gravity * delta;

        }


        public void resolveWithNext(double delta){

            if (next == null) return;

            float distance2Next = position.dst(next.position);

//            if (!immobile) {
//                position.sub(newVec().set(next.position.x - position.x, next.position.y - position.y).nor().scl((float) (distance2Next - lengthToNext * 0.5)));
//                next.position.sub(newVec().set(next.position.x - position.x, next.position.y - position.y).nor().scl((float) (distance2Next - lengthToNext * -0.5)));
//            }
//
//            if (debug){
//                System.out.println( newVec().set(next.position.x - position.x, next.position.y - position.y).nor().scl(distance2Next - lengthToNext) );
//            }

            Vector2 dir = newVec().set(next.position.x - position.x, next.position.y-position.y);



            dir.nor();

            Vector2 dir2 = newVec().set(dir);

            if (!immobile){
            //dir.scl((float) Math.max(weight/(weight + (next.immobile ? 0 : next.weight)) * extensionRigidity * delta,distance2Next-lengthToNext));

                dir.scl((float) (distance2Next - lengthToNext));

                //dir.scl(10);

                dir.scl(distance2Next-lengthToNext < 0 ? -1 : 1);

                position.add(dir);
            }

            if (!next.immobile){
                //dir2.scl(Math.max(weight / (weight + next.weight), distance2Next - lengthToNext));

                dir2.scl((float) distance2Next - lengthToNext);

                //dir2.scl(10);

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
