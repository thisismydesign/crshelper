package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.SplineTestUtil;
import com.thisismydesign.crshelper.screen.Frame;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class XFlatSplineTest {

    private XFlatSpline spline;

    private final int maxPointRangeInPercent = 50;

    @Before
    public void initialize() {
        // TODO error with 3 points
        Frame frame = new Frame(5, 500, 500);
        Vector2[] controlPoints = frame.getRandomPoints(maxPointRangeInPercent);
        spline = new XFlatSpline(controlPoints, SplineTestUtil.precision);
    }

    @Test
    public void FirstPointOfSpline_ShouldBeInFirstSpan() {
        assertEquals(0, spline.getContainingSpan(spline.path.valueAt(new Vector2(), 0f), 0, spline.path.spanCount - 1));
    }

    @Test
    public void LastPointOfSpline_ShouldBeInLastSpan() {
        assertEquals(spline.path.spanCount - 1, spline.getContainingSpan(spline.path.valueAt(new Vector2(), 1f), 0, spline.path.spanCount - 1));
    }

    @Test
    public void splineInTheMiddleOfScreen_ShouldBeIntersectedByVerticalLine() {
        assertNotEquals(SplineTestUtil.splineInTheMiddleOfScreen.intersect(SplineTestUtil.verticalMiddleLine).size(), 0);
    }

    @Test
    public void diagonalLine_ShouldCrossLevelOneSpline_InTheMiddleOfScreen() {
        List<Vector2> intersections = SplineTestUtil.splineInTheMiddleOfScreen.intersect(SplineTestUtil.diagonalMiddleLine);
        System.out.println(intersections);
        assertNotEquals(0, intersections.size());
        assertTrue(SplineTestUtil.middlePointOfFrame.dst(intersections.get(0)) < SplineTestUtil.precision.getAllowedErrorInPixels());
    }

    @Test
    public void halfOfLevelOneSpline_ShouldBeInTheMiddleOfScreen() {
        List<Vector2> intersections = SplineTestUtil.splineInTheMiddleOfScreen.intersect(SplineTestUtil.verticalMiddleLine);
        assertNotEquals(0, intersections.size());
        assertTrue(SplineTestUtil.middlePointOfFrame.dst(intersections.get(0)) < SplineTestUtil.precision.getAllowedErrorInPixels());
    }

}