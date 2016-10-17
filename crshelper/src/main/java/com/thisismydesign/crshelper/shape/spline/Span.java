package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.shape.Line;
import edu.rit.numeric.Cubic;

import java.util.ArrayList;
import java.util.List;

public class Span {
    private Vector2[] controlPoints;
    private final static int numberOfPoints = 4;
    private Cubic equation;
    public CatmullRomSpline<Vector2> spline;
    private final static double alpha = 0.5;

    public Span(Vector2[] controlPoints) {
        if (controlPoints.length != numberOfPoints) throw new IllegalArgumentException(String.format(
                "A span must contain %s controlPoints, instead %s is given", numberOfPoints, controlPoints.length));
        this.controlPoints = controlPoints;
        equation = new Cubic();
        spline = new CatmullRomSpline<>(this.controlPoints, false);
    }

    public List<Vector2> intersect(Line line) {
        List<Vector2> intersections = new ArrayList<>();

        double c3 = line.getA()*getA1(controlPoints) + line.getB()*getA2(controlPoints);
        double c2 = line.getA()*getB1(controlPoints) + line.getB()*getB2(controlPoints);
        double c1 = line.getA()*getC1(controlPoints) + line.getB()*getC2(controlPoints);
        double c0 = line.getA()*getD1(controlPoints) + line.getB()*getD2(controlPoints) + line.getC();

        equation.solve(c3, c2, c1, c0);

        if(equation.x1 >=0 && equation.x1 <= 1) {
            intersections.add(spline.valueAt(new Vector2(), (float)equation.x1));
        }
        if (equation.x2 >=0 && equation.x2 <= 1) {
            intersections.add(spline.valueAt(new Vector2(), (float)equation.x2));
        }
        if (equation.x3 >=0 && equation.x3 <= 1) {
            intersections.add(spline.valueAt(new Vector2(), (float)equation.x3));
        }

        return intersections;
    }

    private double getA1(Vector2[] controlPoints) {
        return alpha*(-1*controlPoints[0].x + 3*controlPoints[1].x - 3*controlPoints[2].x + controlPoints[3].x);
    }
    private double getA2(Vector2[] controlPoints) {
        return alpha*(-1*controlPoints[0].y + 3*controlPoints[1].y - 3*controlPoints[2].y + controlPoints[3].y);
    }

    private double getB1(Vector2[] controlPoints) {
        return alpha*(2*controlPoints[0].x - 5*controlPoints[1].x + 4*controlPoints[2].x - controlPoints[3].x);
    }
    private double getB2(Vector2[] controlPoints) {
        return alpha*(2*controlPoints[0].y - 5*controlPoints[1].y + 4*controlPoints[2].y - controlPoints[3].y);
    }

    private double getC1(Vector2[] controlPoints) {
        return alpha*(-1*controlPoints[0].x + controlPoints[2].x);
    }
    private double getC2(Vector2[] controlPoints) {
        return alpha*(-1*controlPoints[0].y + controlPoints[2].y);
    }

    private double getD1(Vector2[] controlPoints) {
        return alpha*2*controlPoints[1].x;
    }
    private double getD2(Vector2[] controlPoints) {
        return alpha*2*controlPoints[1].y;
    }
}
