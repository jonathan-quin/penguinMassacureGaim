package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.helpers.ObjectPool;
import com.mygdx.game.helpers.TimeRewindInterface;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class TimeRewindRoot extends Root{

    ArrayList<ArrayList<HashMap<String,Object>>> past = new ArrayList<ArrayList<HashMap<String,Object>>>();

    public void update(){

        boolean shouldRewind = Gdx.input.isKeyPressed(Input.Keys.C);

        if (!shouldRewind){
            rootNode.updateCascade();
            saveNodes();
        }
        else{
            loadNodes();
        }


    }

    public void saveNodes(){

        past.add( (ArrayList<HashMap<String, Object>>) ObjectPool.get(ArrayList.class));

        ArrayList<HashMap<String,Object>> thisFrame = past.get(past.size()-1);

        for (Node  n : groups.getNodesInGroup("rewind")){

            thisFrame.add( ((TimeRewindInterface) n).save());

        }
    }

    public void loadNodes(){

        for (Node  n : groups.getNodesInGroup("rewind")){
            n.free();
        }

        ArrayList< HashMap<String,Object> > lastFrame;

        if (past.size() > 1){
            lastFrame = past.remove(past.size()-1);
        }
        else{
            lastFrame = past.get(0);
        }


        for (HashMap<String,Object> currentNode : lastFrame){

            Object obj = ObjectPool.get( (Class) currentNode.get("class")  );

            for (String propertyName :  currentNode.keySet()){

                if (propertyName != "class"){
                    try {
                        setField(obj,propertyName,currentNode.get(propertyName));
                        System.out.println("set field succeded!");
                    }
                    catch (Exception ex){
                        System.out.println("set field failed");
                    }
                }


            }

        }


    }

    public Object setField(Object obj,String fieldName,Object value) throws NoSuchFieldException, IllegalAccessException {
        System.out.println("called the method at least " + fieldName + " " + obj);

        Field nameField;

        nameField = obj.getClass().getDeclaredField(fieldName);

        System.out.println("got declared field");

        nameField.setAccessible(true);
        System.out.println("set accessable");
        nameField.set(obj, value);

        return obj;
    }


    public Object callMethod(Object object, String methodName, Object[] paramValues) {
        //region get paramTypes
        Class<?>[] paramTypes = null;
        if(paramValues != null) {
            paramTypes = new Class<?>[paramValues.length];
            for (int pIndex = 0; pIndex < paramValues.length; pIndex++) {
                paramTypes[pIndex] = paramValues[pIndex].getClass();
            }
        }
        //endregion
        try {
            Method method = object.getClass().getDeclaredMethod(methodName, paramTypes);
            Object returnVal = method.invoke(object,paramValues);
            return returnVal;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }




}
