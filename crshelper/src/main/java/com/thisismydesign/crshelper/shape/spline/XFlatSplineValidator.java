package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.dto.SplinePointPair;
import com.thisismydesign.crshelper.iterator.DoublePointSplineIterator;

public class XFlatSplineValidator {

    private final int screenWidth;
    private final int screenHeight;
    // TODO this should be XFlatSpline
    private final Spline spline;

    public XFlatSplineValidator(int screenWidth, int screenHeight, Spline spline) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.spline = spline;
    }

    public boolean isValid() {
        return exists() && isPlayable();
    }

    private boolean isPlayable() {
        CatmullRomSpline<Vector2> temp = new CatmullRomSpline<Vector2>(spline.controlPoints, false);
        DoublePointSplineIterator iterator = new DoublePointSplineIterator(temp);
        SplinePointPair splinePointPair;

        while ((splinePointPair = iterator.getNext()) != null) {
            if (!isVisible(splinePointPair.currentPoint.point)
                    || !isContinuousOnX(splinePointPair.prevPoint.point, splinePointPair.currentPoint.point))
                return false;
        }

        return true;
    }

    private boolean exists() {
        return spline.path != null;
    }

    private boolean isContinuousOnX(Vector2 lastPosition, Vector2 currentPosition) {
        return lastPosition.x <= currentPosition.x;
    }

    private boolean isVisible(Vector2 position) {
        if (position.x < 0 || position.x > screenWidth ||  position.y < 0 || position.y > screenHeight)
            return false;
        else
            return true;
    }
}
