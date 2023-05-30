package com.mygdx.game.helpers.utilities;

import com.mygdx.game.nodes.TimeRewindRoot;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class TimeRewindSound {

    double fileSpeed;
    String filePath;

    int currentSoundsIndex = 0;
    int numSpeeds;
    ArrayList<AudioInputStream> sounds = new ArrayList<>();

    ArrayList<Clip> playing = new ArrayList<>();

    public TimeRewindSound(String filePath, double fileSpeed,int numSpeeds){

        this.filePath = filePath;
        this.fileSpeed = fileSpeed;
        this.numSpeeds = numSpeeds;

        for (double i = 1D/numSpeeds; MathUtilsCustom.isEqualApprox(i,1,0.08) || i < 1; i += 1D/numSpeeds){
            sounds.add(SoundAccelerator.getAtSpeed(1/fileSpeed * i,filePath));
        }

    }

    public void updateSoundSpeeds(double speed){

        double distance = 100;
        int index = 0;

        for (int i = 0; i < sounds.size(); i++){
            if ( abs(speed - i * (1D/numSpeeds)) < distance ){
                distance = abs(speed - i * (1D/numSpeeds));
                index = i;
            }
        }

        if (currentSoundsIndex == index){
            return;
        }

        for (int i = 0; i < playing.size();i++){

            double progress = ((double) playing.get(i).getMicrosecondPosition())/playing.get(i).getMicrosecondLength();

            playing.get(i).close();

            Clip clip = null;

            try{
                clip = AudioSystem.getClip();
                clip.open(sounds.get(index));
            } catch (Exception ex){
                System.out.println(ex);
            }

            playing.set(i,  clip );

            playing.get(i).setMicrosecondPosition((long) progress * playing.get(i).getMicrosecondLength());


            if (speed != 0) playing.get(i).start();
        }


    }

    public void play(){
        Clip clip = null;

        try{
            clip = AudioSystem.getClip();
            clip.open(sounds.get(currentSoundsIndex));
        } catch (Exception ex){
            System.out.println(ex);
        }

        playing.add(clip );
        clip.start();
    }






}
