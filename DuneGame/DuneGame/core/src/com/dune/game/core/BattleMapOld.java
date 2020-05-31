package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BattleMapOld {
    private TextureRegion grassTexture;
    private TextureRegion ammo;
    private final int [][] ammoRandomArray = new int [7][2];
    private Vector2 ammoPosition;


    public BattleMapOld() {
        this.grassTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.ammo = Assets.getInstance().getAtlas().findRegion("bullet");
        this.ammoPosition = new Vector2();
        ammoFillArray();

    }

    private void ammoFillArray() {
        for (int i = 0; i < ammoRandomArray.length; i++) {
            for (int j = 0; j < ammoRandomArray[i].length; j++) {
                ammoRandomArray[i][j] = MathUtils.random(1000);
            }
        }
    }


    public void render(SpriteBatch batch, Tank tank) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(grassTexture, i * 80, j * 80);
            }
        }

        for (int[] ints : ammoRandomArray) {
            for (int j = 0; j < ints.length - 1; j++) {
                batch.draw(ammo, ints[j], ints[j + 1], 8, 8, 16, 16, 2, 2, 0);
                if ( tank.getPosition().x + 40 >= ints[j] &&
                     tank.getPosition().x - 40 <= ints[j] &&
                     tank.getPosition().y + 40 >= ints[j + 1] &&
                     tank.getPosition().y - 40 <= ints[j + 1] ) {
                    //здесь танк "подбирает" ресурс
                    ints[j] = -100;
                    ints[j + 1] = -100;
                }
            }
        }
    }
}
