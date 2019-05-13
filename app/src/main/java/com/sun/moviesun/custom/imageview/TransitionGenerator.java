package com.sun.moviesun.custom.imageview;

import android.graphics.RectF;

public interface TransitionGenerator {
  Transition generateNextTransition(RectF drawableBounds, RectF viewport);
}
