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

    public Span(Vector2[] controlPoints) {
        if (controlPoints.length != numberOfPoints) throw new IllegalArgumentException(String.format(
                "A span must contain %s controlPoints, instead %s is given", numberOfPoints, controlPoints.length));
        this.controlPoints = controlPoints;
        equation = new Cubic();
        spline = new CatmullRomSpline<>(this.controlPoints, false);
    }

    public List<Vector2> intersect(Line line) {
        List<Vector2> intersections = new ArrayList<>();

        double c0 = line.getA()*getA0(controlPoints) + line.getB()*getB0(controlPoints) + line.getC();
        double c1 = line.getA()*getA1(controlPoints) + line.getB()*getB1(controlPoints);
        double c2 = line.getA()*getA2(controlPoints) + line.getB()*getB2(controlPoints);
        double c3 = line.getA()*getA3(controlPoints) + line.getB()*getB3(controlPoints);

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

    private double getA0(Vector2[] controlPoints) {
        return 0.5*2*controlPoints[1].x;
    }
    private double getB0(Vector2[] controlPoints) {
        return 0.5*2*controlPoints[1].y;
    }

    private double getA1(Vector2[] controlPoints) {
        return 0.5*(-1*controlPoints[0].x + controlPoints[2].x);
    }
    private double getB1(Vector2[] controlPoints) {
        return 0.5*(-1*controlPoints[0].y + controlPoints[2].y);
    }

    private double getA2(Vector2[] controlPoints) {
        return 0.5*(2*controlPoints[0].x - 5*controlPoints[1].x + 4*controlPoints[2].x - controlPoints[3].x);
    }
    private double getB2(Vector2[] controlPoints) {
        return 0.5*(2*controlPoints[0].y - 5*controlPoints[1].y + 4*controlPoints[2].y - controlPoints[3].y);
    }

    private double getA3(Vector2[] controlPoints) {
        return 0.5*(-1*controlPoints[0].x + 3*controlPoints[1].x - 3*controlPoints[2].x + controlPoints[3].x);
    }
    private double getB3(Vector2[] controlPoints) {
        return 0.5*(-1*controlPoints[0].y + 3*controlPoints[1].y - 3*controlPoints[2].y + controlPoints[3].y);
    }
}
