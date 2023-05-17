
package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.helpers.constants.TextureHolder;
import com.mygdx.game.helpers.utilities.TimeRewindInterface;
import com.mygdx.game.nodes.CollisionShape;
import com.mygdx.game.nodes.StaticNode;
import com.mygdx.game.nodes.TextureEntity;

public class ExplodingBarrel extends StaticNode implements TimeRewindInterface {

    

    public ExplodingBarrel(){
        this(0,0);
    }

    public ExplodingBarrel(float x, float y) {
        super( x, y, getMaskLayers(), getMaskLayers());
    }

    public ExplodingBarrel init(float x, float y) {
        super.init( x, y, getMaskLayers(), getMaskLayers(LayerNames.EXPLODINGBARREL));
        
        lastSave = null;

        return this;
    }

    public void ready(){
        addChild( ObjectPool.get(TextureEntity.class).init(TextureHolder.endGate,0,0,0,0));
        lastChild().setName("open");

        addChild( ObjectPool.get(CollisionShape.class).init(8,16,0,-16));

        addToGroup("rewind");
    }

    @Override
    public void update(double delta) {
        super.update(delta);
    }


    @Override
    public ArrayList<Object> save() {
        ArrayList<Object> a = (ArrayList<Object>) ObjectPool.get(ArrayList.class);

        a.clear();

        a.add((ArrayList<Object>) ObjectPool.get(ArrayList.class)); //0
        ((ArrayList)a.get(0)).clear();
        ((ArrayList)a.get(0)).add(this.getClass());
        ((ArrayList)a.get(0)).add(this.getParent());
        ((ArrayList)a.get(0)).add(lastSave);

        a.add(new Vector2(position)); //1
        
        lastSave = a;
        return a;
    }

    @Override
    public <T> T init() {
        lastSave = null;
        return null;
    }

    @Override
    public <T> T load(Object... vars) {
        position.set((Vector2) vars[1]);

        return null;
    }

    ArrayList<Object> lastSave = null;
    @Override
    public void setLastSave(ArrayList<Object> save) {
        lastSave = save;
    }

    public void hit(Vector2 vel, float damage) {
        queueFree();

        

    }


}
