package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.math.Vector2;

import com.thisismydesign.crshelper.dto.SplinePoint;
import com.thisismydesign.crshelper.iterator.Precision;
import com.thisismydesign.crshelper.shape.Intersectable;

import java.util.ArrayList;
import java.util.List;

public class XFlatSpline extends Spline {

    public XFlatSpline(Vector2[] controlPoints, Precision precisionHelper) {
        super(controlPoints, precisionHelper);
    }

    public List<Vector2> intersect(Intersectable intersectable) {
        updateCalculatedData();
        return calculateIntersection(intersectable);
    }

    private List<Vector2> calculateIntersection(Intersectable intersectable) {
        List<Vector2> intersections = new ArrayList<>();
        SplinePoint intersect;
        Vector2 linePos;
        float linePrecision = precisionHelper.calculate(intersectable.getLength());
        float splinePrecision = precisionHelper.calculate(length);

        for (float t = 0f; t <= 1f; t += linePrecision) {
            linePos = intersectable.valueAt(t);

            if (isInXRange(linePos)) {
                int span = getContainingSpan(linePos, 0, path.spanCount - 1);
                intersect = getPointOnSpline(linePos, span, 0f, 1f, splinePrecision);
                if (intersect != null) {
                    intersections.add(intersect.point);
                }
            }
        }

        return intersections;
    }

    private boolean isInXRange(Vector2 point) {
        return beginPoint.x <= point.x && point.x <= endPoint.x;
    }

    public int getContainingSpan(Vector2 point, int begin, int end) {
        int middle = (int) Math.floor(begin + (end - begin / 2));

        Vector2 firstPointOfMiddleSpan = path.valueAt(new Vector2(), middle, 0f);
        Vector2 lastPointOfMiddleSpan = path.valueAt(new Vector2(), middle, 1f);

        if (point.x >= firstPointOfMiddleSpan.x && point.x <= lastPointOfMiddleSpan.x)
            return middle;
        else if (point.x < lastPointOfMiddleSpan.x)
            return getContainingSpan(point, begin, middle - 1);
        else
            return getContainingSpan(point, middle + 1, end);
    }

//     Recursive function that uses binary search on x coordinates to narrow possible points of spline that can match the given point
    private SplinePoint getPointOnSpline(Vector2 point, int span, float begin, float end, float precision) {
        float middle = begin + (end - begin) / 2;
        Vector2 middlePoint = path.valueAt(new Vector2(), span, middle);

        if (Math.abs(point.x - middlePoint.x) <= precision)
            return getPointOfSplineIfInRange(point, middlePoint, span, middle);
        else if (point.x < middlePoint.x)
            return getPointOnSpline(point, span, begin, middle, precision);
        else
            return getPointOnSpline(point, span, middle, end, precision);
    }
}
