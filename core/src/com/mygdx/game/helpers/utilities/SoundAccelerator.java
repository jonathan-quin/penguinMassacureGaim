package com.mygdx.game.helpers.utilities;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SoundAccelerator {



    public static void play(double speed){

        int playBackSpeed = (int) speed;

        System.out.println("Playback Rate: " + playBackSpeed);



        File audio = new File("assets/sounds/testExplosion.wav");


        AudioInputStream ais = null;
        try{
            ais = AudioSystem.getAudioInputStream(audio);
        } catch (Exception ex){
            System.out.println(ex);
        }



        AudioFormat af = null;
        try{
            af = ais.getFormat();
        } catch (Exception ex){
            System.out.println(ex);
        }


        int frameSize = af.getFrameSize();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] currentSoundByteArray = new byte[2^16];
        int read = 1;
        while( read>-1 ) {

            try {
                read = ais.read(currentSoundByteArray);
            }catch (IOException ed){}

            if (read>0) {
                byteArrayOutputStream.write(currentSoundByteArray, 0, read);
            }

        }


        byte[] b1 = byteArrayOutputStream.toByteArray();
        byte[] b2 = new byte[b1.length/playBackSpeed];
        for (int ii=0; ii<b2.length/frameSize; ii++) {
            for (int jj=0; jj<frameSize; jj++) {
                b2[(ii*frameSize)+jj] = b1[(ii*frameSize*playBackSpeed)+jj];
            }
        }


        ByteArrayInputStream bais = new ByteArrayInputStream(b2);
        AudioInputStream aisAccelerated =
                new AudioInputStream(bais, af, b2.length);
        Clip clip = null;

        try{
            clip = AudioSystem.getClip();
            clip.open(aisAccelerated);
        } catch (Exception ex){
            System.out.println(ex);
        }

        //clip.loop(2*playBackSpeed);
        clip.start();

    }


    public static void playAtSpeed(double speed, String file){

        double playBackSpeed = speed;

        System.out.println("Playback Rate: " + playBackSpeed);


        AudioFormat audioFormat = getFormat(file);
        byte[] original = getAudioByteArray(file);

        int frameSize = audioFormat.getFrameSize();
        //System.out.println(frameSize);

        byte[] output = new byte[(int) (original.length/playBackSpeed)];


        for (int i = 0; i < (int)(output.length / frameSize); i++) {

            for (int j = 0; j < frameSize; j++) {

                output[ min(output.length-1,( i * frameSize ) + j ) ] =
                        original[ min(original.length-1,
                                (int) ( ((i * frameSize * playBackSpeed ) - ((i * frameSize * playBackSpeed ) % 4 ))+ j
                                ) )];

            }

        }

        //System.out.println(Arrays.toString(output));

        playSound(output,audioFormat);

    }

    public static void playSpedUp(int speed, String file){

        double playBackSpeed = speed;

        System.out.println("Playback Rate: " + playBackSpeed);


        AudioFormat audioFormat = getFormat(file);
        byte[] original = getAudioByteArray(file);
        int frameSize = audioFormat.getFrameSize();


        byte[] output = new byte[(int) (original.length/playBackSpeed)];

        for (int i = 0; i < (int)(output.length / frameSize); i++) {
            for (int j = 0; j < frameSize; j++) {
                output[ min(output.length-1,( i * frameSize ) + j) ] =
                        original[ min(original.length-1,(int) ( (i * frameSize * playBackSpeed ) + j) )];
            }
        }


        playSound(output,audioFormat);

    }



    public static byte[] getAudioByteArray(String file){
        File audio = new File(file);


        AudioInputStream streamFromFile = null;
        try{
            streamFromFile = AudioSystem.getAudioInputStream(audio);

        } catch (Exception ex){
            System.out.println(ex);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] currentSoundByteArray = new byte[2^16];
        int read = 1;

        try {
            while( read>-1 ) {
                read = streamFromFile.read(currentSoundByteArray);
                if (read>0) {
                    byteArrayOutputStream.write(currentSoundByteArray, 0, read);
                }

            }}catch (IOException ed){}


        return byteArrayOutputStream.toByteArray();
    }

    public static AudioFormat getFormat(String file){
        File audio = new File(file);


        AudioInputStream streamFromFile = null;
        AudioFormat audioFormat = null;
        try{
            streamFromFile = AudioSystem.getAudioInputStream(audio);
            audioFormat = streamFromFile.getFormat();
        } catch (Exception ex){
            System.out.println(ex);
        }

        return audioFormat;
    }

    public static void playSound(byte[] sound,AudioFormat format){
        ByteArrayInputStream bais = new ByteArrayInputStream(sound);
        AudioInputStream aisAccelerated = new AudioInputStream(bais, format, sound.length);
        Clip clip = null;

        try{
            clip = AudioSystem.getClip();
            clip.open(aisAccelerated);
        } catch (Exception ex){
            System.out.println(ex);
        }

        clip.start();
    }

}
