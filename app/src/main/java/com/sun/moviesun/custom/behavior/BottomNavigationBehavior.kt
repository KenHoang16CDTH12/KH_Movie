package com.sun.moviesun.custom.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

  override fun onStartNestedScroll(@NonNull coordinatorLayout: CoordinatorLayout, @NonNull child: BottomNavigationView, @NonNull directTargetChild: View, @NonNull target: View, axes: Int, type: Int): Boolean =
      axes == ViewCompat.SCROLL_AXIS_VERTICAL

  override fun onNestedPreScroll(@NonNull coordinatorLayout: CoordinatorLayout, @NonNull child: BottomNavigationView, @NonNull target: View, dx: Int, dy: Int, @NonNull consumed: IntArray, type: Int) {
    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    child.translationY = Math.max(0.0f, Math.min(child.height.toFloat(), child.translationY + dy))
  }
}
