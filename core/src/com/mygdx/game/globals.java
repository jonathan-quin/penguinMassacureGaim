package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class globals {

    public static int gamespeed = 1;
    public static boolean showCollision = false;

    public static ShapeRenderer globalShape= new ShapeRenderer();

    public static Vector2 cameraOffset = Vector2.Zero;

    public globals(){

    }

    public static float inverse(float num){
        if (num == 0) return 0;

        return 1f/num;


    }




}
