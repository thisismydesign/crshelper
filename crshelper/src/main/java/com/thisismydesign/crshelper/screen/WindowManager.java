package com.thisismydesign.crshelper.screen;

import com.badlogic.gdx.math.Vector2;

public final class WindowManager {

    private Window[] windows;
    private int windowNum;
    private int width;
    private int height;

    public WindowManager(int width, int height, int windowNum, Vector2 startPoint) {
        this.width = width;
        this.height = height;
        this.windowNum = windowNum;
        this.windows = new Window[windowNum];

        Vector2 lowerRight, upperLeft, upperRight;

        for (int i = 0; i < windowNum; i++) {
            lowerRight = new Vector2(startPoint.x + width / windowNum, startPoint.y);
            upperLeft = new Vector2(startPoint.x, startPoint.y + height);
            upperRight = new Vector2(startPoint.x + width / windowNum, startPoint.y + height);

            windows[i] = new Window(startPoint, lowerRight, upperLeft, upperRight);

            startPoint = new Vector2(lowerRight);
        }
    }

    public final Window[] getWindows() {
        return windows;
    }
}
