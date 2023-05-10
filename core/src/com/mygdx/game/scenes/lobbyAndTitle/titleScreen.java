package com.mygdx.game.scenes.lobbyAndTitle;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.EndLevelGate;
import com.mygdx.game.entities.ParalaxBackground;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.levelCreator;
import com.mygdx.game.helpers.constants.Globals;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.constants.TileMapHolder;
import com.mygdx.game.nodes.Node;
import com.mygdx.game.nodes.Root;
import com.mygdx.game.nodes.TextureEntity;
import com.mygdx.game.nodes.TileMapProcessor;

public class titleScreen extends Root {

    Node focusStealer = new Node(){
        public void update(double delta){
            Globals.cameraOffset.set(656f,-296f);
            Globals.camera.setToOrtho(false, (float) (Globals.screenSize.x* 0.85), (float) (Globals.screenSize.y* 0.85));
            //System.out.println(new Vector2((TileMapHolder.titleScreen.width * TileMapHolder.titleScreen.tileSize)/2f,-(TileMapHolder.titleScreen.height * TileMapHolder.titleScreen.tileSize)/2f));
        }

        public void free(){

        }

    };

    public void open() {

        rootNode = poolGet(Node.class).init(0, 0);
        rootNode.setMyRoot(this);

        add(ObjectPool.get(TextureEntity.class).init(TextureHolder.georgeParalaxMountainsAndTrees,656f,-200,0,0));

        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(552.0f,-336.0f,"","","tutorial1"));
        add(ObjectPool.get(Node.class).init(0,0));
        last().addChild(poolGet(EndLevelGate.class).init(792.0f,-336.0f,"","","lobby"));



        add(poolGet(Player.class).init(680.0f,-416.0f));

        add(focusStealer);

        //add(ObjectPool.get(levelCreator.class).init());

        add(poolGet(TileMapProcessor.class).init(TileMapHolder.titleScreen));

    }

    public void close(){
        super.close();
        Globals.camera.setToOrtho(false, (float) (Globals.screenSize.x * 0.65), (float) (Globals.screenSize.y* 0.65));
    }

}