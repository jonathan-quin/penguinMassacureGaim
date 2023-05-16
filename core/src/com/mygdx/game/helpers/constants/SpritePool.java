package com.mygdx.game.helpers.constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class SpritePool {

    static HashMap<Texture, ArrayList<Sprite>> hashObjectsStored = new HashMap<Texture,ArrayList<Sprite>>();
    static HashMap<Texture,ArrayList<Sprite>> hashObjectsInUse = new HashMap<Texture,ArrayList<Sprite>>();


    public static ArrayList<Sprite> getFromHashStored(Texture type){

        if (hashObjectsStored.containsKey(type)){
            if (hashObjectsStored.get(type).size() >= 1) return hashObjectsStored.get(type);

            Sprite temp = null;

            try {
                temp = new Sprite(type);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            hashObjectsStored.get(type).add(temp);

            return hashObjectsStored.get(type);

        }

        hashObjectsStored.put(type,new ArrayList<Sprite>());

        Sprite temp = null;

        try {
            temp = new Sprite(type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        hashObjectsStored.get(type).add(temp);

        return hashObjectsStored.get(type);

    }

    public static void putIntoInUse(Sprite obj){
        if (!hashObjectsInUse.containsKey(obj.getTexture())){
            hashObjectsInUse.put(obj.getTexture(),new ArrayList<Sprite>());
        }

        hashObjectsInUse.get(obj.getTexture()).add(obj);

    }


    public static void putIntoStorage(Sprite obj){
        if (!hashObjectsStored.containsKey(obj.getTexture())){
            hashObjectsStored.put(obj.getTexture(),new ArrayList<Sprite>());
        }

        hashObjectsStored.get(obj.getTexture()).add(obj);
    }







    public static Sprite get(Texture type){
        ArrayList<Sprite> tempArray = getFromHashStored(type);

        Sprite value = tempArray.remove(tempArray.size()-1);

        putIntoInUse(value);

        return value;
    }

    public static void remove(Sprite obj){

        if (obj == null){
            return;
        }

        if (removeFromList(hashObjectsInUse.get(obj.getTexture()),obj))
            putIntoStorage(obj);

    }



    public static void removeBackwards(Sprite obj){

        if (obj == null) return;;

        ArrayList<Sprite> list = hashObjectsInUse.get(obj.getTexture());

        if (list == null) return;

        if (removeFromList(list,obj,true)) putIntoStorage(obj);
    }


    private static boolean removeFromList(ArrayList<Sprite> list, Sprite o){
        return removeFromList(list,o,false);
    }

    private static boolean removeFromList(ArrayList<Sprite> list, Sprite o,boolean backWards){

        if (backWards == false){
            if (o == null) {
                for (int index = 0; index < list.size(); index++)
                    if (list.get(index) == null) {
                        list.remove(index);
                        return true;
                    }
            } else {
                for (int index = 0; index < list.size(); index++)
                    if (o == (list.get(index))) {
                        list.remove(index);
                        return true;
                    }
            }

            return false;
        }

        //System.out.println("it's called");

        if (o == null) {
            for (int index = list.size()-1; index >= 0; index--)
                if (list.get(index) == null) {
                    list.remove(index);
                    return true;
                }
        } else {
            for (int index = list.size()-1; index >= 0; index--)
                if (o == (list.get(index))) {
                    list.remove(index);
                    return true;
                }
        }

        return false;
    }



    public static void printObjectBreakdownInUse(){
        HashMap<Texture,Integer> breakdown = new HashMap<>();

        for (Texture type : hashObjectsInUse.keySet()){

            breakdown.put(type,hashObjectsInUse.get(type).size());

        }

        String tempString = "";

        for (Texture c : breakdown.keySet()){

            String tempTempString = c.toString();
            tempTempString = tempTempString.substring(tempTempString.lastIndexOf(".")+1);
            tempTempString += ": " + breakdown.get(c).toString();
            tempTempString += "\n";
            tempString += tempTempString;
        }

        System.out.println(tempString);


    }

    public static int total = 0;

    public static int calculateTotal(){
        total = (hashMapSize(hashObjectsInUse) + hashMapSize(hashObjectsStored) );
        return total;
    }

    public static int hashMapSize(HashMap<Texture,ArrayList<Sprite>> hash){
        int size = 0;
        for (Texture type : hash.keySet()){
            size += hash.get(type).size();
        }
        return size;
    }

}
