package com.mygdx.game.helpers.utilities;

import com.mygdx.game.nodes.TimeRewindRoot;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

public class TimeRewindSound {

    double fileSpeed;
    String filePath;

    int currentSoundsIndex = 0;
    int numSpeeds;
    ArrayList<AudioInputStream> sounds = new ArrayList<>();

    public ArrayList<signedClip> playing = new ArrayList<>();



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

        if (!(playing.size() > 0)) return;

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



        for (int i = playing.size() -1; i >= 0 ;i--){
            signedClip currentClip = playing.get(i);
            double progress = currentClip.getProgress();
            storeClip(currentClip);

            System.out.println(progress);

            signedClip newClip = getClip(sounds.get(currentSoundsIndex));
            currentClip.setProgress(progress);
            newClip.start();
        }


    }

    public void play(){


        signedClip clip = getClip();
        clip.start();

    }


    HashMap<AudioInputStream,ArrayList<signedClip>> storedClips = new HashMap<>();

    public signedClip getClip(){
        return getClip(sounds.get(currentSoundsIndex));
    }

    public signedClip getClip(AudioInputStream type){

        if (!storedClips.containsKey(type)){
            storedClips.put(type,new ArrayList<signedClip>());
        }


        if (storedClips.get(type).size() > 0){

            signedClip returnClip = storedClips.get(type).remove(storedClips.get(type).size()-1);

            playing.add(returnClip);
            returnClip.setProgress(0);
            returnClip.stop();

            return returnClip;
        }


        Clip clip = null;
        try{
            type.reset();
            clip = AudioSystem.getClip();
            clip.open(type);
        } catch (Exception ex){
            System.out.println(ex);
        }

        assert clip != null;
        clip.setFramePosition(0);

        final signedClip returnClip = new signedClip(type,clip);
        clip.addLineListener(new LineListener() {
            public void update(LineEvent myLineEvent) {
                if (myLineEvent.getType() == LineEvent.Type.STOP) {
                    storeClip(returnClip);
                }
            }
        });

        returnClip.stop();

        playing.add(returnClip);
        return returnClip;


    }

    public void storeClip(signedClip clip){
        clip.clip.stop();
        clip.clip.setFramePosition(0);

        playing.remove(clip);

        storedClips.get(clip.ais).add(clip);
    }

    public class signedClip{
        public AudioInputStream ais;
        public Clip clip;

        public signedClip(AudioInputStream ais, Clip clip) {
            this.ais = ais;
            this.clip = clip;
        }

        public void start(){
            clip.start();
        }

        public void stop(){
            clip.stop();
        }

        public double getProgress(){
           return ((double) clip.getMicrosecondPosition())/clip.getMicrosecondLength();
        }

        public void setProgress(double progress){
            clip.setMicrosecondPosition( (long) (progress * clip.getMicrosecondLength()) );

            //System.out.println(progress + " " + ((long) (progress * clip.getMicrosecondLength())) + " " + clip.getMicrosecondLength());
        }

    }






}
