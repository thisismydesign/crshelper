package com.thisismydesign.crshelper.dto;

import org.junit.Assert;
import org.junit.Test;

public class SplinePositionTest {

    private static final SplinePosition reference = new SplinePosition(1, 0.5f);

    @Test
    public void positionWithHigherSpan_ShouldCompareMinusOneToReference() {
        SplinePosition s = new SplinePosition(2, 0f);
        Assert.assertEquals(-1, reference.compareTo(s));
    }

    @Test
    public void positionWithHigherSpanAndHigherT_ShouldCompareMinusOneToReference() {
        SplinePosition s = new SplinePosition(2, 1f);
        Assert.assertEquals(-1, reference.compareTo(s));
    }

    @Test
    public void positionWithSmallerSpan_ShouldComparePlusOneToReference() {
        SplinePosition s = new SplinePosition(0, 0f);
        Assert.assertEquals(1, reference.compareTo(s));
    }

    @Test
    public void positionWithSmallerSpanAndHigherT_ShouldComparePlusOneToReference() {
        SplinePosition s = new SplinePosition(0, 1f);
        Assert.assertEquals(1, reference.compareTo(s));
    }

    @Test
    public void positionWithEqualSpanAndSmallerT_ShouldComparePlusOneToReference() {
        SplinePosition s = new SplinePosition(1, 0f);
        Assert.assertEquals(1, reference.compareTo(s));
    }

    @Test
    public void positionWithEqualSpanAndHigherT_ShouldCompareMinusOneToReference() {
        SplinePosition s = new SplinePosition(1, 1f);
        Assert.assertEquals(-1, reference.compareTo(s));
    }

    @Test
    public void positionWithEqualSpanAndT_ShouldCompareZeroToReference() {
        SplinePosition s = new SplinePosition(1, 0.5f);
        Assert.assertEquals(0, reference.compareTo(s));
    }

}