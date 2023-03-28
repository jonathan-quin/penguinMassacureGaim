package com.mygdx.game.nodes;

import java.util.ArrayList;

public class StaticNode extends ColliderObject {



    public StaticNode(Root myRoot){
        this(myRoot,0,0, getMaskLayers(0), getMaskLayers(0));
    }

    public StaticNode(Root myRoot, float x, float y, ArrayList<Integer> mask, ArrayList<Integer> layers){
        super(x,y,mask,layers);
    }


}
