package com.mygdx.game.helpers.utilities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.TimeParticle;
import com.mygdx.game.helpers.constants.ObjectPool;

public class MathUtilsCustom {
    public static double moveTowardsZero(double num, double amount){
        if (num > 0) return Math.max(num-amount,0);
        if (num < 0) return Math.min(num+amount,0);
        return 0;
    }

    public static double moveTowardsNum(double num, double targetNum, double amount){
        if (num > targetNum) return Math.max(num-amount,targetNum);
        if (num < targetNum) return Math.min(num+amount,targetNum);
        return targetNum;
    }

    public static double differenceBetweenAngles(double Angle, double Bngle){

        Angle = (Angle % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);
        Bngle = (Bngle % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);

        double returnVal = Bngle - Angle;

        if (returnVal > Math.PI) returnVal -= 2 * Math.PI;
        else if (returnVal < -Math.PI) returnVal += 2 * Math.PI;

        if (isEqualApprox(Angle,Bngle,0.01)) returnVal = 0;
        if (isEqualApprox(Angle,Bngle - (2*Math.PI),0.01)) returnVal = 0;


        return returnVal;
    }

    public static boolean isEqualApprox(double a, double b, double tolerance){
        if (a + tolerance > b && a - tolerance < b) return true;
        return false;
    }

    static public double lerpD (double fromValue, double toValue, double progress) {
        return fromValue + (toValue - fromValue) * progress;
    }


    public static Vector2 newVec() {
        return ObjectPool.getGarbage(Vector2.class);
    }

}
