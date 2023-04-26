package com.mygdx.game.scenes.lobbyAndLevels;

import com.mygdx.game.entities.EndLevelGate;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.guns.penguinGuns.PenguinRevolver;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;
import com.mygdx.game.nodes.TileMapProcessor;
import com.mygdx.game.nodes.TimeRewindRoot;

public class tempLevel1 extends TimeRewindRoot {

    public void open(){

        rootNode = poolGet(Node.class);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,0));

        last().setName("bulletHolder");

        add(poolGet(EndLevelGate.class).init(915,-386,"","lobby-Level2","Lobby"));

        add(poolGet(Player.class).init(115,-260));
        ((Player) last()).takeGun(PenguinRevolver.class);



        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tempLevel1));

    }

}
