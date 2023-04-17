package com.mygdx.game.entities.guns.elfGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class ElfGun {

    public double moveSpeed;
    public double distanceMin;
    public double distanceMax;

    public double rotation;

    protected double aimedTolerance;
    protected double aimSpeed;

    public Texture tex;
    public Vector2 texOffset;

    public double timeUntilNextShot;
    protected double fireRate; //shots per second

    public ElfGun() {
    }

    public void init(){

    }

    public void shoot(){
        timeUntilNextShot = 1/fireRate;
    }

    public void updateTimeUntilNextShot(double num){
        timeUntilNextShot = max(0,timeUntilNextShot-num);
    }

    public boolean canShoot(Vector2 myPos, Vector2 target){

        return timeUntilNextShot == 0 && abs(differenceBetweenAngles(getTargetRotation(myPos,target),rotation)) < aimedTolerance;
    }

    public void aimAt(Vector2 myPos, Vector2 target){
        rotation = rotation + differenceBetweenAngles(rotation,getTargetRotation(myPos,target)) * aimSpeed;
    }

    private double getTargetRotation(Vector2 myPos, Vector2 target) {

        return Math.atan2(target.y - myPos.y,target.x - myPos.x);

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

}
