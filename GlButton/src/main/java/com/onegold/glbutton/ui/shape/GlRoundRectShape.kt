package com.onegold.glbutton.ui.shape

import android.graphics.Outline
import android.graphics.drawable.shapes.RoundRectShape

/**
 * GlRoundShape
 */
class GlRoundRectShape(
    corner: Float
) : RoundRectShape(FloatArray(8) { corner }, null, null) {

    override fun getOutline(outline: Outline) {
        outline.setRect(0, 0, 0, 0)
    }
}