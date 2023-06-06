package com.mygdx.game.helpers.utilities;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

public class TimeRewindSoundV2 {

    double fileSpeed;
    String filePath;

    public int currentSoundsIndex = 0;
    int numSpeeds;
    //ArrayList<AudioInputStream> sounds = new ArrayList<>();
    ArrayList<ArrayList<Object>> soundsList = new ArrayList<>();

    public ArrayList<signedClip> playing = new ArrayList<>();



    public TimeRewindSoundV2(String filePath, double fileSpeed, int numSpeeds){

        this.filePath = filePath;
        this.fileSpeed = fileSpeed;
        this.numSpeeds = numSpeeds;

        for (double i = 1D/numSpeeds; MathUtilsCustom.isEqualApprox(i,1,0.02) || i < 1; i += 1D/numSpeeds){
            soundsList.add(SoundAccelerator.getAtSpeedV2(1/fileSpeed * i,filePath));
        }

        currentSoundsIndex = soundsList.size()-1;

    }

    public void updateSoundSpeeds(double speed){

        if (!(playing.size() > 0)) return;

        double distance = 100;
        int index = 0;

        for (int i = 0; i < soundsList.size(); i++){
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

            signedClip newClip = getClip(soundsList.get(currentSoundsIndex));
            currentClip.setProgress(progress);
            newClip.start();
        }


    }

    public void play(){


        signedClip clip = getClip();
        clip.start();

    }




    HashMap<Integer,ArrayList<signedClip>> storedClips = new HashMap<>();

    public signedClip getClip(){

        return getClip(soundsList.get(currentSoundsIndex));
    }

    public signedClip getClip(ArrayList<Object> type){

        if (!storedClips.containsKey(type.get(3))){
            storedClips.put((Integer) type.get(3),new ArrayList<signedClip>());
        }


        if (storedClips.get(type.get(3)).size() > 0){

            signedClip returnClip = storedClips.get(type.get(3)).remove(storedClips.get(type.get(3)).size()-1);

            playing.add(returnClip);
            returnClip.setProgress(0);
            returnClip.stop();

            return returnClip;
        }


        Clip clip = null;
        try{

            clip = AudioSystem.getClip();
            clip.open((AudioFormat) type.get(0), (byte[]) type.get(1), (Integer) type.get(2),(int) type.get(3));
        } catch (Exception ex){
            System.out.println(ex);
        }

        assert clip != null;
        clip.setFramePosition(0);

        final signedClip returnClip = new signedClip((Integer) type.get(3),clip);
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
        clip.stop();
        clip.setProgress(0);

        playing.remove(clip);

        storedClips.get(clip.bufferSize).add(clip);
    }

    public class signedClip{
        public int bufferSize;
        public Clip clip;

        public signedClip(int bufferSize, Clip clip) {
            this.bufferSize = bufferSize;
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

            System.out.println(progress + " " + ((long) (progress * clip.getMicrosecondLength())) + " " + clip.getMicrosecondLength());

            System.out.println("clip progress after set: "+clip.getMicrosecondPosition());
        }

    }






}
