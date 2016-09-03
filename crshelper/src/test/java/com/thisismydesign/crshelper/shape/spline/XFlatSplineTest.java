package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.screen.Frame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XFlatSplineTest {

    private CatmullRomSpline<Vector2> spline;

    private final int maxPointRangeInPercent = 50;

    @Before
    public void initialize() {
        // TODO error with 3 points
        Frame frame = new Frame(5, 500, 500);
        Vector2[] controlPoints = frame.getRandomPoints(maxPointRangeInPercent);
        spline = new Spline(Color.RED, controlPoints).path;
    }

    @Test
    public void FirstPointOfSpline_ShouldBeInFirstSpan() {
        Assert.assertEquals(0, XFlatSpline.getContainingSpan(spline, spline.valueAt(new Vector2(), 0f), 0, spline.spanCount - 1));
    }

    @Test
    public void LastPointOfSpline_ShouldBeInLastSpan() {
        Assert.assertEquals(spline.spanCount - 1, XFlatSpline.getContainingSpan(spline, spline.valueAt(new Vector2(), 1f), 0, spline.spanCount-1));
    }

}