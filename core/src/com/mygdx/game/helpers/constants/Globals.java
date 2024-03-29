package com.mygdx.game.helpers.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

import static com.mygdx.game.helpers.constants.Globals.Sounds.*;

public class Globals {

    public static double gameSpeed = 1;

    public static OrthographicCamera camera;
    public static MapRenderer mapRenderer;

    public static boolean showCollision = false;
    public static boolean aiIgnore = false;

    public static boolean sceneJustChanged = true;

    public static Vector2 screenStretch = new Vector2(1,1);

    public static double playerDeadTime = -1;

    public static int timeRootStage = 0;

    public static boolean currentlyRewinding = false;

    public static Vector2 screenSize =  new Vector2(1024,600);

    public static ShapeRenderer globalShape = new ShapeRenderer();

    public static Vector2 cameraOffset = new Vector2(0,0);

    public static HashMap<String, Boolean> lobbyDoorsOpen = new HashMap<>();

    public static HashMap<String, Sound> sounds = new HashMap<>();

    public Globals(){

    }

    public static void globalsInit(){

        lobbyDoorsOpen.put("lobby-Level1",true);
        lobbyDoorsOpen.put("lobby-Level2",false);
        lobbyDoorsOpen.put("lobby-Level3",false);

        lobbyDoorsOpen.put("lobby1",true);
        lobbyDoorsOpen.put("lobby2",false);
        lobbyDoorsOpen.put("lobby3",false);
        lobbyDoorsOpen.put("lobby4",false);
        lobbyDoorsOpen.put("lobby5",false);
        lobbyDoorsOpen.put("lobby6",false);
        lobbyDoorsOpen.put("lobby7",false);
        lobbyDoorsOpen.put("lobby8",false);
        lobbyDoorsOpen.put("lobby9",false);
        lobbyDoorsOpen.put("lobby10",false);



        sounds.put(ELFDIE, Gdx.audio.newSound(Gdx.files.internal("sounds/elf explode.wav")));
        sounds.put(BULLETSOFT, Gdx.audio.newSound(Gdx.files.internal("sounds/bullet impact 1.wav")));
        sounds.put(BULLETHARD, Gdx.audio.newSound(Gdx.files.internal("sounds/bullet impact 3.wav")));
        sounds.put(JUMP, Gdx.audio.newSound(Gdx.files.internal("sounds/jump noisee.mp3")));
        sounds.put(SHOTGUNSHOOT, Gdx.audio.newSound(Gdx.files.internal("sounds/shotgun shot.mp3")));
        sounds.put(REVOLVERSHOOT, Gdx.audio.newSound(Gdx.files.internal("sounds/revolver shotFinal.mp3")));
        sounds.put(MINIGUNSHOOT, Gdx.audio.newSound(Gdx.files.internal("sounds/miniGunShot.mp3")));
        sounds.put(REWINDSTATIC, Gdx.audio.newSound(Gdx.files.internal("sounds/staticcc.wav")));

    }

    public static class Sounds{
        public static String ELFDIE = "elfDie";
        public static String BULLETSOFT = "bulletHitSoft";
        public static String BULLETHARD = "bulletHitHard";
        public static String JUMP = "jump";

        public static String SHOTGUNSHOOT = "shotgunShoot";
        public static String REVOLVERSHOOT = "shotgunShoot";
        public static String MINIGUNSHOOT = "shotgunShoot";

        public static String REWINDSTATIC = "rewindStatic";

    }

    public static float inverse(float num){
        if (num == 0) return 0;

        return 1f/num;


    }

    public static double inverse(double num){
        if (num == 0) return 0;

        return 1f/num;
    }




}
