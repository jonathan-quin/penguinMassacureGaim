package com.mygdx.game.nodes;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.helpers.constants.Globals;

import java.util.HashMap;

public class AudioHandler {



    HashMap<String,Long> loopIds = new HashMap<>();



    public void play(String effect){
        Globals.sounds.get(effect).play(1f);
    }

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







}
