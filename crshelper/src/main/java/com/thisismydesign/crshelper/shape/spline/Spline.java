package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
// TODO do not hardcode colors
import com.badlogic.gdx.graphics.Color;

import com.thisismydesign.crshelper.dto.*;
import com.thisismydesign.crshelper.iterator.*;
import com.thisismydesign.crshelper.shape.Intersectable;
import com.thisismydesign.crshelper.shape.Shape;

public class Spline extends Shape {

    public CatmullRomSpline<Vector2> path;

    public Vector2 controlPoints[];

    private SplinePoint intersection;

    private float length;
    private float height = 5f;
    private Vector2 beginPoint = new Vector2();
    private Vector2 endPoint = new Vector2();

    // TODO move
    private final boolean debug = true;

    public Spline(Color color, Vector2[] controlPoints) {
        super(color);

        this.controlPoints = controlPoints;
        this.path = new CatmullRomSpline<>(this.controlPoints, false);

        updateCalculatedData();
    }

    public Vector2[] getControlPoints() {
        return controlPoints.clone();
    }

    public void setControlPoints(Vector2[] controlPoints) {
        this.controlPoints = controlPoints.clone();
        updateCalculatedData();
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }

    public SplinePoint getIntersection() {
        return intersection;
    }

    @Override
    public Vector2 valueAt(float t) {
        return path.valueAt(new Vector2(), t);
    }

    @Override
    public float getLength() {
        return length;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(this.color);

        SinglePointSplineIterator iterator = new SinglePointSplineIterator(path);
        SplinePoint splinePoint;

        while ((splinePoint = iterator.getNext()) != null) {
            shapeRenderer.circle(splinePoint.point.x, splinePoint.point.y, height);
        }

        shapeRenderer.end();

        if (debug) {
            renderDebug(shapeRenderer);
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.WHITE);
        for (Vector2 v : controlPoints) {
            shapeRenderer.circle(v.x, v.y, 5);
        }

        shapeRenderer.setColor(Color.RED);

        for (Vector2 v : path.controlPoints) {
            shapeRenderer.circle(v.x, v.y, 5);
        }

        shapeRenderer.end();

        shapeRenderer.setColor(Color.GREEN);
        if (intersection != null)
            renderIntersection(shapeRenderer);
    }

    private void renderIntersection(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(intersection.point.x, intersection.point.y, 10);
        shapeRenderer.end();
    }

    public Vector2 intersect(Intersectable intersectable) {
        if (intersection != null)
            return intersection.point;
        else {
            updateCalculatedData();
            return calculateIntersection(intersectable);
        }
    }

    private Vector2 calculateIntersection(Intersectable intersectable) {
        SplinePoint intersect;
        Vector2 linePos;
        float linePrecision = Precision.calculate(intersectable.getLength());
        float splinePrecision = Precision.calculate(this.length);

        for (float t = 0f; t <= 1f; t += linePrecision) {
            linePos = intersectable.valueAt(t);

            if (isInXRange(linePos)) {
                int span = XFlatSpline.getContainingSpan(path, linePos, 0, path.spanCount - 1);
                intersect = XFlatSpline.getPointOnSpline(this.path, linePos, span, 0f, 1f, splinePrecision);
                if (intersect != null) {
                    this.setIntersection(intersect);
                    return intersect.point;
                }
            }
        }
        return null;
    }

    private void setIntersection(SplinePoint intersectionPoint) {
        this.intersection = intersectionPoint;
        // TODO guessedColorChange value resets for some reason, cpy ctr needed
//        guessedColorChange = new SplinePosition(SplinePosition.min(intersection.splinePosition, positionAtRatio.splinePosition));
//        diffColorChange = SplinePosition.max(intersection.splinePosition, positionAtRatio.splinePosition);
    }

    private SplinePoint findPositionAtRatio(float ratio) {
        float currentLength = 0f;
        DoublePointSplineIterator iterator = new DoublePointSplineIterator(path);
        SplinePointPair splinePointPair;

        while ((splinePointPair = iterator.getNext()) != null) {
            currentLength += splinePointPair.prevPoint.point.dst(splinePointPair.currentPoint.point);
            if (Math.abs(currentLength - (this.length * ratio)) <= Precision.allowedErrorInPixels) {
                return splinePointPair.currentPoint;
            }
        }

        throw new RuntimeException("Position at ratio not found.");
    }

    public static SplinePoint getPointOfSplineIfInRange(Vector2 point, Vector2 pointOfSpline, int span, float t) {
        return point.dst(pointOfSpline) <= Precision.allowedErrorInPixels ? new SplinePoint(pointOfSpline, span, t) : null;
    }

    private boolean isInXRange(Vector2 point) {
        return beginPoint.x <= point.x && point.x <= endPoint.x;
    }

    private float dstOnSpline(SplinePosition a, SplinePosition b) {
        float dst = 0f;
        SplinePosition first = SplinePosition.min(a, b);
        SplinePosition second = SplinePosition.max(a, b);

        DoublePointSplineIterator iterator = new DoublePointSplineIterator(path, first, second);
        SplinePointPair splinePointPair;

        while ((splinePointPair = iterator.getNext()) != null) {
            dst += splinePointPair.prevPoint.point.dst(splinePointPair.currentPoint.point);
        }

        return dst;
    }

    private void updateCalculatedData() {
        length = path.approxLength(100);
        path.valueAt(beginPoint, 0f);
        path.valueAt(endPoint, 1f);
    }

}
