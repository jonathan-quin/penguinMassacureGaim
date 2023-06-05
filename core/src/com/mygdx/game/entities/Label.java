package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.Node;

public class Label extends Node {

    String text;

    public static BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), Gdx.files.internal("fonts/font.png"), false);

    public boolean relativeToCamera;

    public Label(){
        font.getData().setScale(0.5f);
        //text = "Default Text";
        relativeToCamera = true;
    }

    public void setText(String newText){

        text = newText;
    }



    public void render(SpriteBatch batch){
        font.getScaleX();



        if (!relativeToCamera){
            font.draw(batch, text,globalPosition.x,globalPosition.y);

        }





    }

    public void debug(){

        if (relativeToCamera){ //0 - 1000, 0 - 600

            //setText(position.toString());

            int x = (int) (position.x);
            int y = (int) (position.y);

            Globals.fontBatch.begin();

            font.draw(Globals.fontBatch,text, x, y);

            Globals.fontBatch.end();
        }


    }

}
