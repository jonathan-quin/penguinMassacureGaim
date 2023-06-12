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

        for (int i = playing.size() -1; i >= 0 ;i--){
            if (playing.get(i).store) storeClip(playing.get(i));
        }

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
        //System.out.println("changing! " + currentSoundsIndex + " " + index + " " + playing.size());
        currentSoundsIndex = index;



        for (int i = playing.size() -1; i >= 0 ;i--){
            signedClip currentClip = playing.get(i);
            double progress = currentClip.getProgress();
            storeClip(currentClip);

            //System.out.println(progress);

            signedClip newClip = getClip(soundsList.get(currentSoundsIndex));
            newClip.start(progress);
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
            returnClip.stop();
            returnClip.setProgress(0);
            returnClip.store = false;

            return returnClip;
        }


        Clip clip = null;
        try{

            clip = AudioSystem.getClip();
            clip.open((AudioFormat) type.get(0), (byte[]) type.get(1), (Integer) type.get(2),(int) type.get(3)-((int) type.get(3) % 4));
        } catch (Exception ex){
            System.out.println("failed to make clip: " + ex);
            System.out.println(filePath + " " + currentSoundsIndex);
        }

        assert clip != null;
        clip.setFramePosition(0);

        final signedClip returnClip = new signedClip((Integer) type.get(3),clip);
        clip.addLineListener(new LineListener() {
            public void update(LineEvent myLineEvent) {
                if (myLineEvent.getType() == LineEvent.Type.STOP) {
                    if (returnClip.getProgress() > 0.98f)
                    returnClip.queueStore();
                }
            }
        });

        returnClip.stop();
        returnClip.store = false;

        playing.add(returnClip);
        return returnClip;


    }



    public void storeClip(signedClip clip){
        clip.stop();
        clip.setProgress(0);
        playing.remove(clip);
        storedClips.get(clip.bufferSize).add(clip);
        System.out.println(storedClips.get(clip.bufferSize).size());
    }

    public class signedClip{
        public int bufferSize;
        public Clip clip;

        long clipPosition;

        boolean store = false;

        public void queueStore(){
            store = true;
        }

        public signedClip(int bufferSize, Clip clip) {
            this.bufferSize = bufferSize;
            this.clip = clip;
        }

        public void start(){


            new Thread(new Runnable() {
                @Override
                public void run() {
                    // code goes here.

                    //if (clip.isRunning()) clip.stop();

                    clip.setMicrosecondPosition(clipPosition);

                    clip.start();

                }

            }).start();
        }

        public void start(final double progress){


            new Thread(new Runnable() {
                @Override
                public void run() {
                    // code goes here.

                    //if (clip.isRunning()) clip.stop();

                    clipPosition = ( (long) (progress * clip.getMicrosecondLength()) );
                    clip.setMicrosecondPosition(clipPosition);
                    clip.start();

                }

            }).start();
        }

        public void stop(){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // code goes here.
                    if (clip.isRunning())
                        clip.stop();
                }
            }).start();

        }

        public double getProgress(){
           return ((double) clip.getMicrosecondPosition())/clip.getMicrosecondLength();
        }

        public void setProgress(double progress){
            clipPosition = ( (long) (progress * clip.getMicrosecondLength()) );
        }

    }






}
