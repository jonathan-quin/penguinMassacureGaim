package com.mygdx.game.nodes.collisionShapeHelpers;

import com.badlogic.gdx.math.Vector2;

public class sweepInfo {


        float length;

        float time;
        public Vector2 firstImpact;

        public sweepInfo() {
            this.time = 1;
            this.firstImpact = new Vector2();
        }

    
}
