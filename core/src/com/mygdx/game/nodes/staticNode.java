package com.mygdx.game.nodes;

public class staticNode extends colliderObject {



    public staticNode(root myRoot){
        this(myRoot,0,0, new int[]{0}, new int[]{0});
    }

    public staticNode(root myRoot,float x, float y,int[] mask,int[] layers){
        super(myRoot,x,y,mask,layers);
    }


}
