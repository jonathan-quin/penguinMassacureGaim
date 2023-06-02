package com.mygdx.game.helpers.utilities;

import com.mygdx.game.nodes.TimeRewindRoot;

import javax.sound.sampled.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class TimeRewindSound {

    double fileSpeed;
    String filePath;

    int currentSoundsIndex = 0;
    int numSpeeds;
    ArrayList<AudioInputStream> sounds = new ArrayList<>();

    public ArrayList<Clip> playing = new ArrayList<>();

    public TimeRewindSound(String filePath, double fileSpeed,int numSpeeds){

        this.filePath = filePath;
        this.fileSpeed = fileSpeed;
        this.numSpeeds = numSpeeds;

        for (double i = 1D/numSpeeds; MathUtilsCustom.isEqualApprox(i,1,0.02) || i < 1; i += 1D/numSpeeds){
            sounds.add(SoundAccelerator.getAtSpeed(1/fileSpeed * i,filePath));
        }

        currentSoundsIndex = sounds.size()-1;

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

        //System.out.println(speed);
        //System.out.println(index);

        if (currentSoundsIndex == index){
            return;
        }
        System.out.println("changing! " + currentSoundsIndex + " " + index + " " + playing.size());
        currentSoundsIndex = index;



        for (int i = 0; i < playing.size();i++){

            double progress = ((double) playing.get(i).getMicrosecondPosition())/playing.get(i).getMicrosecondLength();

            //playing.get(i).close();

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
            sounds.get(currentSoundsIndex).reset();
            clip = AudioSystem.getClip();
            clip.open(sounds.get(currentSoundsIndex));
        } catch (Exception ex){
            System.out.println(ex);
        }

        playing.add(clip);

        assert clip != null;
        clip.setFramePosition(0);
        clip.start();

        final Clip finalClip = clip;
        clip.addLineListener(new LineListener() {
            public void update(LineEvent myLineEvent) {
                if (myLineEvent.getType() == LineEvent.Type.STOP) {
                    finalClip.setFramePosition(0);
                    finalClip.close();
                    playing.remove(finalClip);
                }
            }
        });

        System.out.println(playing.size() + " " + filePath);

//        for (int i = playing.size() - 1; i >= 0; i--){
//            if (playing.get(i).getMicrosecondLength() == playing.get(i).getMicrosecondPosition()){
//                //playing.get(i).stop();
//                playing.get(i).close();
//                playing.remove(i);
//
//            }
//        }

    }






}
