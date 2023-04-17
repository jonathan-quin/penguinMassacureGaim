package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TimeRewindRoot extends Root{

    ArrayList<ArrayList<ArrayList<Object>>> past = new ArrayList<>();

    public void update(){

        boolean shouldRewind = Gdx.input.isKeyPressed(Input.Keys.C);

        if (!shouldRewind){
            Globals.currentlyRewinding = false;
            rootNode.updateCascade();
            saveNodes();
        }
        else{
            Globals.currentlyRewinding = true;
            loadNodes();
        }


    }

    public void saveNodes(){

        past.add( (ArrayList<ArrayList<Object>>) ObjectPool.get(ArrayList.class));

        past.get(past.size() -1 ).clear();

        ArrayList<ArrayList<Object>> thisFrame = past.get(past.size()-1);

        for (Node  n : groups.getNodesInGroup("rewind")){

            thisFrame.add( ((TimeRewindInterface) n).save());

        }
    }

    public void loadNodes() {

        for (Node n : groups.getNodesInGroup("rewind")) {

            n.free();
        }

        ArrayList<ArrayList<Object>> lastFrame;

        boolean holding =  past.size() <= 2;

        if (!holding) {
            lastFrame = past.remove(past.size() - 1);
        } else {
            lastFrame = past.get(0);
        }


        for (ArrayList<Object> currentNode : lastFrame) {

            Object obj = ObjectPool.get((Class) currentNode.get(0));

            ((TimeRewindInterface) obj).init();

            add(obj);

            ((Node) obj).updateGlobalPosition();

            ((TimeRewindInterface) obj).load(currentNode.toArray());

            if (!holding){
                for (int i = currentNode.size() - 1; i > 0; i--) {
                    ObjectPool.removeBackwards(currentNode.get(i));
                }
                ObjectPool.removeBackwards(currentNode);
            }

        }

        if (!holding) ObjectPool.removeBackwards(lastFrame);



    }

    public void close(){
        rootNode.free();
        rootNode = null;

        for (ArrayList<ArrayList<Object>> frame : past){


            for (ArrayList<Object> currentNode : frame) {

                for (int i = currentNode.size() - 1; i > 0; i--) {
                    ObjectPool.removeBackwards(currentNode.get(i));
                }

                ObjectPool.removeBackwards(currentNode);

            }

            ObjectPool.removeBackwards(frame);

        }

        past.clear();





    }


    /*public void loadNodes(){

        for (Node  n : groups.getNodesInGroup("rewind")){

            n.free();
        }

        ArrayList< HashMap<String,Object> > lastFrame;

        if (past.size() > 2){
            lastFrame = past.remove(past.size()-1);
        }
        else{
            lastFrame = past.get(0);
        }


        for (HashMap<String,Object> currentNode : lastFrame){

            Object obj = ObjectPool.get( (Class) currentNode.get("class")  );

            ((TimeRewindInterface) obj).init();

            add(obj);

            ((Node) obj).updateGlobalPosition();

            for (String propertyName :  currentNode.keySet()){

                if (propertyName != "class"){
                    try {
                        setField(obj,propertyName,currentNode.get(propertyName));
                        //System.out.println("set field succeded!");
                    }
                    catch (Exception ex){
                        System.out.println("set field failed");
                    }
                }

            }



        }


    }*/

    public Object setField(Object obj,String fieldName,Object value) throws NoSuchFieldException, IllegalAccessException {


        Field nameField;

        ArrayList<Field> fields = new ArrayList<>();
        Class type = obj.getClass();

        while (  !type.equals(Object.class) ){

            for (Field f : type.getDeclaredFields()) {
                fields.add(f);
            }

            type = type.getSuperclass();

        }



        for (Field f : fields){
            if (f.getName().equals(fieldName)){
                nameField = f;



                nameField.setAccessible(true);


                nameField.set(obj, value);

                break;
            }
        }



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
