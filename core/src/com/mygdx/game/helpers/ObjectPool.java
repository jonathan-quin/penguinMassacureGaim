package com.mygdx.game.helpers;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectPool {
    static ArrayList<Object> objectsStored = new ArrayList<Object>();
    static ArrayList<Object> objectsInUse = new ArrayList<Object>();
    static ArrayList<Object> garbageObjectInUse = new ArrayList<Object>();

    public static int count = 0;

    public static <T> T get(Class<T> type){
        T returnObj = null;
        for (Object obj : objectsStored){
            if (type.equals(obj.getClass())){
                returnObj = (T) obj;

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


    public static <T> T getGarbage(Class<T> type){
        T returnObj = null;



        for (Object obj : objectsStored){
            if (type.equals(obj.getClass())){
                returnObj = (T) obj;

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


         //removeFromList(objectsStored,obj); //safe but inefficient
        if (removeFromList(objectsInUse,obj))
         objectsStored.add(obj);

    }

    public static void removeBackwards(Object obj){
        if (removeFromList(objectsInUse,obj,true)) objectsStored.add(obj);
    }

    private static boolean removeFromList(ArrayList<Object> list, Object o){

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

    private static boolean removeFromList(ArrayList<Object> list, Object o,boolean backWards){

        if (backWards == false){
            return removeFromList(list,o);
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
            System.out.print("total: " + (garbageObjectInUse.size() + objectsInUse.size() + objectsStored.size()) );
            System.out.println(" inUse: " + objectsInUse.size());
        }

        public static void printObjectBreakdownInUse(){
            HashMap<Class,Integer> breakdown = new HashMap<>();

            for (Object obj : objectsInUse){

                if (breakdown.containsKey(obj.getClass())){
                    breakdown.put(obj.getClass(),breakdown.get(obj.getClass()) + 1);
                }
                else{
                    breakdown.put(obj.getClass(),1);
                }

            }

            String tempString = "";

            

            System.out.println(breakdown);


        }

        public int total = 0;
        public int calculateTotal(){
            total = (garbageObjectInUse.size() + objectsInUse.size() + objectsStored.size());
            return total;
        }

}