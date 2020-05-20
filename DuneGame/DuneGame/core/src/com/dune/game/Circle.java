package com.dune.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Circle {

    private ShapeRenderer shapeRenderer;
    private Vector2 positionCircle;
    private float initX = (float) Math.random() * 1000;
    private float initY = (float) Math.random() * 500;
    private final int radius = 50;

    public Circle() {
        shapeRenderer = new ShapeRenderer();
        positionCircle = new Vector2(initX, initY);
    }

    public void render() {
        shapeRenderer.setColor(Color.ROYAL);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(positionCircle.x, positionCircle.y, radius);
        shapeRenderer.end();
    }

    public void update(Tank tank) {
        if ( tank.getPositionTank().x == positionCircle.x-radius ||
             tank.getPositionTank().y == positionCircle.y-radius ) {
            positionCircle.x = (float) Math.random() * 1280;
            positionCircle.y = (float) Math.random() * 720;
        }
    }
}
