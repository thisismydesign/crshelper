package com.thisismydesign.crshelper.screen;

import com.badlogic.gdx.math.Vector2;

public class Frame {
    private WindowManager windowManager;

    private int numberOfPoints;
    private int maxPointRangeInPercent;

    private final int width;
    private final int height;

    public Frame(int numberOfPoints, int width, int height) {
        windowManager = new WindowManager(width, height, numberOfPoints - 2, new Vector2(0f, 0f));
        this.width = width;
        this.height = height;
        this.numberOfPoints = numberOfPoints;
        this.maxPointRangeInPercent = getMaxPointRangeInPercent(numberOfPoints);
    }

    public Vector2[] getRandomPoints() {

        Vector2[] points = new Vector2[numberOfPoints];

        // FIX POINTS
        // First point is in the left middle
        points[0] = new Vector2(0, height / 2);
        // Last point is in the right middle
        points[numberOfPoints - 1] = new Vector2(width, height / 2);

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
