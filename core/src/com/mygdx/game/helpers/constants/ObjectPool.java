package com.mygdx.game.helpers.constants;

import java.util.ArrayList;
import java.util.HashMap;

import com.mygdx.game.nodes.TextureEntity;

public class ObjectPool {
    //static ArrayList<Object> objectsStored = new ArrayList<Object>();

    static HashMap<Class,ArrayList<Object>> hashObjectsStored = new HashMap<Class,ArrayList<Object>>();
    static HashMap<Class,ArrayList<Object>> hashObjectsInUse = new HashMap<Class,ArrayList<Object>>();

    static HashMap<Class,ArrayList<Object>> hashGarbageObjectInUse = new HashMap<Class,ArrayList<Object>>();

   // static ArrayList<Object> objectsInUse = new ArrayList<Object>();
    //static ArrayList<Object> garbageObjectInUse = new ArrayList<Object>();

    public static ArrayList<Object> getFromHashStored(Class type){

        if (hashObjectsStored.containsKey(type)){
            if (hashObjectsStored.get(type).size() >= 1) return hashObjectsStored.get(type);

            Object temp = null;

            try {
                temp =  type.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            hashObjectsStored.get(type).add(temp);

            return hashObjectsStored.get(type);

        }

        hashObjectsStored.put(type,new ArrayList<>());

        Object temp = null;

        try {
            temp =  type.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        hashObjectsStored.get(type).add(temp);

        return hashObjectsStored.get(type);

    }

    public static void putIntoInUse(Object obj){
        if (!hashObjectsInUse.containsKey(obj.getClass())){
            hashObjectsInUse.put(obj.getClass(),new ArrayList<>());
        }

        hashObjectsInUse.get(obj.getClass()).add(obj);

    }

    public static void putIntoGarbage(Object obj){
        if (!hashGarbageObjectInUse.containsKey(obj.getClass())){
            hashGarbageObjectInUse.put(obj.getClass(),new ArrayList<>());
        }

        hashGarbageObjectInUse.get(obj.getClass()).add(obj);

    }

    public static void putIntoStorage(Object obj){
        if (!hashObjectsStored.containsKey(obj.getClass())){
            hashObjectsStored.put(obj.getClass(),new ArrayList<>());
        }

        hashObjectsStored.get(obj.getClass()).add(obj);
    }

    public static void printTextureEntityParents() {

        String temp = "";

        for (Object obj :  hashObjectsInUse.get(TextureEntity.class)){
            TextureEntity tex = (TextureEntity) obj;

            temp += " " + tex.getParent().getClass();
        }

        System.out.println(temp);

    }

    public void removeFromInUse(Object obj){

        if (removeFromList( hashObjectsInUse.get(obj.getClass()),obj )){

        }


    }

    public static int count = 0;

    public static <T> T get(Class<T> type){
        ArrayList<Object> tempArray = getFromHashStored(type);

        T value =  (T) tempArray.remove(tempArray.size()-1);

        putIntoInUse(value);

        return value;
    }

//    public static <T> T oldGet(Class<T> type){
//        T returnObj = null;
//        for (Object obj : objectsStored){
//            if (type.equals(obj.getClass())){
//                returnObj = (T) obj;
//
//                removeFromList(objectsStored,returnObj);
//                //removeFromList(objectsInUse,returnObj); //safe but inefficient
//
//                objectsInUse.add(obj);
//                return returnObj;
//            }        }
//        try {
//            returnObj =  type.getDeclaredConstructor().newInstance();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        objectsInUse.add(returnObj);
//
//        return returnObj;
//    }

    public static <T> T getGarbage(Class<T> type){
        ArrayList<Object> tempArray = getFromHashStored(type);

        T value =  (T) tempArray.remove(tempArray.size()-1);

        putIntoGarbage(value);

        return value;
    }


//    public static <T> T getGarbage(Class<T> type){
//        T returnObj = null;
//
//
//
//        for (Object obj : objectsStored){
//            if (type.equals(obj.getClass())){
//                returnObj = (T) obj;
//
//                removeFromList(objectsStored,returnObj);
//                //removeFromList(garbageObjectInUse,returnObj); //safe but inefficient
//                //garbageObjectInUse.remove(returnObj); //You can't remove it from the list because it uses ".equals" not " == "
//                garbageObjectInUse.add(returnObj);
//
//
//
//                return returnObj;
//            }
//        }
//
//        try {
//            returnObj =  type.getDeclaredConstructor().newInstance();
//        } catch (Exception ex) {        }
//
//
//
//        garbageObjectInUse.add(returnObj);
//
//
//        return returnObj;
//    }

    public static void remove(Object obj){

        if (obj == null) return;

        if (removeFromList(hashObjectsInUse.get(obj.getClass()),obj))
         putIntoStorage(obj);

    }

//    public static void oldRemove(Object obj){
//        //System.out.println("In use: " + objectsInUse.size() + "  Stored: " + objectsStored.size() + " Garbage in use: " + garbageObjectInUse.size());
//
//
//        //removeFromList(objectsStored,obj); //safe but inefficient
//        if (removeFromList(objectsInUse,obj))
//            objectsStored.add(obj);
//
//    }

    public static void removeBackwards(Object obj){

        if (obj == null) return;;

        ArrayList<Object> list = hashObjectsInUse.get(obj.getClass());

        if (list == null) return;

        if (removeFromList(list,obj,true)) putIntoStorage(obj);
    }

//    public static void removeBackwards(Object obj){
//        if (removeFromList(objectsInUse,obj,true)) objectsStored.add(obj);
//    }

    private static boolean removeFromList(ArrayList<Object> list, Object o){
        return removeFromList(list,o,false);
    }

    private static boolean removeFromList(ArrayList<Object> list, Object o,boolean backWards){

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

//    public static void keep(Object obj){
//
//        if (removeFromList(garbageObjectInUse,obj))  {
//            objectsInUse.add(obj);
//        }
//
//    }

    public static void takeOutTrash(){


        for (Class type : hashGarbageObjectInUse.keySet()){
            for (Object obj : hashGarbageObjectInUse.get(type)){
                putIntoStorage(obj);
            }
            hashGarbageObjectInUse.get(type).clear();
        }


    }

/*    public static void takeOutTrash(){


        for (int i = garbageObjectInUse.size() -1; i >= 0; i--){
            objectsStored.add(garbageObjectInUse.get(i));
            garbageObjectInUse.remove(i);
        }


        }*/


        /*public static void printTotal(){
            System.out.print("total: " + (garbageObjectInUse.size() + objectsInUse.size() + objectsStored.size()) );
            System.out.println(" inUse: " + objectsInUse.size());
        }*/

        public static void printObjectBreakdownInUse(){
            HashMap<Class,Integer> breakdown = new HashMap<>();

            for (Class type : hashObjectsInUse.keySet()){

                breakdown.put(type,hashObjectsInUse.get(type).size());

            }

            String tempString = "";

            for (Class c : breakdown.keySet()){

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
            total = (hashMapSize(hashObjectsInUse) + hashMapSize(hashObjectsStored)  + hashMapSize(hashGarbageObjectInUse) );
            return total;
        }

        public static int hashMapSize(HashMap<Class,ArrayList<Object>> hash){
            int size = 0;
            for (Class type : hash.keySet()){
                size += hash.get(type).size();
            }
            return size;
        }

}