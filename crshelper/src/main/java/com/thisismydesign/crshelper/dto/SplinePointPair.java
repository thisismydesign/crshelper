package com.thisismydesign.crshelper.dto;

public class SplinePointPair {
    public SplinePoint prevPoint;
    public SplinePoint currentPoint;

    public SplinePointPair(SplinePoint prevPoint, SplinePoint currentPoint) {
        this.prevPoint = prevPoint;
        this.currentPoint = currentPoint;
    }
}
