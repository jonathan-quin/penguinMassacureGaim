package com.mygdx.game.nodes;

public class StaticNode extends ColliderObject {



    public StaticNode(Root myRoot){
        this(myRoot,0,0, new int[]{0}, new int[]{0});
    }

    public StaticNode(Root myRoot, float x, float y, int[] mask, int[] layers){
        super(myRoot,x,y,mask,layers);
    }


}
