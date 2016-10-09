package com.thisismydesign.crshelper.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Shape implements Intersectable {
    public abstract void render(ShapeRenderer shapeRenderer, Color color);
}
