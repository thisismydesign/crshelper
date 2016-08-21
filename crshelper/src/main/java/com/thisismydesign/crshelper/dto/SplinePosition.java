package com.thisismydesign.crshelper.dto;

public class SplinePosition implements Comparable<SplinePosition> {
    public int span;
    public float t;

    public SplinePosition (int span, float t) {
        this.span = span;
        this.t = t;
    }

    public SplinePosition (SplinePosition s) {
        this.span = s.span;
        this.t = s.t;
    }

    public static SplinePosition min(SplinePosition s1, SplinePosition s2) {
        return (s1.compareTo(s2) <= 0) ? s1 : s2;
    }

    public static SplinePosition max(SplinePosition s1, SplinePosition s2) {
        return (s1.compareTo(s2) >= 0) ? s1 : s2;
    }

    @Override
    public int compareTo(SplinePosition s) {
        return (compareSpan(s) == 0) ? compareT(s) : compareSpan(s);
    }

    private int compareSpan (SplinePosition s) {
        return this.span - s.span;
    }

    private int compareT (SplinePosition s) {
        if (this.t < s.t)
            return -1;
        else if (this.t > s.t)
            return 1;
        else
            return 0;
    }
}
