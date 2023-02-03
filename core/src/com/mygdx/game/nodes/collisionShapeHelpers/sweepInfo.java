package com.mygdx.game.nodes.collisionShapeHelpers;

import com.badlogic.gdx.math.Vector2;

public class sweepInfo {


        public hitInfo hit;
        public Vector2 pos;
        public float time;

        public sweepInfo() {
            this.hit = null;
            this.pos = new Vector2();
            this.time = 1;
        }

    
}
