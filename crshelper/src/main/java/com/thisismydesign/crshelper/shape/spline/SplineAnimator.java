package com.thisismydesign.crshelper.shape.spline;

import com.badlogic.gdx.math.Vector2;

public class SplineAnimator {
    private Spline spline;
    private Vector2[] newControlPoints;
    private Vector2[] currentControlPoints;

    private float controlPointDiffs[];

    private enum RenderState {ANIMATE, ANIMATION_OVER, FINAL}
    protected RenderState state;
    private float animationTime;

    public SplineAnimator(Spline spline, Vector2[] newControlPoints) {
        this.spline = spline;
        this.currentControlPoints =  spline.getControlPoints();
        this.newControlPoints = newControlPoints;

        int numberOfPoints = this.currentControlPoints.length;
        int numberOfNewPoints = this.newControlPoints.length;

        if (numberOfNewPoints > numberOfPoints) {
            if (numberOfNewPoints > numberOfPoints + 1) {
                throw new IllegalArgumentException(String.format("Too much control points (original: %s, new: %s",
                        numberOfPoints, numberOfNewPoints));
            } else {
                spline.setControlPoints(duplicateLastControlPoint(currentControlPoints));
            }
        }

        this.controlPointDiffs = getControlPointDiffs(spline.getControlPoints(), newControlPoints);
        this.animationTime = 0f;
        state = RenderState.ANIMATE;
    }

    public void animate(float timeDelta) {
        if (state == RenderState.ANIMATE) {
            for (int i = 1; i < newControlPoints.length - 1; ++i) {
                Vector2 direction = (newControlPoints[i].cpy().sub(currentControlPoints[i])).nor();
                currentControlPoints[i] = currentControlPoints[i].add(direction.scl(controlPointDiffs[i] * timeDelta));
            }
            spline.setControlPoints(currentControlPoints);
            animationTime += timeDelta;
            if (isAnimationFinished(timeDelta))
                state = RenderState.ANIMATION_OVER;

        } else if(state == RenderState.ANIMATION_OVER) {
            spline.setControlPoints(newControlPoints);
            state = RenderState.FINAL;
        }
    }

    private float[] getControlPointDiffs(Vector2[] controlPoints, Vector2[] destControlPoints) {
        float[] diffs = new float[controlPoints.length];

        for(int i = 0; i < diffs.length; i++) {
            diffs[i] = destControlPoints[i].dst(controlPoints[i]);
        }

        return diffs;
    }

    private boolean isAnimationFinished(float lastTimeDelta) {
        return animationTime >= 1f - lastTimeDelta;
    }

    private Vector2[] duplicateLastControlPoint(Vector2[] currentControlPoints) {
        int oldLength = currentControlPoints.length;
        int newLength = oldLength + 1;
        Vector2[] increasedCurrentControlPoints = new Vector2[newLength];

        System.arraycopy(currentControlPoints, 0, increasedCurrentControlPoints, 0, currentControlPoints.length);
        increasedCurrentControlPoints[newLength - 1] = currentControlPoints[oldLength - 1];

        return increasedCurrentControlPoints;
    }
}
