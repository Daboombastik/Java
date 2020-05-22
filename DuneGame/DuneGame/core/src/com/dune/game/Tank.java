package com.dune.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Tank {

    private Vector2 positionTank;
    private Texture texture;
    private float angle;
    private float speed;

    public Tank(float x, float y) {
        this.positionTank = new Vector2(x, y);
        this.texture = new Texture("tank.png");
        this.speed = 200.0f;
    }

    public void update(float dt) {

        if ( Gdx.input.isKeyPressed(Input.Keys.LEFT) ) {
            angle += 180.0f * dt;
        }
        if ( Gdx.input.isKeyPressed(Input.Keys.RIGHT) ) {
            angle -= 180.0f * dt;
        }
        if ( Gdx.input.isKeyPressed(Input.Keys.UP) ) {
            positionTank.x += speed * MathUtils.cosDeg(angle) * dt;
            positionTank.y += speed * MathUtils.sinDeg(angle) * dt;
        }

        checkBorders();
    }

    private void checkBorders() {
        while (positionTank.x < 0)
            positionTank.x++;
        while (positionTank.x > 1280)
            positionTank.x--;
        while (positionTank.y < 0)
            positionTank.y++;
        while (positionTank.y > 720)
            positionTank.y--;
    }

    public void render(SpriteBatch batch) {
            batch.draw(texture, positionTank.x - 40, positionTank.y - 40, 40, 40, 80, 80, 1, 1, angle, 0, 0, 80, 80, false, false);
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPositionTank() {
        return positionTank;
    }
}
