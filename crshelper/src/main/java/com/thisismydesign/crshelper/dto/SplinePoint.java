package com.thisismydesign.crshelper.dto;

import com.badlogic.gdx.math.Vector2;

public class SplinePoint {
    public Vector2 point;
    public SplinePosition splinePosition;

    public SplinePoint(Vector2 point, SplinePosition splinePosition) {
        this.point = point;
        this.splinePosition = splinePosition;
    }
    public SplinePoint(Vector2 point, int span, float t) {
        this.point = point;
        this.splinePosition = new SplinePosition(span, t);
    }
}
