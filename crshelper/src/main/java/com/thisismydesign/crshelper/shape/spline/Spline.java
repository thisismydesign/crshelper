package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import com.thisismydesign.crshelper.dto.*;
import com.thisismydesign.crshelper.iterator.*;
import com.thisismydesign.crshelper.shape.Intersectable;
import com.thisismydesign.crshelper.shape.Shape;

public class Spline extends Shape {

    public CatmullRomSpline<Vector2> path;

    public Vector2 controlPoints[];

    protected SplinePoint intersection;

    protected float length;
    protected float height = 1f;
    protected Vector2 beginPoint = new Vector2();
    protected Vector2 endPoint = new Vector2();

    protected Precision precisionHelper;

    public Precision getPrecisionHelper() {
        return precisionHelper;
    }

    public Spline(Vector2[] controlPoints, Precision precisionHelper) {
        this.controlPoints = controlPoints.clone();
        this.path = new CatmullRomSpline<>(this.controlPoints, false);
        this.precisionHelper = precisionHelper;

        updateCalculatedData();
    }

    public Spline(Spline s) {
        this.controlPoints = s.getControlPoints();
        this.path = new CatmullRomSpline<>(this.controlPoints, false);
        this.precisionHelper = s.getPrecisionHelper();

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
    public void render(ShapeRenderer shapeRenderer, Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);

        SinglePointSplineIterator iterator = new SinglePointSplineIterator(path, precisionHelper);
        SplinePoint splinePoint;

        while ((splinePoint = iterator.getNext()) != null) {
            shapeRenderer.circle(splinePoint.point.x, splinePoint.point.y, height);
        }

        shapeRenderer.end();
    }

    public Vector2 intersect(Intersectable intersectable) {
        SinglePointSplineIterator iterator;
        SplinePoint splinePoint;
        float intersectablePrecision = precisionHelper.calculate(intersectable.getLength());
        SplinePoint intersection;
        Vector2 intersectablePoint;

        for (float t = 0f; t <= 1f; t+= intersectablePrecision ) {
            intersectablePoint = intersectable.valueAt(t);

            iterator = new SinglePointSplineIterator(path, precisionHelper);

            while ((splinePoint = iterator.getNext()) != null) {
                intersection = getPointOfSplineIfInRange(intersectablePoint, splinePoint.point, splinePoint.splinePosition.span, splinePoint.splinePosition.t);
                if (intersection != null) {
                    setIntersection(intersection);
                    return intersection.point;
                }
            }
        }

        throw new RuntimeException("Intersection not found.");
    }

    public Spline move(Vector2 v) {
        for (int i=0; i<controlPoints.length; i++) {
            controlPoints[i] = new Vector2(controlPoints[i].x + v.x, controlPoints[i].y + v.y);
        }
        updateCalculatedData();

        return this;
    }

    protected void renderIntersection(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(intersection.point.x, intersection.point.y, 10);
        shapeRenderer.end();
    }

    protected void setIntersection(SplinePoint intersectionPoint) {
        this.intersection = intersectionPoint;
    }

    private SplinePoint findPositionAtRatio(float ratio) {
        float currentLength = 0f;
        DoublePointSplineIterator iterator = new DoublePointSplineIterator(path, precisionHelper);
        SplinePointPair splinePointPair;

        while ((splinePointPair = iterator.getNext()) != null) {
            currentLength += splinePointPair.prevPoint.point.dst(splinePointPair.currentPoint.point);
            if (Math.abs(currentLength - (this.length * ratio)) <= precisionHelper.getAllowedErrorInPixels()) {
                return splinePointPair.currentPoint;
            }
        }

        throw new RuntimeException("Position at ratio not found.");
    }

    public SplinePoint getPointOfSplineIfInRange(Vector2 point, Vector2 pointOfSpline, int span, float t) {
        return point.dst(pointOfSpline) <= precisionHelper.getAllowedErrorInPixels() ? new SplinePoint(pointOfSpline, span, t) : null;
    }

    private float dstOnSpline(SplinePosition a, SplinePosition b) {
        float dst = 0f;
        SplinePosition first = SplinePosition.min(a, b);
        SplinePosition second = SplinePosition.max(a, b);

        DoublePointSplineIterator iterator = new DoublePointSplineIterator(path, first, second, precisionHelper);
        SplinePointPair splinePointPair;

        while ((splinePointPair = iterator.getNext()) != null) {
            dst += splinePointPair.prevPoint.point.dst(splinePointPair.currentPoint.point);
        }

        return dst;
    }

    protected void updateCalculatedData() {
        length = path.approxLength(100);
        path.valueAt(beginPoint, 0f);
        path.valueAt(endPoint, 1f);
    }

}
