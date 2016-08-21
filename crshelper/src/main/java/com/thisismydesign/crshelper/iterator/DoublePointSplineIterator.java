package com.thisismydesign.crshelper.iterator;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.dto.*;

public class DoublePointSplineIterator extends SplineIterator {
    private SplinePoint prevPoint;

    public DoublePointSplineIterator(CatmullRomSpline<Vector2> spline, SplinePosition startPos, SplinePosition endPos) {
        super(spline, startPos, endPos);
        init();
    }
    public DoublePointSplineIterator(CatmullRomSpline<Vector2> spline) {
        super(spline);
        init();
    }

    private void init() {
        prevPoint = new SplinePoint(this.spline.valueAt(new Vector2(), currentPos.span, currentPos.t), new SplinePosition(currentPos.span, currentPos.t));
    }

    public SplinePointPair getNext() {
        if (!end) {
            currentPoint = getPoint(currentPos.span, currentPos.t);
            step(precision);
            SplinePointPair pointPair = new SplinePointPair(prevPoint, currentPoint);
            prevPoint = currentPoint;
            return pointPair;
        } else
            return null;
    }
}
