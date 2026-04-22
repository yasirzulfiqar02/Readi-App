package com.readi.apps.helper

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class SinglePageRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun fling(velocityX: Int, velocityY: Int): Boolean {

        val maxVelocity = 1000

        val newVelocityX = when {
            velocityX > maxVelocity -> maxVelocity
            velocityX < -maxVelocity -> -maxVelocity
            else -> velocityX
        }

        return super.fling(newVelocityX, velocityY)
    }
}
