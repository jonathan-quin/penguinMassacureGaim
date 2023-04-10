package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Utils {

    public static boolean is_on_screen(Vector2 pos,float toleranceX,float toleranceY){
        return is_on_screen(pos.x,pos.y,toleranceX,toleranceY);
    }

    public static boolean is_on_screen(float x, float y,float toleranceX,float toleranceY){
        if (x > Globals.cameraOffset.x + Globals.camera.viewportWidth/2 + toleranceX) return false;
        if (x < (Globals.cameraOffset.x - (Globals.camera.viewportWidth/2)) - toleranceX) return false;

        if (y > Globals.cameraOffset.y + Globals.camera.viewportHeight/2 + toleranceY) return false;
        if (y < (Globals.cameraOffset.y - (Globals.camera.viewportHeight/2)) - toleranceY) return false;

        return true;
    }

    public static Vector2 getGlobalMousePosition(){
        Vector2 returnVector = ObjectPool.getGarbage(Vector2.class);

        updateGlobalScreenStretch();

        float offsetX = Globals.cameraOffset.x - Globals.screenSize.x/2;
        float offsetY = Globals.cameraOffset.y + Globals.screenSize.y/2;

        returnVector.set((Gdx.input.getX() * Globals.screenStretch.x) + offsetX, (- (Gdx.input.getY() * Globals.screenStretch.y)) + offsetY);

        return returnVector;
    }

    public static void updateGlobalScreenStretch(){

        //Globals.screenStretch = Globals.screenSize.x/
        Globals.screenStretch.x = Gdx.graphics.getWidth()/Globals.screenSize.x;
        Globals.screenStretch.y = Gdx.graphics.getHeight()/Globals.screenSize.y;
    }


}
