package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.iterator.Precision;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class XFlatSplineValidatorTest {

    private final static int width = 500;
    private final static int height = 500;

    private XFlatSpline spline;
    private Precision precision;

    @Before
    public void initialize() {
        precision = new Precision(3f);
    }

    @Test
    public void simpleLineAlongXAxis_ShouldBeValid() {
        Vector2[] points = new Vector2[] {new Vector2(0f, 0f), new Vector2(0f, 100f), new Vector2(0f, 200f),
                new Vector2(0f, 300f)};
        spline = new XFlatSpline(points, precision);

        XFlatSplineValidator validator = new XFlatSplineValidator(width, height, spline);
        assertTrue(validator.isValid());
    }

    @Test
    public void SplineLookingLikeMirroredC_ShouldNotBeValid() {
        Vector2[] points = new Vector2[] {new Vector2(0f, 0f), new Vector2(0f, 0f),
                new Vector2(100f, 100f),
                new Vector2(0f, 300f), new Vector2(0f, 300f)};
        spline = new XFlatSpline(points, precision);

        XFlatSplineValidator validator = new XFlatSplineValidator(width, height, spline);
        assertFalse(validator.isValid());
    }

}