package com.mygdx.game.nodes;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.globals;

public class movementNode extends colliderObject{

    public movementNode(root myRoot){
        super(myRoot);
    }

    public movementNode(root myRoot,float x, float y){
        super(myRoot,x,y);
    }

    private Vector2 tempPos = new Vector2(0,0);

    public Vector2 moveAndCollide(Vector2 distance,float delta){

        Vector2 prevPos = new Vector2(position);

        position.set(getFirstCollision(distance.scl(delta)));

        Vector2 difference = new Vector2();

        difference.set(position.x - prevPos.x, position.y - prevPos.y);

        if (false && Math.signum(difference.y) != Math.signum(distance.y)) {

            System.out.println("heyo");
            System.out.println(difference);
            System.out.println(prevPos);
            System.out.println(position);
        }

        return difference.scl(globals.inverse(delta));
    }


    public Vector2 moveAndSlideBruteForce(Vector2 distance){



        if ((int)distance.x == 0 && (int)distance.y == 0) return distance.cpy();

        Vector2 returnVal = new Vector2(0,0);




        if (!testMove(distance)) {
            returnVal.set(distance.x,distance.y);
            position.add(returnVal);
            return returnVal;
        }



        if ( !isZeroApprox(distance.x) && !testMove((int) Math.signum(distance.x),0)) {

            int high = (int) distance.x;
            int low = 0;
            int n = (int)((high + low)/2);

            returnVal.x = distance.x;

            if (!testMove(distance.x,0)) high = low;

            while (Math.abs(low) + 1 < Math.abs(high)){

                if (testMove(n,0)){

                    high = n;
                    returnVal.x = moveTowardsZero(n,1);

                }
                else{

                    low = n;
                    returnVal.x = n;
                }

                n = (int)((high + low)/2);


            }
        }
        else{
            returnVal.x = 0;
        }

        position.add(returnVal);

        if ( !isZeroApprox(distance.y) && !testMove(0,(int) Math.signum(distance.y))) {

            int high = (int) distance.y;
            int low = 0;
            int n = (int)((high + low)/2);

            returnVal.y = distance.y;

            if (!testMove(0, distance.y)) high = low;

            while (Math.abs(low) + 1 < Math.abs(high)){

                if (testMove(0,n)){

                    high = n;
                    returnVal.y = moveTowardsZero(n,1);

                }
                else{

                    low = n;
                    returnVal.y = n;
                }

                n = (int)((high + low)/2);


            }
        }
        else{
            returnVal.y = 0;
        }



        position.add(0,returnVal.y);

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

/*    public boolean testMove(float x, float y){
        updateGlobalPosition();
        position.add(x,y);
        updateParentPos();

        boolean returnValue = isColliding();
        position.sub(x,y);



        return returnValue;
    }*/

    public boolean testMove(float x, float y){
        updateGlobalPosition();
        updateParentPos();

        return sweepTestArray(new Vector2(x,y),myRoot.colliders).collides;
    }

    public Vector2 move(Vector2 distance){

        Vector2 returnVal = Vector2.Zero;

        if (!testMove(distance)) returnVal.set(distance);

        else if (!testMove(distance.x,0)) returnVal.set(distance.x,0);

        else if (!testMove(0,distance.y)) returnVal.set(0,distance.y);

        position.add(returnVal);

        return returnVal;

    }

    public boolean isZeroApprox(float num){

        return (num < 0.01 && num > -0.01);

    }


}
