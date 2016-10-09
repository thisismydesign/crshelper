package com.thisismydesign.crshelper.iterator;

public class Precision {

    private final float precisionOverLength;

    private final float allowedErrorInPixels;

    /**
     * @param precisionOverLength number of pixels per point (e.g. 10 => 1 point drawn every 10 pixels)
     */
    public Precision(float precisionOverLength) {
        this.precisionOverLength = precisionOverLength;
        int allowedErrorPercentageByPrecision = 20;
        this.allowedErrorInPixels = (allowedErrorPercentageByPrecision/100f) * precisionOverLength;
    }

    public float getAllowedErrorInPixels() {
        return allowedErrorInPixels;
    }

    public float calculate(float length) {
        return precisionOverLength / length;
    }
}
