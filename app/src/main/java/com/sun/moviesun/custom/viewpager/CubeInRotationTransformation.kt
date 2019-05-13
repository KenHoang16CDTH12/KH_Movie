package com.sun.moviesun.custom.viewpager

import android.view.View
import androidx.annotation.NonNull
import androidx.viewpager.widget.ViewPager

class CubeInRotationTransformation : ViewPager.PageTransformer {

  override fun transformPage(@NonNull page: View, position: Float) {
    page.cameraDistance = CAMERA_DISTANCE
    when {
      position < -ONE -> page.alpha = ZERO
      position <= ZERO -> {
        page.alpha = ONE
        page.pivotX = page.width.toFloat()
        page.rotationY = NINETY * Math.abs(position)
      }
      position <= ONE -> {
        page.alpha = ONE
        page.pivotX = ZERO
        page.rotationY = -NINETY * Math.abs(position)
      }
      else -> page.alpha = ZERO
    }
  }

  companion object {
    private const val CAMERA_DISTANCE = 20000F
    private const val ZERO = 0F
    private const val ONE = 1F
    private const val NINETY = 90
  }
}