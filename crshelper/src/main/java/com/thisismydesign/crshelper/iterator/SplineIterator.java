package com.thisismydesign.crshelper.iterator;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.dto.*;

public class SplineIterator {
    protected CatmullRomSpline<Vector2> spline;
    protected SplinePoint currentPoint;
    private Precision precision;

    protected SplinePosition startPos;
    protected SplinePosition endPos;
    protected SplinePosition currentPos;
    boolean end;

    protected float spanLength;
    protected float currentPrecision;

    public SplineIterator(CatmullRomSpline<Vector2> spline, SplinePosition startPos, SplinePosition endPos, Precision precision) {
        this.spline = spline;
        this.precision = precision;

        this.startPos = startPos;
        this.endPos = endPos;
        this.currentPos = startPos;
        this.end = false;

        updatePrecision();
    }
    public SplineIterator(CatmullRomSpline<Vector2> spline, Precision precision) {
        this(spline, new SplinePosition(0, 0f), new SplinePosition(spline.spanCount - 1, 1f), precision);
    }

    protected SplinePoint getPoint(int s, float t) {
        return new SplinePoint(this.spline.valueAt(new Vector2(), s, t), new SplinePosition(s, t));
    }

    protected void step (float t) {
        currentPos.t += t;
        if (spanEndReached()) {
            currentPos.t = 0f;
            stepSpan(1);
        }
    }

    private boolean spanEndReached() {
        return (currentPos.span != endPos.span && currentPos.t > 1f) || (currentPos.span == endPos.span && currentPos.t > endPos.t);
    }

    private void stepSpan(int s) {
        currentPos.span += s;
        setEnd();
        if (!end) {
            updatePrecision();
        }
    }

    private void updatePrecision() {
        spanLength = calculateSpanLength(currentPos.span);
        currentPrecision = precision.calculate(spanLength);
    }

    private void setEnd() {
        end = currentPos.span > endPos.span;
    }

    // Done with fixed precision loop to avoid infinite callbacks caused by initializing an iterator inside an iterator
    public float calculateSpanLength(int s) {
        float length = 0f;
        Vector2 prevPosition = spline.valueAt(new Vector2(), s, 0f);
        Vector2 currentPosition;

        float samples = 100f;

        for(int i=0; i<=samples; i++) {
            float t = i/samples;
            currentPosition = spline.valueAt(new Vector2(), s, t);
            length += prevPosition.dst(currentPosition);
            prevPosition = currentPosition;
        }
        return length;
    }
}
