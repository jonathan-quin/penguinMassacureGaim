package com.mygdx.game.helpers.constants;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Globals {

    public static double gameSpeed = 1;
    public static OrthographicCamera camera;
    public static MapRenderer mapRenderer;

    public static boolean showCollision = false;

    public static boolean sceneJustChanged = true;

    public static Vector2 screenStretch = new Vector2(1,1);

    public static boolean currentlyRewinding = false;

    public static Vector2 screenSize = screenSize = new Vector2(1024,600);

    public static ShapeRenderer globalShape = new ShapeRenderer();

    public static Vector2 cameraOffset = new Vector2(0,0);

    public static HashMap<String, Boolean> lobbyDoorsOpen = new HashMap<>();

    public Globals(){

    }

    public static void globalsInit(){

        lobbyDoorsOpen.put("level1",true);
        lobbyDoorsOpen.put("level2",false);
        lobbyDoorsOpen.put("level3",false);
    }

    public static float inverse(float num){
        if (num == 0) return 0;

        return 1f/num;


    }

    public static double inverse(double num){
        if (num == 0) return 0;

        return 1f/num;
    }




}