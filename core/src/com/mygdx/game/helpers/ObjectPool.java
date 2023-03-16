package com.mygdx.game.helpers;

import java.util.ArrayList;

public class ObjectPool {
    static ArrayList<Object> objectsStored = new ArrayList<Object>();
    static ArrayList<Object> objectsInUse = new ArrayList<Object>();
    static ArrayList<Object> garbageObjectInUse = new ArrayList<Object>();

    public static int count = 0;
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

        printTotal();

        for (Object obj : objectsStored){
            if (type.isInstance(obj)){
                returnObj = obj;
                objectsStored.remove(returnObj);

                garbageObjectInUse.remove(returnObj);
                garbageObjectInUse.add(returnObj);
                return returnObj;
            }
        }

        try {
            returnObj =  type.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {        }

        //System.out.println("created new object! " + count + returnObj.getClass());
        count++;

        garbageObjectInUse.add(returnObj);

        //printTotal();

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

        //System.out.println("total before: " + (garbageObjectInUse.size() + objectsInUse.size() + objectsStored.size()) );

        for (int i = garbageObjectInUse.size() -1; i >= 0; i--){
            objectsStored.add(garbageObjectInUse.get(i));
            garbageObjectInUse.remove(i);
        }

        //System.out.println("trash collect, " + "Garbage in use: " + garbageObjectInUse.size() + "  Stored: " + objectsStored.size());
        //System.out.println("Otherwise in use: " + objectsInUse.size());

        //System.out.println("total after: " + (garbageObjectInUse.size() + objectsInUse.size() + objectsStored.size()) );

        }


        public static void printTotal(){
            System.out.println("total: " + (garbageObjectInUse.size() + objectsInUse.size() + objectsStored.size()) );
        }

        public int total = 0;
        public int calculateTotal(){
            total = (garbageObjectInUse.size() + objectsInUse.size() + objectsStored.size());
            return total;
        }

}