package com.mygdx.game.scenes.lobbyAndLevels;

import com.mygdx.game.entities.EndLevelGate;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.guns.penguinGuns.PenguinMiniGun;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;
import com.mygdx.game.nodes.TileMapProcessor;

public class Lobby extends Root {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");

        add(poolGet(EndLevelGate.class).init(300,-547,"lobby-Level1","","Level1"));
        add(poolGet(EndLevelGate.class).init(440,-547,"lobby-Level2","","Level2"));
        add(poolGet(EndLevelGate.class).init(640,-547,"lobby-Level3","","Level3"));



        add(poolGet(Player.class).init(496.0f , -400.0f));
        ((Player) last()).takeGun(PenguinMiniGun.class);



        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tempLobby));

    }

}
