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

                removeFromList(objectsStored,returnObj);
                //removeFromList(objectsInUse,returnObj); //safe but inefficient

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

                removeFromList(objectsStored,returnObj);
                //removeFromList(garbageObjectInUse,returnObj); //safe but inefficient
                //garbageObjectInUse.remove(returnObj); //You can't remove it from the list because it uses ".equals" not " == "
                garbageObjectInUse.add(returnObj);



                return returnObj;
            }
        }

        try {
            returnObj =  type.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {        }



        garbageObjectInUse.add(returnObj);


        return returnObj;
    }
    public static void remove(Object obj){
         //System.out.println("In use: " + objectsInUse.size() + "  Stored: " + objectsStored.size() + " Garbage in use: " + garbageObjectInUse.size());


         removeFromList(objectsStored,obj);
         //removeFromList(objectsInUse,obj); //safe but inefficient
         objectsStored.add(obj);
    }

    public static boolean removeFromList(ArrayList<Object> list, Object o){

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

    public static void keep(Object obj){

        if (removeFromList(garbageObjectInUse,obj))  {
            objectsInUse.add(obj);
        }

    }

    public static void takeOutTrash(){


        for (int i = garbageObjectInUse.size() -1; i >= 0; i--){
            objectsStored.add(garbageObjectInUse.get(i));
            garbageObjectInUse.remove(i);
        }


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