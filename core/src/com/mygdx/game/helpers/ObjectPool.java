package com.mygdx.game.helpers;

import java.util.ArrayList;

public class ObjectPool {
    static ArrayList<Object> objectsStored = new ArrayList<Object>();
    static ArrayList<Object> objectsInUse = new ArrayList<Object>();
    static ArrayList<Object> garbageObjectInUse = new ArrayList<Object>();
    public static Object get(Class type){
        Object returnObj = null;
        for (Object obj : objectsStored){
            if (type.isInstance(obj)){
                returnObj = obj;
                objectsStored.remove(obj);
                objectsInUse.remove(obj);
                objectsInUse.add(obj);
                return returnObj;
            }        }
        try {
            returnObj =  type.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {        }
        objectsInUse.add(returnObj);
        return returnObj;
    }
    public static Object getGarbage(Class type){
        Object returnObj = null;
        for (Object obj : objectsStored){
            if (type.isInstance(obj)){
                returnObj = obj;
                objectsStored.remove(obj);

                garbageObjectInUse.remove(obj);
                garbageObjectInUse.add(obj);
                return returnObj;
            }        }
        try {
            returnObj =  type.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {        }
        garbageObjectInUse.add(returnObj);
        return returnObj;
    }
    public static void remove(Object obj){
         //System.out.println("In use: " + objectsInUse.size() + "  Stored: " + objectsStored.size() + " Garbage in use: " + garbageObjectInUse.size());

         objectsStored.remove(obj);
         objectsInUse.remove(obj);
         objectsStored.add(obj);
    }
    public static void keep(Object obj){

        if (garbageObjectInUse.contains(obj))  {
            objectsInUse.add(garbageObjectInUse.get(garbageObjectInUse.indexOf(obj)));
            garbageObjectInUse.remove(obj);
        }

    }
    public static void takeOutTrash(){

        for (int i = garbageObjectInUse.size() -1; i >= 0; i--){
            objectsStored.add(garbageObjectInUse.get(i));
            garbageObjectInUse.remove(i);
        }

        //System.out.println("trash collect, " + "In use: " + garbageObjectInUse.size() + "  Stored: " + objectsStored.size());
        }
}