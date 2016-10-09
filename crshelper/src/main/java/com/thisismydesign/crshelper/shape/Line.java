package com.thisismydesign.crshelper.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Line extends Shape {

    private Vector2 startPoint;
    private Vector2 endPoint;
    private float width;
    private float length;

    public Line(Vector2 startPoint, Vector2 endPoint, float width) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.width = width;
        length = startPoint.dst(endPoint);
    }

    public Line(Vector2 startPoint, Vector2 endPoint) {
        this(startPoint, endPoint, 5f);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, Color color) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);

        shapeRenderer.rectLine(startPoint, endPoint, width);

        shapeRenderer.end();
    }

    @Override
    public Vector2 valueAt(float t) {
        if (t < 0f || t > 1f) return null;

        float x2 = startPoint.x - (startPoint.x - endPoint.x) * t;
        float y2 = startPoint.y - (startPoint.y - endPoint.y) * t;

        return new Vector2(x2, y2);
    }

    @Override
    public float getLength() {
        return startPoint.dst(endPoint);
    }

    public boolean isInside(Vector2 point) {
        if (startPoint.x <= point.x && point.x >= endPoint.x) {
            Vector2 linePoint = valueAt((point.x - startPoint.x) / length);
            return linePoint.y - width <= point.y && point.y <= linePoint.y + width;
        } else
            return false;
    }
}
