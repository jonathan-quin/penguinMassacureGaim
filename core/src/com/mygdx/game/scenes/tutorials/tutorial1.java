package com.mygdx.game.scenes.tutorials;

import com.mygdx.game.entities.EndLevelGate;
import com.mygdx.game.entities.ParalaxBackground;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.levelCreator;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;
import com.mygdx.game.nodes.TileMapProcessor;

public class tutorial1 extends Root {

    public void open() {

        rootNode = poolGet(Node.class).init(0, 0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,30));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(poolGet(Player.class).init(968.0f,-432.0f));
        add(poolGet(EndLevelGate.class).init(1672.0f,-272.0f,"","","NEXTSCENE"));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.tutorial1));
    }

}
