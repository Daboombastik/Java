package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dune.game.core.GameController;

public class GameRenderer {
    private SpriteBatch batch;
    private GameController gameController;

    public GameRenderer(SpriteBatch batch, GameController gameController) {
        this.batch = batch;
        this.gameController = gameController;
    }

    public void render () {
        Gdx.gl.glClearColor(0, 0.4f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gameController.getTank().render(batch);
        batch.end();

    }
}
