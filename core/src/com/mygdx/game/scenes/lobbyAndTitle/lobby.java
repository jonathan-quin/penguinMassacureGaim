package com.mygdx.game.scenes.lobbyAndTitle;

import com.mygdx.game.entities.EndLevelGate;
import com.mygdx.game.entities.ParalaxBackground;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.guns.floorGuns.FloorMiniGun;
import com.mygdx.game.entities.guns.floorGuns.FloorShotgun;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;
import com.mygdx.game.nodes.TileMapProcessor;

public class lobby extends Root {

    public void open(){

        rootNode = poolGet(Node.class).init(0,0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(Node.class).init(0,-300));
        last().addChild(ObjectPool.get(ParalaxBackground.class).init(0,0, TextureHolder.georgeParalaxMountainsAndTrees));

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.lobby));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(536.0f,-704.0f,"lobby1","","levelJ"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(712.0f,-704.0f,"lobby2","","levelG"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(872.0f,-688.0f,"lobby3","","levelE"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(1000.0f,-704.0f,"lobby4","","levelB"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(968.0f,-528.0f,"lobby5","","levelA"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(1304.0f,-704.0f,"lobby6","","levelI"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(1464.0f,-672.0f,"lobby7","","levelD"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(1768.0f,-672.0f,"lobby8","","levelC"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(1608.0f,-528.0f,"lobby9","","levelF"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(1368.0f,-496.0f,"lobby10","","levelH"));
        add(ObjectPool.get(Node.class).init(0,0));


        add(ObjectPool.get(Node.class).init(0,0));
        last().setName("bulletHolder");


        last().addChild(poolGet(EndLevelGate.class).init(824.0f,-400.0f,"","","titleScreen"));
        add(poolGet(Player.class).init(616.0f,-576.0f));


        add(poolGet(FloorMiniGun.class).init(648.0f,-588.0f));
        add(poolGet(FloorMiniGun.class).init(504.0f,-652.0f));
        add(poolGet(FloorMiniGun.class).init(1144.0f,-844.0f));
        add(poolGet(FloorMiniGun.class).init(1672.0f,-860.0f));
        add(poolGet(FloorMiniGun.class).init(712.0f,-860.0f));
        add(poolGet(FloorMiniGun.class).init(456.0f,-844.0f));
        add(poolGet(FloorShotgun.class).init(616.0f,-812.0f));
        add(poolGet(FloorShotgun.class).init(648.0f,-748.0f));
        add(poolGet(FloorShotgun.class).init(568.0f,-588.0f));
        add(poolGet(FloorShotgun.class).init(1064.0f,-620.0f));
        add(poolGet(FloorShotgun.class).init(1160.0f,-700.0f));
        add(poolGet(FloorShotgun.class).init(1496.0f,-588.0f));
        add(poolGet(FloorShotgun.class).init(1560.0f,-828.0f));
        add(poolGet(FloorShotgun.class).init(1432.0f,-828.0f));
        add(poolGet(FloorShotgun.class).init(1640.0f,-716.0f));
        add(poolGet(FloorShotgun.class).init(1848.0f,-636.0f));
        add(poolGet(FloorMiniGun.class).init(1752.0f,-572.0f));






    }

}
