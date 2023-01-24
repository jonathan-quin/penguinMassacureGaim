package com.mygdx.game.nodes;

import com.badlogic.gdx.math.Vector2;

public class movementNode extends colliderObject{

    public movementNode(root myRoot){
        super(myRoot);
    }

    public movementNode(root myRoot,float x, float y){
        super(myRoot,x,y);
    }

    private Vector2 tempPos = new Vector2(0,0);
    public Vector2 moveAndSlide(Vector2 distance){

        if (distance.x == 0 && distance.y == 0) return Vector2.Zero;

        Vector2 returnVal = Vector2.Zero;

        tempPos.set(position);

        if (!testMove(distance)) {
            returnVal.set(distance);
            position.add(returnVal);
            return returnVal;
        }

        System.out.println("doing tests");

        if ( (int)distance.x != 0 && !testMove((int) Math.signum(distance.x),0)) {
            int high = (int) distance.x;
            int low = (int) Math.signum(distance.x);

            int n = (int)((high + low)/2);

            System.out.println(low);

            System.out.println("move2zero " + moveTowardsZero(low,-1));

            while (Math.abs(low) + 1 < Math.abs(high)){

                if (testMove(n,0)){
                  // System.out.println(n);
                    high = n;
                    returnVal.x = moveTowardsZero(n,2);
                }
                else{
                   // System.out.println("found a way to not collide");
                    low = n;
                    returnVal.x = moveTowardsZero(n,1);
                }

                System.out.println(high + " " + low);


                n = (int)((high + low)/2);
                System.out.println(n);

            }




        }



        position.add(returnVal);
        System.out.println(returnVal);
       System.out.println(position);
        return returnVal;

    }

    public int moveTowardsZero(int num, int amount){
        if (num > 0) return Math.max(num-amount,0);
        if (num < 0) return Math.min(num+amount,0);
        return 0;
    }

    public boolean testMove(Vector2 distance){
        return testMove(distance.x, distance.y);
    }

    public boolean testMove(float x, float y){
        position.add(x,y);
        updateParentPos();
        boolean returnValue = isColliding();
        position.sub(x,y);
        return returnValue;
    }

    public Vector2 move(Vector2 distance){

        Vector2 returnVal = Vector2.Zero;

        tempPos.set(position);

        if (!testMove(distance)) returnVal.set(distance);

        else if (!testMove(distance.x,0)) returnVal.set(distance.x,0);

        else if (!testMove(0,distance.y)) returnVal.set(0,distance.y);

        position.add(returnVal);

        return returnVal;

    }


}
