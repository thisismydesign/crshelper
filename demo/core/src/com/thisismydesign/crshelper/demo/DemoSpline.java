package com.thisismydesign.crshelper.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.dto.SplinePointPair;
import com.thisismydesign.crshelper.iterator.DoublePointSplineIterator;
import com.thisismydesign.crshelper.iterator.Precision;
import com.thisismydesign.crshelper.shape.spline.Spline;

public class DemoSpline extends Spline {

    public DemoSpline(Vector2[] controlPoints, Precision precisionHelper) {
        super(controlPoints, precisionHelper);
    }

    public DemoSpline(DemoSpline s) {
        super(s);
    }

    public void renderDebug(ShapeRenderer shapeRenderer) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.RED);
        for (Vector2 v : path.controlPoints) {
            shapeRenderer.circle(v.x, v.y, 5);
        }

        shapeRenderer.end();

        shapeRenderer.setColor(Color.GREEN);
        if (intersection != null)
            renderIntersection(shapeRenderer);
    }

    public void dumbRender(ShapeRenderer shapeRenderer) {
        float precision = precisionHelper.calculate(length);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.MAGENTA);

        for (float t = 0f; t <= 1f; t+= precision ) {
            Vector2 point = new Vector2();
            path.valueAt(point, t);
            shapeRenderer.circle(point.x, point.y, height);
        }

        shapeRenderer.end();

        renderDebug(shapeRenderer);
    }

    public void dumbLineRender(ShapeRenderer shapeRenderer) {
        float precision = precisionHelper.calculate(length);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.MAGENTA);
        Vector2 previousPint = path.valueAt(new Vector2(), 0f);

        for (float t = 0f; t <= 1f; t+= precision ) {
            Vector2 point = new Vector2();
            path.valueAt(point, t);
            shapeRenderer.line(previousPint.x, previousPint.y,
                    point.x, point.y);
            previousPint = point;
        }

        shapeRenderer.end();

        renderDebug(shapeRenderer);
    }

    public void lineRender(ShapeRenderer shapeRenderer, Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);

        DoublePointSplineIterator iterator = new DoublePointSplineIterator(path, precisionHelper);
        SplinePointPair splinePointPair;

        while ((splinePointPair = iterator.getNext()) != null) {
            shapeRenderer.line(splinePointPair.prevPoint.point.x, splinePointPair.prevPoint.point.y,
                    splinePointPair.currentPoint.point.x, splinePointPair.currentPoint.point.y);
        }

        shapeRenderer.end();

        renderDebug(shapeRenderer);
    }
}
