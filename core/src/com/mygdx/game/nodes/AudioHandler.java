package com.mygdx.game.nodes;

import java.util.ArrayList;
import java.util.HashMap;


import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.utilities.SoundAccelerator;

import static com.badlogic.gdx.math.MathUtils.lerp;

public class AudioHandler {



    HashMap<String,Long> loopIds = new HashMap<>();

    ArrayList<String> soundsMade = new ArrayList<>();

    public void play(String effect){
        soundsMade.add(effect);


        Globals.timeRewindSounds.get(effect).play();

    }

    public void updateSoundSpeeds(){

        for ( String key : Globals.timeRewindSounds.keySet().toArray(new String[0]) ) {
            System.out.println(key + " " + Globals.timeRewindSounds.get(key).playing.size());
            Globals.timeRewindSounds.get(key).updateSoundSpeeds(Globals.gameSpeed);
        }

    }



//    public void play(String effect,float volume){
//        Globals.sounds.get(effect).play(volume);
//    }

    public void loop(String sound){
        if (!loopIds.containsKey(sound)){
            long id = Globals.sounds.get(sound).play(0.2f);
            Globals.sounds.get(sound).setLooping(id,true);
            loopIds.put(sound,id);
        }else{
            Globals.sounds.get(sound).resume(loopIds.get(sound));
        }

    }

    public void stopLoop(String sound){
        if (loopIds.containsKey(sound)){
            Globals.sounds.get(sound).pause(loopIds.get(sound));
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
