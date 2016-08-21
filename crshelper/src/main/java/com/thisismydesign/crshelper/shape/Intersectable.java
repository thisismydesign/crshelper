package com.thisismydesign.crshelper.shape;

import com.badlogic.gdx.math.Vector2;

public interface Intersectable {

    public Vector2 valueAt(float t);

    public float getLength();

}
