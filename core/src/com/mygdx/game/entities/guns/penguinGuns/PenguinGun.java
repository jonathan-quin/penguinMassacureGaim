package com.mygdx.game.entities.guns.penguinGuns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.ElfBullet;

import static java.lang.Math.*;

public class PenguinGun {

    public double moveSpeed;

    public double rotation;

    protected double aimSpeed;
    protected double fixedAimSpeed;

    public int startingAmmo;

    public int ammoLeft;

    public double recoil;
    public Texture tex;
    public Vector2 texOffset;

    public double timeUntilNextShot;
    protected double fireRate; //shots per second

    public PenguinGun() {
    }

    public void init(){

    }

    public ElfBullet[] shoot(Vector2 pos){
        timeUntilNextShot = 1/fireRate;

        return getBullets(pos);

    }

    protected ElfBullet[] getBullets(Vector2 pos) {
        return null;
    }

    public void updateTimeUntilNextShot(double num){
        timeUntilNextShot = max(0,timeUntilNextShot-num);
    }

    public boolean canShoot(){

        return timeUntilNextShot == 0;
    }

    public void aimAt(Vector2 myPos, Vector2 target){

        double difference = differenceBetweenAngles(rotation,getTargetRotation(myPos,target));

        rotation = rotation + difference * aimSpeed;

        if (abs(difference)>fixedAimSpeed)
            rotation = rotation + signum(difference) * fixedAimSpeed;
    }

    private double getTargetRotation(Vector2 myPos, Vector2 target) {

        return Math.atan2(target.y - myPos.y,target.x - myPos.x);

    }

    public boolean shouldFlipY(){
        return abs(differenceBetweenAngles(rotation,0)) > PI/2;
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
