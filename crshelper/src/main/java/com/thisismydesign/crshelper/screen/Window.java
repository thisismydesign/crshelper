package com.thisismydesign.crshelper.screen;

import com.badlogic.gdx.math.Vector2;

/**
 * TODO implement padding (on window), spacing (on point)
 */
public final class Window {

    private float width;
    private float height;
    private Vector2 lowerLeft;
    private Vector2 lowerRight;
    private Vector2 upperLeft;
    private Vector2 upperRight;
    private Vector2 middle;

    public Window(Vector2 lowerLeft, Vector2 lowerRight, Vector2 upperLeft, Vector2 upperRight) {
        this.lowerLeft = lowerLeft;
        this.lowerRight = lowerRight;
        this.upperLeft = upperLeft;
        this.upperRight = upperRight;
        this.middle = new Vector2(lowerLeft.x + (lowerRight.x - lowerLeft.x) / 2, lowerLeft.y + (upperLeft.y - lowerLeft.y) / 2);

        this.width = lowerRight.x - lowerLeft.x;
        this.height = upperLeft.y - lowerLeft.y;
    }

    public final Vector2 getRandomPoint(int maxRangeInPercent) {
        float maxXRange = ((lowerRight.x - lowerLeft.x) / 2) / 100 * maxRangeInPercent;
        float maxYRange = ((upperLeft.y - lowerLeft.y) / 2) / 100 * maxRangeInPercent;

        float x = middle.x + (float) (Math.random() * (maxXRange * 2) - maxXRange);
        float y = middle.y + (float) (Math.random() * (maxYRange * 2) - maxYRange);
        return new Vector2(x, y);
    }

    public final Vector2 getMiddlePoint() {
        return new Vector2((lowerRight.x - lowerLeft.x) / 2 + lowerLeft.x, (upperLeft.y - lowerLeft.y) / 2 + lowerLeft.y);
    }

    public final float getWidth() {
        return width;
    }
}
