package com.onegold.glbutton.ui.shape

import android.graphics.*
import android.graphics.drawable.shapes.Shape
import com.onegold.glbutton.utils.GlButtonUtils

/**
 * GlCutShape
 */
class GlCutRectShape(
    private val leftTopCorner: Float,
    private val leftBottomCorner: Float,
    private val rightTopCorner: Float,
    private val rightBottomCorner: Float,
) : Shape() {
    // Shape size
    private val mRect = RectF()

    override fun draw(canvas: Canvas?, paint: Paint?) {
        val path = getShapePath()

        if (canvas != null && paint != null) {
            canvas.drawPath(path, paint)
        }
    }

    private fun getShapePath(): Path = GlButtonUtils.getCutRectPath(
        mRect,
        leftTopCorner,
        leftBottomCorner,
        rightTopCorner,
        rightBottomCorner
    )


    override fun getOutline(outline: Outline) {
        outline.setRect(0, 0, 0, 0)
    }

    override fun onResize(width: Float, height: Float) {
        mRect.set(0f, 0f, width, height)
    }
}