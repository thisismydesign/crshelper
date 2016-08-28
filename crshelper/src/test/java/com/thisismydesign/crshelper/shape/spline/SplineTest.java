package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.iterator.Precision;
import com.thisismydesign.crshelper.iterator.SinglePointSplineIterator;
import com.thisismydesign.crshelper.screen.Frame;
import com.thisismydesign.crshelper.shape.Line;
import org.junit.*;

public class SplineTest {

    private static Line diagonalMiddleLine;
    private static Line verticalMiddleLine;
    private static Vector2 middlePointOfFrame;

    private Spline spline;
    private float allowedErrorInPixels;

    private final float delta = 0.01f;

    private final static int width = 500;
    private final static int height = 500;

    @BeforeClass
    public static void init() {

        middlePointOfFrame = new Vector2(width /2, height /2);
        diagonalMiddleLine = new Line(Color.RED, new Vector2(0f,0f), new Vector2(width, height));
        verticalMiddleLine = new Line(Color.RED, new Vector2(width /2, height /2), new Vector2(width /2, height /2));
    }

    @Before
    public void initialize() {
        Frame frame = new Frame(5, width, height);
        Vector2[] controlPoints = frame.getRandomPoints();
        spline = new Spline(Color.RED, controlPoints);
        allowedErrorInPixels = Precision.allowedErrorInPixels;// * spline.controlPoints.length;
    }

    @Test
    public void levelOneSpline_ShouldBeValid() {
        XFlatSplineValidator validator = new XFlatSplineValidator(width, height, spline);
        Assert.assertTrue(validator.isValid());
    }

    @Test
    public void randomizedSpline_ShouldNotThrow_PointNotFoundException() {
        for(int j = 0; j < 10; j++) {
            for (int i = 3; i < 100; i += 10) {
                Frame frame = new Frame(i, width, height);
                Vector2[] points = frame.getRandomPoints();
                try {
                    spline = new Spline(Color.RED, points);
                    spline.intersect(diagonalMiddleLine);
                } catch (RuntimeException e) {
                    Assert.fail("RuntimeException thrown at " + i + " level: " + e.getMessage());
                }
            }
        }
    }


    @Test
    @Ignore
    public void sumOfSpanLengths_ShouldEqualTo_PathLength() {
        SinglePointSplineIterator iterator = new SinglePointSplineIterator(spline.path);
        float length = 0f;

        for(int i = 0; i < spline.path.spanCount; i++) {
            length += iterator.calculateSpanLength(i);
        }
        Assert.assertEquals(spline.path.approxLength(spline.path.spanCount*10), length, delta);
    }

    @Ignore
    @Test
    // This should not fail... :/
    public void splineLengthMeasuredWithForLoop_ShouldEqualTo_splineLengthMeasuredWithIterator() {
        float forLoopLength = 0f;
        float iteratorLength = 0f;

        Vector2 lastPosition = spline.path.valueAt(new Vector2(), 0f);
        Vector2 currentPosition;
        for(int span = 0; span < spline.path.spanCount; span++) {
            for (float t = 0f; t <= 1f; t += 0.1) {
                currentPosition = spline.path.valueAt(new Vector2(), span, t);
                forLoopLength += lastPosition.dst(currentPosition);
                lastPosition = currentPosition;
            }
        }

        SinglePointSplineIterator iterator = new SinglePointSplineIterator(spline.path);
        for(int i = 0; i < spline.path.spanCount; i++) {
            iteratorLength += iterator.calculateSpanLength(i);
        }

        Assert.assertEquals(forLoopLength, iteratorLength, delta);
    }

    @Test
    public void levelOneSpline_ShouldBeInTheMiddleOfScreen() {
        Vector2 intersection = spline.intersect(verticalMiddleLine);
        Assert.assertNotEquals(intersection, null);
    }

    @Test
    public void diagonalLine_ShouldCrossLevelOneSpline_InTheMiddleOfScreen() {
        Vector2 intersection = spline.intersect(diagonalMiddleLine);
        Assert.assertTrue(middlePointOfFrame.dst(intersection) < allowedErrorInPixels);
    }

    @Test
    public void halfOfLevelOneSpline_ShouldBeInTheMiddleOfScreen() {
        Vector2 intersection = spline.intersect(verticalMiddleLine);
        Assert.assertTrue(middlePointOfFrame.dst(intersection) < allowedErrorInPixels);
    }
}
