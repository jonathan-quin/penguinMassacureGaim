package com.mygdx.game.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.utilities.MathUtilsCustom;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.lerp;
import static com.mygdx.game.helpers.constants.Globals.gameSpeed;
import static com.mygdx.game.helpers.constants.Globals.sceneJustChanged;

public class TimeRewindRoot extends Root{

    ArrayList<ArrayList<ArrayList<Object>>> past = new ArrayList<>();

    double time = 0;
    double lastSaveTime = 0;

    double nextGameSpeed = 1;

    public void setNextGameSpeed(double nextGameSpeed) {
        this.nextGameSpeed = nextGameSpeed;
        nextGameSpeedChanged = true;
    }

    boolean nextGameSpeedChanged = false;

    boolean playBack = false;
    int playBackFrame = 0;

    private int playBackStage = 0;

    public boolean isSaveFrame() {
        return saveFrame;
    }

    boolean saveFrame = true;

    String nextScene = "";

    public TimeRewindRoot init(){
        playBack = false;
        playBackFrame = 0;
        lastSaveTime = 0;
        time = 0;
        nextScene = "";
        playBackStage = 0;

        return this;
    }

    public void update(){


        if (!playBack){


            if (!nextGameSpeedChanged){
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    Globals.gameSpeed = lerp((float) gameSpeed,0.1f,0.2f);
                } else {
                    Globals.gameSpeed = lerp((float) gameSpeed,1f,0.3f);
                }
            }
            else{
                gameSpeed = nextGameSpeed;

                if (nextGameSpeed != 0){

                    nextGameSpeedChanged = false;
                }

            }


            boolean shouldRewind = Gdx.input.isKeyPressed(Input.Keys.SPACE) && !sceneJustChanged;

            if (shouldRewind){
                nextGameSpeedChanged = false;
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    Globals.gameSpeed = 0.1;
                } else {
                    Globals.gameSpeed = 1;
                }
            }

            if (!shouldRewind) {
                Globals.currentlyRewinding = false;

                if (gameSpeed > 0.001){
                    rootNode.updateCascade();
                }

                time += Gdx.graphics.getDeltaTime() * gameSpeed;

                if (time > lastSaveTime + (1f / 60f - 1f / 600f)) {
                    saveNodes();
                    saveFrame = true;
                    lastSaveTime = time;
                }
                else{
                    saveFrame = false;
                }

            } else {
                Globals.currentlyRewinding = true;

                time -= Gdx.graphics.getDeltaTime() * gameSpeed;

                if (time < lastSaveTime - 1f / 60f + 1f / 600f) {
                    loadNodes();
                    lastSaveTime = time;
                }else{
                    playBack(time - 1);
                }


            }
        }
        else{
            switch (playBackStage){
                case 0:
                    {
                    playBackStage++;
                    playBackFrame = past.size() - 1;
                    break;
                    }
                case 1:
                {
                    playBack(playBackFrame);

                    if (playBackFrame > 50){
                        playBackFrame -= 4;
                    }

                    playBackFrame--;

                    if (playBackFrame < 2) {
                        playBackStage++;
                    }
                    break;
                }
                case 2:
                {
                    playBack(playBackFrame);
                    playBackFrame++;
                    if (playBackFrame > past.size() - 1) {
                        SceneHandler.setCurrentScene(nextScene);
                    }
                    break;
                }

            }


        }


    }

    public void playBackAndChangeScene(String nextScene){
        this.nextScene = nextScene;
        playBack = true;
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

            Object obj = ObjectPool.get((Class) ((ArrayList) currentNode.get(0)).get(0));

            ((TimeRewindInterface) obj).init();

            ((Node)((ArrayList) currentNode.get(0)).get(1)).addChild((Node) obj);

            ((TimeRewindInterface) obj).setLastSave((ArrayList<Object>) ((ArrayList) currentNode.get(0)).get(2));

            ((Node) obj).updateGlobalPosition();

            ((TimeRewindInterface) obj).load(currentNode.toArray());

            if (!holding){
//                for (int i = currentNode.size() - 1; i > 0; i--) {
//                    ObjectPool.removeBackwards(currentNode.get(i));
//                }
                ObjectPool.removeBackwards(currentNode);
                ObjectPool.removeBackwards(currentNode.get(0));
            }

        }

        if (!holding) ObjectPool.removeBackwards(lastFrame);



    }

    public void playBack(int frame){
        for (Node n : groups.getNodesInGroup("rewind")) {

            n.free();
        }

        ArrayList<ArrayList<Object>> currentFrame;




        currentFrame = past.get(frame);



        for (ArrayList<Object> currentNode : currentFrame) {

            Object obj = ObjectPool.get((Class) ((ArrayList) currentNode.get(0)).get(0));

            ((TimeRewindInterface) obj).init();

            ((Node)((ArrayList) currentNode.get(0)).get(1)).addChild((Node) obj);

            ((Node) obj).updateGlobalPosition();



            ((TimeRewindInterface) obj).load(currentNode.toArray());

        }


    }

    public void playBack(double time){

        if ((int) time == past.size()){
            playBack((int)time);
            return;
        }

        for (Node n : groups.getNodesInGroup("rewind")) {

            n.free();
        }

        ArrayList<ArrayList<Object>> currentFrame;
        ArrayList<ArrayList<Object>> futureFrame;



        currentFrame = past.get((int) time + 1);

        float timeAfter = (float) (time - ((int) time));


        for (ArrayList<Object> currentNode : currentFrame) {

            Object obj = ObjectPool.get((Class) ((ArrayList) currentNode.get(0)).get(0));

            ((TimeRewindInterface) obj).init();

            ((Node)((ArrayList) currentNode.get(0)).get(1)).addChild((Node) obj);

            ((Node) obj).updateGlobalPosition();




            Object[] currentArray = currentNode.toArray();
            Object[] beforeArray =  ((ArrayList)((ArrayList) currentNode.get(0)).get(2)).toArray();

            Object[] newArray = new Object[currentArray.length];

            for (int i = 0; i < currentArray.length; i++){
                newArray[i] = beforeArray[i];

                if (beforeArray[i] instanceof Double){
                    newArray[i] = MathUtilsCustom.lerpD((Double) beforeArray[i], (Double) currentArray[i],timeAfter);
                }
                else if (beforeArray[i] instanceof Float){
                    newArray[i] = lerp((Float) beforeArray[i], (Float) currentArray[i],timeAfter);
                }
                else if (beforeArray[i] instanceof Vector2){
                    ((Vector2) newArray[i]).x = lerp(((Vector2) beforeArray[i]).x, ((Vector2) currentArray[i]).x,timeAfter);
                    ((Vector2) newArray[i]).y = lerp(((Vector2) beforeArray[i]).y, ((Vector2) currentArray[i]).y,timeAfter);
                }

            }

            ((TimeRewindInterface) obj).load(newArray);

        }


    }

    public void close(){
        rootNode.free();
        rootNode = null;
        gameSpeed = 1;

        for (ArrayList<ArrayList<Object>> frame : past){


            for (ArrayList<Object> currentNode : frame) {

//                for (int i = currentNode.size() - 1; i > 0; i--) {
//                    ObjectPool.removeBackwards(currentNode.get(i));
//                }

                ObjectPool.removeBackwards(currentNode.get(0));

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
