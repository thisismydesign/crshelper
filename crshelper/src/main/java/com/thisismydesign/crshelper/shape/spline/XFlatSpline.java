package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

import com.thisismydesign.crshelper.dto.SplinePoint;

// TODO extends spline
public class XFlatSpline {

    private XFlatSpline() {}

    public static int getContainingSpan(CatmullRomSpline<Vector2> spline, Vector2 point, int begin, int end) {
        int middle = (int) Math.floor(begin + (end - begin / 2));

        Vector2 firstPointOfMiddleSpan = spline.valueAt(new Vector2(), middle, 0f);
        Vector2 lastPointOfMiddleSpan = spline.valueAt(new Vector2(), middle, 1f);

        if (point.x >= firstPointOfMiddleSpan.x && point.x <= lastPointOfMiddleSpan.x)
            return middle;
        else if (point.x < lastPointOfMiddleSpan.x)
            return getContainingSpan(spline, point, begin, middle - 1);
        else
            return getContainingSpan(spline, point, middle + 1, end);
    }

    // Recursive function that uses binary search on x coordinates to narrow possible points of spline that can match the given point
    public static SplinePoint getPointOnSpline(CatmullRomSpline<Vector2> spline, Vector2 point, int span, float begin, float end, float precision) {
        float middle = begin + (end - begin) / 2;
        Vector2 middlePoint = spline.valueAt(new Vector2(), span, middle);

        if (Math.abs(point.x - middlePoint.x) <= precision)
            return Spline.getPointOfSplineIfInRange(point, middlePoint, span, middle);
        else if (point.x < middlePoint.x)
            return getPointOnSpline(spline, point, span, begin, middle, precision);
        else
            return getPointOnSpline(spline, point, span, middle, end, precision);
    }
}
