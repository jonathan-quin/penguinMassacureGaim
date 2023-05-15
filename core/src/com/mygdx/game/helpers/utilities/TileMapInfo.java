package com.mygdx.game.helpers.utilities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.nodes.CollisionShape;

public class TileMapInfo {

    public Texture mapTexture;

    public int[] tiles;

    public int width;
    public int height;
    public int tileSize;

    public TileMapInfo(Texture mapTexture, int width, int height, int tileSize, int[] collisionInfo) {
        this.mapTexture = mapTexture;
        this.tiles = collisionInfo;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
    }

    public void fixTiles(){

        for (int i = 0; i < tiles.length; i++){
            tiles[i] = (tiles[i] <= 0) ? 0 : 1;
        }

    }

    public ArrayList<CollisionShape> getShapes(){
        fixTiles();

        ArrayList returnArray = new ArrayList<>();

        for (int i = 0; i < tiles.length; i++){

            if (tiles[i] == 1){
                int sectionWidth = expandRightwards(i);
                //System.out.println("hey");
                int sectionHeight = expandDownward(i,sectionWidth);

                returnArray.add( ObjectPool.get(CollisionShape.class).init(sectionWidth,sectionHeight,(i % width) * 2, (-((int)(i/width))) * 2 ) );

            }


        }
        //printTiles();

        //System.out.println(returnArray.size());

        for (int i = 0; i < returnArray.size(); i++){
            CollisionShape currentShape = ((CollisionShape)returnArray.get(i));
            currentShape.setColor(new Color((float) i/returnArray.size(),0,(float) i/returnArray.size(),0.6f));
            currentShape.position.add(currentShape.boundingBox.half.x,-currentShape.boundingBox.half.y);
            currentShape.position.scl(tileSize/2);
            currentShape.boundingBox.half.scl(tileSize/2);
        }

        return returnArray;

    }

    /*public void printTiles(){
        int i = 0;
        for (int n = 0; n < height; n++){
            String tempText = "";
            for (int o = 0; o < width; o++) {

                switch (tiles[i]){
                    case 0:
                        tempText += ". ";
                        break;
                    case 1:
                        tempText += ANSI_YELLOW + tiles[i] + " " + ANSI_RESET;
                        break;
                    case 2:
                        tempText += ANSI_BLUE + tiles[i] + " " + ANSI_RESET;
                        break;
                    case 3:
                        tempText += ANSI_PURPLE + tiles[i] + " " + ANSI_RESET;
                        break;
                    case -1:
                        tempText += ANSI_RED + "X " + ANSI_RESET;
                        break;
                    default:
                        tempText += ANSI_GREEN + "? " + ANSI_RESET;
                        break;

                }

                i++;

            }
            System.out.println(tempText);
        }
    }*/

    public int expandRightwards(int startPos){

        int pos = startPos;

        tiles[pos] = 3;

        while ((pos + 1) % width != 0 && tiles[pos + 1] > 0){
            tiles[pos + 1] = 2;
            pos++;
        }

        //System.out.println(pos - startPos);
        //System.out.println(pos);
        return (pos - startPos) + 1;

    }

    public int expandDownward(int start, int length){

        int count = 0;

        boolean canGoDown = true;
        int downOffset = start;

        while (canGoDown) {




            for (int x = downOffset; x < downOffset + length; x++) {


                if (x + width >= tiles.length || tiles[x + width] < 1) {
                    if (!(x + width >= tiles.length)) tiles[x + width] = -1;
                    canGoDown = false;
                    break;
                }



            }


            if (canGoDown) {
                for (int x = downOffset; x < downOffset + length; x++) {
                    if (x + width >= tiles.length) break;
                    tiles[x + width] = 2;
                }
            }


            count++;
            downOffset =start + count * width;

        }


        return count;

    }



}
