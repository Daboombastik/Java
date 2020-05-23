package com.dune.game.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.Tank;

public class Circle {

    private final ShapeRenderer shapeRenderer;
    private final Vector2 positionCircle;
    private final int radius = 50;

    public Circle() {
        shapeRenderer = new ShapeRenderer();
        float initX = (float) Math.random() * 1000;
        float initY = (float) Math.random() * 500;
        positionCircle = new Vector2(initX, initY);
    }

    public void render() {
        shapeRenderer.setColor(Color.ROYAL);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(positionCircle.x, positionCircle.y, radius);
        shapeRenderer.end();
    }

    public void update(Tank tank) {
        if (
                tank.getPosition().x+20 >= positionCircle.x - radius &&
                tank.getPosition().y+20 >= positionCircle.y - radius &&
                tank.getPosition().x-20 <= positionCircle.x + radius &&
                tank.getPosition().y-20 <= positionCircle.y + radius
        )
            changeCircleCoordinates();
    }

    private void changeCircleCoordinates() {
        positionCircle.x = (float) Math.random() * 1000;
        positionCircle.y = (float) Math.random() * 500;
    }
}
