package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.guns.elfGuns.ElfMiniGun;
import com.mygdx.game.entities.guns.elfGuns.ElfRevolver;
import com.mygdx.game.entities.guns.elfGuns.ElfShotgun;
import com.mygdx.game.entities.guns.floorGuns.FloorMiniGun;
import com.mygdx.game.entities.guns.floorGuns.FloorRevolver;
import com.mygdx.game.entities.guns.floorGuns.FloorShotgun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinMiniGun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.entities.guns.penguinGuns.PenguinShotgun;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.SceneHandler;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.Utils;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.TextureEntity;

public class levelCreator extends Node {

    protected enum State {
        ELF,
        PLAYER,
        GATE,
        GUN
    }

    State state = State.PLAYER;

    ArrayList<Class> elfGuns = new ArrayList<>();
    ArrayList<Class> floorGuns = new ArrayList<>();
    ArrayList<Class> playerGuns = new ArrayList<>();

    ArrayList<Texture> gunTextures = new ArrayList<>();

    ArrayList<String> outputs = new ArrayList<>();

    int gunNumber = 0;

    boolean specialState = false;

    TextureEntity sprite;
    TextureEntity gunSprite;

    public levelCreator(){
        elfGuns.add(ElfRevolver.class);
        elfGuns.add(ElfShotgun.class);
        elfGuns.add(ElfMiniGun.class);

        floorGuns.add(FloorRevolver.class);
        floorGuns.add(FloorShotgun.class);
        floorGuns.add(FloorMiniGun.class);

        playerGuns.add(PenguinRevolver.class);
        playerGuns.add(PenguinShotgun.class);
        playerGuns.add(PenguinMiniGun.class);

        gunTextures.add(TextureHolder.redRevolver);
        gunTextures.add(TextureHolder.redShotgun);
        gunTextures.add(TextureHolder.redMiniGun);
    }
    public levelCreator init(){
        super.init(0,0);

        outputs.clear();

        return this;
    }

    public void ready(){

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.elfTexture, 0, 0, 0, 0));
        sprite = (TextureEntity) lastChild();

        addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.redRevolver, 0, 0, 0, 0));
        gunSprite = (TextureEntity) lastChild();
    }

    public void update(double delta){

        if (Gdx.input.isKeyPressed(Input.Keys.A)) Globals.cameraOffset.x -= 10;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) Globals.cameraOffset.x += 10;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) Globals.cameraOffset.y -= 10;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) Globals.cameraOffset.y += 10;

        Globals.camera.setToOrtho(false, (float) (Globals.screenSize.x), (float) (Globals.screenSize.y));

        for (Node obj : getGroupHander().getNodesInGroup("rewind")){
            obj.queueFree();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            switch (state){
                case PLAYER:{
                    state = State.ELF;
                    break;
                }
                case ELF:{
                    state = State.GATE;
                    break;
                }
                case GATE:{
                    state = State.GUN;
                    break;
                }
                case GUN:{
                    state = State.PLAYER;
                    break;
                }
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            switch (state){
                case PLAYER:{
                    state = State.GUN;
                    break;
                }
                case ELF:{
                    state = State.PLAYER;
                    break;
                }
                case GATE:{
                    state = State.ELF;
                    break;
                }
                case GUN:{
                    state = State.GATE;
                    break;
                }
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || false){
            gunNumber += 1;
            if (gunNumber > 2) gunNumber = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            gunNumber -= 1;
            if (gunNumber < 0) gunNumber = 2;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.L)){
            System.out.println(SceneHandler.getCurrentScene());
        }


        Vector2 tempValue = Utils.getGlobalMousePosition();

        //tempValue.add(8,0);

        tempValue.set(16 * (int) (tempValue.x/16),16 * (int) (tempValue.y/16));

        tempValue.add(8,0);

        gunSprite.init(gunTextures.get(gunNumber),tempValue.x, tempValue.y,0,0);

        boolean place = Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
        boolean remove = Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE) || Gdx.input.isKeyJustPressed(Input.Keys.T)) {
         specialState = !specialState;
        }

        sprite.setFlip(false,false);

        switch (state){
            case ELF:{
                sprite.init(TextureHolder.elfTexture,tempValue.x, tempValue.y,0,0);
                sprite.setVisible(true);
                gunSprite.setVisible(true);
                sprite.setFlip(specialState,false);

                if (place){
                    String temp = "add(poolGet(Elf.class).init(" + tempValue.x + "f , " + tempValue.y + "f ,ObjectPool.get(" +(elfGuns.get(gunNumber).toString()).substring(elfGuns.get(gunNumber).toString().lastIndexOf(".")+1) + ".class)," + !specialState +"));";
                    outputs.add(temp);
                }

                break;
            }
            case GATE:{
                sprite.init(TextureHolder.endGate,tempValue.x, tempValue.y,0,0);
                sprite.setVisible(true);
                gunSprite.setVisible(false);

                if (place) {
                    outputs.add("add(ObjectPool.get(Node.class).init(0,0));\n last().addChild(poolGet(EndLevelGate.class).init(" + tempValue.x + "f," + tempValue.y +"f,\"\",\"\",\"NEXTSCENE\"));");
                }

                break;
            }
            case PLAYER:{
                sprite.init(TextureHolder.penguinTexture,tempValue.x, tempValue.y,0,0);
                sprite.setVisible(true);

                gunSprite.setVisible(!specialState);

                if (place) {
                    String temp = "add(poolGet(Player.class).init(" + tempValue.x + "f," + tempValue.y +"f));";

                    if (! specialState) temp += "\n((Player) last()).takeGun(" + (playerGuns.get(gunNumber).toString()).substring(playerGuns.get(gunNumber).toString().lastIndexOf(".")+1) + ".class);";

                    outputs.add( temp);

                }

                break;
            }
            case GUN:{
                sprite.setVisible(false);
                gunSprite.setVisible(true);

                gunSprite.position.y -= 12;
                tempValue.y -= 12;

                if (place){
                    String temp = "add(poolGet(" +(floorGuns.get(gunNumber).toString()).substring(floorGuns.get(gunNumber).toString().lastIndexOf(".")+1) + ".class).init(" + tempValue.x + "f," + tempValue.y +"f));";

                    outputs.add(temp);
                }

                break;
            }


        }

        if (place){


            addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.elfTexture, 0, 0, 0, 0));
            sprite = (TextureEntity) lastChild();

            addChild(ObjectPool.get(TextureEntity.class).init(TextureHolder.redRevolver, 0, 0, 0, 0));
            gunSprite = (TextureEntity) lastChild();

            System.out.println("\n");

            for (String str :outputs){
                System.out.println(str);
            }

        }

        if (remove){
            if (outputs.size() > 0){
                outputs.remove(outputs.size()-1);

                children.remove(children.size()-3);
                children.remove(children.size()-3);
            }

            System.out.println("\n");
            for (String str :outputs){
                System.out.println(str);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            SceneHandler.goToNextScene();
        }


    }

}
