package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.SplineTestUtil;
import com.thisismydesign.crshelper.iterator.SinglePointSplineIterator;
import com.thisismydesign.crshelper.screen.Frame;
import org.junit.*;

import static org.junit.Assert.*;

public class SplineTest {

    private Spline spline;

    @Before
    public void initialize() {
        Frame frame = new Frame(10, SplineTestUtil.width, SplineTestUtil.height);
        Vector2[] controlPoints = frame.getRandomPoints(SplineTestUtil.maxPointRangeInPercent);
        spline = new Spline(controlPoints, SplineTestUtil.precision);
    }

    @Test
    public void simpleLength_ShouldIgnoreFirstAndLastPoint() {
        Vector2[] points = new Vector2[] {new Vector2(0f, 0f), new Vector2(0f, 100f), new Vector2(0f, 200f),
                new Vector2(0f, 300f)};
        spline = new Spline(points, SplineTestUtil.precision);

        SinglePointSplineIterator iterator = new SinglePointSplineIterator(spline.path, SplineTestUtil.precision);
        float length = 0f;

        for(int i = 0; i < spline.path.spanCount; i++) {
            length += iterator.calculateSpanLength(i);
        }

        assertEquals(100f, length, SplineTestUtil.precision.getAllowedErrorInPixels());
    }

    @Test
    public void simpleLengthWith3Points_ShouldBeZero() {
        Vector2[] points = new Vector2[] {new Vector2(0f, 0f), new Vector2(0f, 100f), new Vector2(0f, 300f)};
        spline = new Spline(points, SplineTestUtil.precision);

        SinglePointSplineIterator iterator = new SinglePointSplineIterator(spline.path, SplineTestUtil.precision);
        float length = 0f;

        for(int i = 0; i < spline.path.spanCount; i++) {
            length += iterator.calculateSpanLength(i);
        }

        assertEquals(0f, length, SplineTestUtil.precision.getAllowedErrorInPixels());
    }

    @Test
    public void sumOfSpanLengthsViaIterator_ShouldEqualTo_PathLengthViaAPI() {
        SinglePointSplineIterator iterator = new SinglePointSplineIterator(spline.path, SplineTestUtil.precision);
        float length = 0f;

        int spanCount = spline.path.spanCount;

        for(int i = 0; i < spanCount; i++) {
            length += iterator.calculateSpanLength(i);
        }
        assertEquals(spline.path.approxLength(100), length, SplineTestUtil.precision.getAllowedErrorInPixels()*spanCount);
    }

    @Test
    @Ignore
    public void randomizedSpline_ShouldNotThrow_PointNotFoundException() {
        for(int j = 0; j < 10; j++) {
            for (int i = 3; i < 100; i += 10) {
                Frame frame = new Frame(i, SplineTestUtil.width, SplineTestUtil.height);
                Vector2[] points = frame.getRandomPoints(SplineTestUtil.maxPointRangeInPercent);
                try {
                    spline = new Spline(points, SplineTestUtil.precision);
                    spline.intersect(SplineTestUtil.diagonalMiddleLine);
                } catch (RuntimeException e) {
                    fail("RuntimeException thrown at " + i + " level: " + e.getMessage());
                }
            }
        }
    }

    @Test
    public void splineLengthMeasuredWithForLoop_ShouldEqualTo_splineLengthMeasuredWithIterator() {
        float forLoopLength = 0f;
        float iteratorLength = 0f;

        Vector2 lastPosition = spline.path.valueAt(new Vector2(), 0f);
        Vector2 currentPosition;

        for(int span = 0; span < spline.path.spanCount; span++) {

            float samples = 100f;
            for(int i=0; i<=samples; i++) {
                float t = i/samples;
                currentPosition = spline.path.valueAt(new Vector2(), span, t);
                forLoopLength += lastPosition.dst(currentPosition);
                lastPosition = currentPosition;
            }
        }

        SinglePointSplineIterator iterator = new SinglePointSplineIterator(spline.path, SplineTestUtil.precision);
        for(int i = 0; i < spline.path.spanCount; i++) {
            iteratorLength += iterator.calculateSpanLength(i);
        }

        assertEquals(forLoopLength, iteratorLength, SplineTestUtil.precision.getAllowedErrorInPixels());
    }

    @Test
    public void simpleSpline_ShouldBeIntersectable() {
        Vector2 intersection = spline.intersect(SplineTestUtil.diagonalMiddleLine);
        assertNotNull(intersection);
    }

    @Test
    public void diagonalLineAndSplineInTheMiddle_ShouldIntersectInTheMiddle() {
        Vector2 intersection = SplineTestUtil.splineInTheMiddleOfScreen.intersect(SplineTestUtil.verticalMiddleLine);
        assertEquals(SplineTestUtil.middlePointOfFrame.x, intersection.x, SplineTestUtil.precision.getAllowedErrorInPixels());
        assertEquals(SplineTestUtil.middlePointOfFrame.y, intersection.y, SplineTestUtil.precision.getAllowedErrorInPixels());
    }
}
