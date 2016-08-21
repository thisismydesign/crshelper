package com.thisismydesign.crshelper.iterator;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.dto.*;

public class SinglePointSplineIterator extends SplineIterator {
    public SinglePointSplineIterator(CatmullRomSpline<Vector2> spline, SplinePosition startPos, SplinePosition endPos) {
        super(spline, startPos, endPos);
    }
    public SinglePointSplineIterator(CatmullRomSpline<Vector2> spline) {
        super(spline);
    }

    public SplinePoint getNext() {
        if(!end) {
            currentPoint = getPoint(currentPos.span, currentPos.t);
            step(precision);
            return currentPoint;
        } else
            return null;
    }
}
