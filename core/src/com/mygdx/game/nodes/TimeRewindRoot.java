package com.mygdx.game.nodes;

import static com.badlogic.gdx.math.MathUtils.lerp;
import static com.mygdx.game.helpers.constants.Globals.gameSpeed;
import static com.mygdx.game.helpers.constants.Globals.sceneJustChanged;
import static com.mygdx.game.helpers.constants.Globals.timeRootStage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.Globals.Sounds;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.utilities.MathUtilsCustom;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;

public class TimeRewindRoot extends Root{

    ArrayList<ArrayList<ArrayList<Object>>> past = new ArrayList<>();

    ArrayList<String[]> pastSounds =  new ArrayList<>();

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

    int framesSinceLastRewind = 0;

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



        if (Gdx.input.isKeyJustPressed(Input.Keys.L)){
            SceneHandler.setCurrentScene("lobby");
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            SceneHandler.setCurrentScene("titleScreen");
            return;
        }

        framesSinceLastRewind++;

        if (!playBack){

            //audio.updateSoundSpeeds();

            audio.stopLoop(Sounds.REWINDSTATIC);

            if (!nextGameSpeedChanged){
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    Globals.gameSpeed = lerp((float) gameSpeed,Globals.ultraSlow ? 0.005f : 0.1f,0.15f);
                } else {
                    if (framesSinceLastRewind < 60){
                        Globals.gameSpeed = lerp((float) gameSpeed,1f,0.05f);
                    }
                    else{
                        Globals.gameSpeed = lerp((float) gameSpeed,1f,0.1f);
                    }

                }

                if (framesSinceLastRewind < 30){
                    gameSpeed = 0.05;
                }

            }
            else{



                gameSpeed = nextGameSpeed;

                if (nextGameSpeed != 0){

                    nextGameSpeedChanged = false;
                }

            }


            boolean shouldRewind = Gdx.input.isKeyPressed(Input.Keys.R) && !sceneJustChanged;

            if (shouldRewind){

                audio.loop(Sounds.REWINDSTATIC);

                framesSinceLastRewind = 0;
//                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
//                    trimPast();
//
//                };

                nextGameSpeedChanged = false;

                //Globals.gameSpeed = lerp((float) gameSpeed,0.05f,0.05f);

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

                double distanceToNextFrame = (time - (lastSaveTime - 1f / 60f + 1f / 600f)) * 60;

               // System.out.println(distanceToNextFrame);

                if (time < lastSaveTime - 1f / 60f + 1f / 600f) {
                    trimPast();
                    playBack((past.size() - 1));

                    lastSaveTime = time;
                }else{
                    playBack((past.size()-1) + distanceToNextFrame);
                }


            }
        }
        else{

            gameSpeed = 1;

            switch (playBackStage){
                case 0:
                    {
                        audio.loop(Sounds.REWINDSTATIC);
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

                    audio.stopLoop(Sounds.REWINDSTATIC);
                    playBackSound(playBackFrame);
                    //delete(playBackFrame);
                    playBackFrame++;
                    if (playBackFrame > past.size() - 1) {
                        playBackFrame = 0;
                        playBackStage = 0;

                        //SceneHandler.setCurrentScene(nextScene);
                    }
                    break;
                }

            }


        }

        timeRootStage = playBackStage;


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

        pastSounds.add(audio.getAndClearLastSounds());

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

    public void trimPast() {

        ArrayList<ArrayList<Object>> lastFrame;

        boolean holding =  past.size() <= 2;

        if (!holding) {
            lastFrame = past.remove(past.size() - 1);

            for (ArrayList<Object> currentNode : lastFrame) {

                ObjectPool.removeBackwards(currentNode);
                ObjectPool.removeBackwards(currentNode.get(0));

            }

            ObjectPool.removeBackwards(lastFrame);
        }

        if (!holding)
        pastSounds.remove(pastSounds.size()-1);

    }

    public void delete(int frame) {

        ArrayList<ArrayList<Object>> lastFrame;


        lastFrame = past.remove(frame);

        for (ArrayList<Object> currentNode : lastFrame) {

            ObjectPool.remove(currentNode);
            ObjectPool.remove(currentNode.get(0));

        }

        ObjectPool.remove(lastFrame);


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
            ((TimeRewindInterface) obj).setLastSave((ArrayList<Object>) ((ArrayList) currentNode.get(0)).get(2));

            ((Node) obj).updateGlobalPosition();



            ((TimeRewindInterface) obj).load(currentNode.toArray());

        }


    }

    public void playBackSound(int frame){
        for (Node n : groups.getNodesInGroup("rewind")) {

            n.free();
        }

        ArrayList<ArrayList<Object>> currentFrame;




        currentFrame = past.get(frame);



        for (ArrayList<Object> currentNode : currentFrame) {

            Object obj = ObjectPool.get((Class) ((ArrayList) currentNode.get(0)).get(0));

            ((TimeRewindInterface) obj).init();

            ((Node)((ArrayList) currentNode.get(0)).get(1)).addChild((Node) obj);
            ((TimeRewindInterface) obj).setLastSave((ArrayList<Object>) ((ArrayList) currentNode.get(0)).get(2));

            ((Node) obj).updateGlobalPosition();



            ((TimeRewindInterface) obj).load(currentNode.toArray());

        }

        for (String sound : pastSounds.get(frame)){
            audio.play(sound);
        }


    }



    public void playBack(double time){



        if ((int) time == past.size()){
            playBack(past.size()-1);
            return;
        }

        //System.out.println("Time: " + time + " past: " + past.size());

        for (Node n : groups.getNodesInGroup("rewind")) {

            n.free();
        }

        ArrayList<ArrayList<Object>> currentFrame;



        currentFrame = past.get((int) time);

        float timeAfter = (float) (time - ((int) time));
        //System.out.println(timeAfter);

        for (ArrayList<Object> currentNode : currentFrame) {

            ArrayList<Object> getPast =  ((ArrayList)((ArrayList) currentNode.get(0)).get(2));

        //    if (getPast == null){
        //        continue;
        //    }

            Object obj = ObjectPool.get((Class) ((ArrayList) currentNode.get(0)).get(0));

            ((TimeRewindInterface) obj).init();

            ((Node)((ArrayList) currentNode.get(0)).get(1)).addChild((Node) obj);
            ((TimeRewindInterface) obj).setLastSave((ArrayList<Object>) ((ArrayList) currentNode.get(0)).get(2));

            ((Node) obj).updateGlobalPosition();

            if (getPast == null){
                ((TimeRewindInterface) obj).load(currentNode.toArray());
                continue;
            }


            Object[] currentArray = currentNode.toArray();

            Object[] beforeArray =  getPast.toArray();;

//            if (beforeArray.length != currentArray.length){
//                System.out.println("THEY'RE NOT EQUAL");
//                for (Object i : beforeArray){
//                    System.out.println(i);
//                }
//                System.out.println();
//                for (Object i : currentArray){
//                    System.out.println(i);
//                }
//            }

            Object[] newArray = new Object[beforeArray.length];

            for (int i = 0; i < beforeArray.length; i++){
                newArray[i] = currentArray[i];

                if (beforeArray[i] instanceof Double){
                    if (currentArray[i] != null)
                    newArray[i] = MathUtilsCustom.lerpD((Double) beforeArray[i], (Double) currentArray[i],timeAfter);
                }
                else if (beforeArray[i] instanceof Float){
                    if (currentArray[i] != null)
                    newArray[i] = lerp((Float) beforeArray[i], (Float) currentArray[i],timeAfter);
                }
                else if (beforeArray[i] instanceof Vector2){
                    if (currentArray[i] != null)
                    newArray[i] = new Vector2(0,0);
                    ((Vector2) newArray[i]).x = lerp(((Vector2) beforeArray[i]).x, ((Vector2) currentArray[i]).x,timeAfter);
                    ((Vector2) newArray[i]).y = lerp(((Vector2) beforeArray[i]).y, ((Vector2) currentArray[i]).y,timeAfter);
                }
                else if (beforeArray[i] instanceof Boolean){
                    newArray[i] = currentArray[i];
                }

            }

            ((TimeRewindInterface) obj).load(newArray);

        }


    }

    public void close(){

        audio.stopLoop(Sounds.REWINDSTATIC);

        rootNode.free();
        rootNode = null;
        gameSpeed = 1;

        for (int i = past.size()-1; i > 0; i--){

            ArrayList<ArrayList<Object>> frame = past.get(i);

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
