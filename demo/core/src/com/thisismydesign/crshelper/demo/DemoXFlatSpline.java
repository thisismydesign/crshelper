package com.thisismydesign.crshelper.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.iterator.Precision;
import com.thisismydesign.crshelper.shape.Intersectable;
import com.thisismydesign.crshelper.shape.spline.XFlatSpline;

public class DemoXFlatSpline extends XFlatSpline {

    public DemoXFlatSpline(Vector2[] controlPoints, Precision precisionHelper) {
        super(controlPoints, precisionHelper);
    }

    public void renderDebug(ShapeRenderer shapeRenderer) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.RED);
        for (Vector2 v : path.controlPoints) {
            shapeRenderer.circle(v.x, v.y, 5);
        }

        shapeRenderer.end();

        shapeRenderer.setColor(Color.GREEN);
    }

    public long timedIntersect(Intersectable intersectable) {
        long start = System.nanoTime();
        intersect(intersectable);
        return System.nanoTime() - start;
    }
}
