package com.emami.openweather.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class VerticalSwipeToRefreshLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {
    private var startGestureX: Float = 0f
    private var startGestureY: Float = 0f


    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startGestureX = event.x
                startGestureY = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(event.x - startGestureX) > Math.abs(event.y - startGestureY)) {
                    return false
                }
            }
        }

        return super.onInterceptTouchEvent(event)
    }
}
