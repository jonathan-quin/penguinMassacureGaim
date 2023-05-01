package com.mygdx.game.helpers.utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.TimeParticle;
import com.mygdx.game.helpers.constants.LayerNames;
import com.mygdx.game.helpers.constants.ObjectPool;
import com.mygdx.game.nodes.ColliderObject;
import com.mygdx.game.nodes.Particle;
import com.mygdx.game.nodes.TextureEntity;

import java.util.ArrayList;

import static java.lang.Math.toRadians;

public class ParticleMaker {

    public static ArrayList<TimeParticle> makeBloodyParticlesFromSprite(TextureEntity sprite, Vector2 vel){

        System.out.println(vel);

        Texture texture = sprite.sprite.getTexture();

        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();

        ArrayList<TimeParticle> particles = (ArrayList<TimeParticle>) ObjectPool.getGarbage(ArrayList.class);

        particles.clear();

        for (int x = 0; x < texture.getWidth(); x++){
            for (int y = 0; y < texture.getHeight(); y++){

                Color tempColor = ObjectPool.getGarbage(Color.class).set(pixmap.getPixel(x, y));

                if (tempColor.a != 0){
                    particles.add((TimeParticle) ObjectPool.get(TimeParticle.class).init(
                            ObjectPool.getGarbage(Vector2.class).set(vel).scl((float) (0.5 + Math.random())),
                            0,
                            0f,
                            Math.random(),
                            400,
                            ObjectPool.getGarbage(Vector2.class).set(0, 0),
                            0,
                            0,
                            0.8,
                            false,
                            tempColor,
                            Color.RED,
                            + Math.random(),
                            0.2 + Math.random()));

                    TimeParticle part = particles.get(particles.size() - 1);

                    part.position.set(sprite.globalPosition);
                    int xFlip = sprite.getFlipX() ? 1 : -1;
                    part.position.add(texture.getWidth() / 2 * xFlip, texture.getHeight() / 2);
                    part.position.sub(x  * xFlip, y);
                    //part.setMaskLayers(ColliderObject.getMaskLayers(LayerNames.WALLS),ColliderObject.getMaskLayers());

                    System.out.println(part.vel);
                }


            }
        }

        return particles;

    }



}
