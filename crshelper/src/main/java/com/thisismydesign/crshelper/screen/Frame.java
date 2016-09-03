package com.thisismydesign.crshelper.screen;

import com.badlogic.gdx.math.Vector2;

public class Frame {
    private WindowManager windowManager;

    private int numberOfPoints;

    private final int width;
    private final int height;

    private final Vector2 startPoint;

    public Frame(int numberOfPoints, int width, int height, Vector2 startPoint) {
        this.windowManager = new WindowManager(width, height, numberOfPoints - 2, startPoint);
        this.startPoint = startPoint;
        this.width = width;
        this.height = height;
        this.numberOfPoints = numberOfPoints;
    }

    public Frame(int numberOfPoints, int width, int height) {
        this(numberOfPoints, width, height, new Vector2(0f, 0f));
    }

    public Vector2[] getRandomPoints(int maxPointRangeInPercent) {

        Vector2[] points = new Vector2[numberOfPoints];

        // FIX POINTS
        // First point is in the left middle
        points[0] = new Vector2(startPoint.x, startPoint.y + height / 2);
        // Last point is in the right middle
        points[numberOfPoints - 1] = new Vector2(startPoint.x + width, startPoint.y + height / 2);

        // Would be 0, but first point is fixed so the first window is skipped
        int i = 1;
        // Middle points are random
        for (Window w : windowManager.getWindows()) {
            points[i] = w.getRandomPoint(maxPointRangeInPercent);
            i++;
            if (i >= numberOfPoints - 1) break;
        }

        return points;
    }

    private int getMaxPointRangeInPercent(int numberOfPoints) {
        if (numberOfPoints <= 5)
            return 0;
        else
            return (numberOfPoints - 5) * 2;
    }

}
