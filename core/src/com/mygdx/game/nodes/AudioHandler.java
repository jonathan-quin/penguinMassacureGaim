package com.mygdx.game.nodes;

import java.util.ArrayList;
import java.util.HashMap;


import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.utilities.SoundAccelerator;
import com.mygdx.game.helpers.utilities.TimeRewindSoundV2;

import static com.badlogic.gdx.math.MathUtils.lerp;

public class AudioHandler {



    HashMap<String,Long> loopIds = new HashMap<>();

    ArrayList<String> soundsMade = new ArrayList<>();

    boolean saveSounds = false;
    double currentTime = -1;

    public void play(String effect){

        soundsMade.add(effect);
        System.out.println(effect + " " + soundsMade.size());

        if (saveSounds){
            Globals.timeRewindSounds.get(effect).play();
        }else{
            Globals.timeRewindSounds.get(effect).play(currentTime);
        }


    }

    public void updateSoundSpeeds(){

        String tempString = "";


        for ( String key : Globals.timeRewindSounds.keySet().toArray(new String[0]) ) {

            ArrayList list = Globals.timeRewindSounds.get(key).playing;
            tempString += (key + " " + list.size() + "\n");

            for (Object clip :list) {
                tempString += "\t" + ((TimeRewindSoundV2.signedClip) clip).getProgress() + " ";

                if (!(((TimeRewindSoundV2.signedClip) clip).getProgress() > 0 && ((TimeRewindSoundV2.signedClip) clip).getProgress() < 1)){
                    tempString += ((TimeRewindSoundV2.signedClip) clip).clip.getMicrosecondPosition() + " " + ((TimeRewindSoundV2.signedClip) clip).clip.getMicrosecondLength();
                }

                tempString += "\n";
            }

            Globals.timeRewindSounds.get(key).updateSoundSpeeds(Globals.gameSpeed);
        }

        tempString += "Sound speed: " + Globals.timeRewindSounds.get(Globals.Sounds.JUMP).currentSoundsIndex;
        debugString = tempString;

    }

    public void stopAllSounds(){

        for ( String key : Globals.timeRewindSounds.keySet().toArray(new String[0]) ) {
            Globals.timeRewindSounds.get(key).stopAll();
        }

    }

    public void loadTimeStamps(double timeStamp){

        for ( String key : Globals.timeRewindSounds.keySet().toArray(new String[0]) ) {
            Globals.timeRewindSounds.get(key).loadSounds(timeStamp);
        }

    }

    public void clearPastSounds(){

        for ( String key : Globals.timeRewindSounds.keySet().toArray(new String[0]) ) {
            Globals.timeRewindSounds.get(key).clearTimeStamps();
        }

    }

    public String debugString = "default";



//    public void play(String effect,float volume){
//        Globals.sounds.get(effect).play(volume);
//    }



    public void loop(String sound){

        if (!loopIds.containsKey(sound)){
            long id = Globals.sounds.get(sound).play(1f);
            Globals.sounds.get(sound).setLooping(id,true);
            loopIds.put(sound,id);
            System.out.println("looping");
        }else{
            Globals.sounds.get(sound).resume(loopIds.get(sound));
            System.out.println("resuming");
        }

    }

    public void stopLoop(String sound){
        if (loopIds.containsKey(sound)){
            Globals.sounds.get(sound).pause(loopIds.get(sound));
            System.out.println("stopping");
        }
    }


    public String[] getAndClearLastSounds(){

        String[] temp = new String[soundsMade.size()];

        for (int i = 0; i < soundsMade.size(); i++){
            temp[i] = soundsMade.get(i);
        }

        soundsMade.clear();

        return temp;

    }






}
