package com.thisismydesign.crshelper;

import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.iterator.Precision;
import com.thisismydesign.crshelper.shape.Line;
import com.thisismydesign.crshelper.shape.spline.XFlatSpline;

public class SplineTestUtil {
    public final static int width = 500;
    public final static int height = 500;

    public final static Line diagonalMiddleLine = new Line(new Vector2(0f,0f), new Vector2(width, height));
    public final static Line verticalMiddleLine = new Line(new Vector2(width /2, height /2), new Vector2(width /2, height /2));
    public final static Vector2 middlePointOfFrame = new Vector2(width /2, height /2);

    public final static int maxPointRangeInPercent = 50;

    public final static Precision precision = new Precision(3f);

    private final static Vector2[] points = new Vector2[] {new Vector2(0f, height/2),
            new Vector2(width/4, height/2), new Vector2(width/4*3, height/2),
            new Vector2(width, height/2)};
    public final static XFlatSpline splineInTheMiddleOfScreen = new XFlatSpline(points, SplineTestUtil.precision);
}
