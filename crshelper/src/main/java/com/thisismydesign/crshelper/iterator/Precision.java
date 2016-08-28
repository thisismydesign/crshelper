package com.thisismydesign.crshelper.iterator;

public class Precision {

    /**
     * The lower the slower but the less likely for PositionAtRatioNotFoundException to occur
     * TODO take as argument in cnstr
     */
    private static final float precisionOverLength = 6f;

    // The higher the more inaccurate but less likely for PositionAtRatioNotFoundException to occur
    public static final float allowedErrorInPixels = 2f;

    // With current implementation 1 and 2 for the above works fine, but allowedErrorInPixels should be <= 1 and there are still performance issues
    // For rendering precisionOverLength = 3f is good enough

    public static float calculate(float length) {
        return precisionOverLength / length;
    }
}
