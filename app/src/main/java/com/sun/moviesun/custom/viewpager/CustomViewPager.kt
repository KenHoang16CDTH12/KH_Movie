package com.sun.moviesun.custom.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

  private var enabled = false

  override fun onTouchEvent(event: MotionEvent): Boolean = this.enabled && super.onTouchEvent(event) && performClick()

  override fun onInterceptTouchEvent(event: MotionEvent): Boolean = this.enabled && super.onInterceptTouchEvent(event)

  override fun performClick(): Boolean = this.enabled && super.performClick()

  override fun setEnabled(enabled: Boolean) { this.enabled = enabled }
}