package com.mygdx.game.entities.guns.penguinGuns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.Bullets.GenericBullet;
import com.mygdx.game.entities.guns.elfGuns.thrownGuns.ThrownGun;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;

import static java.lang.Math.*;

public class PenguinGun extends Node {

    public double moveSpeed;

    public double rotation;
    public Class<? extends ThrownGun> throwClass;

    protected double aimSpeed;
    protected double fixedAimSpeed;

    public int startingAmmo;

    public int ammoLeft;

    public double recoil;
    public Texture tex;
    public Vector2 texOffset;

    public double timeUntilNextShot;
    protected double fireRate; //shots per second

    public Node bulletHolder;

    private TextureEntity sprite;

    public PenguinGun() {
    }

    public PenguinGun init(){
        super.init(0,0);
        return this;
    }

    public void ready(){
        addChild(ObjectPool.get(TextureEntity.class).init(tex,0f,0f,texOffset.x,texOffset.y));
        lastChild().setName("gunSprite");
        sprite = (TextureEntity) lastChild();
        //sprite.position.set(texOffset.cpy().scl(-1));
    }

    public void update(double delta){
//        aimAt(globalPosition, Utils.getGlobalMousePosition());
//
//        if (canShoot() && Gdx.input.isTouched()) {
//
//            for (GenericBullet bullet : shoot(ObjectPool.getGarbage(Vector2.class).set(globalPosition).sub(0, -2))) {
//                bulletHolder.addChild(bullet);
//            }
//        }

    }

    public GenericBullet[] shoot(){
        timeUntilNextShot = 1/fireRate;
        ammoLeft--;

        return getBullets(globalPosition);

    }

    protected GenericBullet[] getBullets(Vector2 pos) {
        return null;
    }

    public void updateTimeUntilNextShot(double num){
        timeUntilNextShot = max(0,timeUntilNextShot-num);
    }

    public boolean canShoot(){

        return timeUntilNextShot == 0 && ammoLeft > 0;
    }

    public void aimAt(Vector2 target, double delta){

        double difference = differenceBetweenAngles(rotation,getTargetRotation(globalPosition,target));

        rotation = rotation + difference * aimSpeed * 60 * delta;

        if (abs(difference)>fixedAimSpeed) {
            rotation = rotation + signum(difference) * fixedAimSpeed * 60 * delta;
        }

        rotation = rotation % (2*PI);

        setRotation(rotation);

        //System.out.println(rotation);
    }

    private double getTargetRotation(Vector2 myPos, Vector2 target) {

        return Math.atan2(target.y - myPos.y,target.x - myPos.x);

    }

    public boolean shouldFlipY(){
        return abs(differenceBetweenAngles(rotation,0)) > PI/2;
    }

    public void setFlip(){
        boolean shouldFlip = shouldFlipY();
        sprite.setFlip(false,shouldFlipY());

        if (shouldFlip){
            sprite.offset.y = -texOffset.y;
        }else{
            sprite.offset.y = texOffset.y;
        }

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

    public void setRotation(double rotation) {
        this.rotation = rotation;

        sprite.setRotation(toDegrees(rotation));
    }
}
