package com.mygdx.game.helpers.constants;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Globals {

    public static int gamespeed = 1;
    public static OrthographicCamera camera;
    public static MapRenderer mapRenderer;

    public static boolean showCollision = false;

    public static boolean sceneJustChanged = true;

    public static Vector2 screenStretch = new Vector2(1,1);



    public static Vector2 screenSize = screenSize = new Vector2(1024,600);

    public static ShapeRenderer globalShape = new ShapeRenderer();

    public static Vector2 cameraOffset = new Vector2(0,0);

    public Globals(){

    }



    public static float inverse(float num){
        if (num == 0) return 0;

        return 1f/num;


    }




}
