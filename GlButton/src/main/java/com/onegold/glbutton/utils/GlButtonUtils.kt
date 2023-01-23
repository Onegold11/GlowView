package com.onegold.glbutton.utils

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import java.lang.Float.max
import kotlin.math.min

/**
 * GlButton utility
 */
object GlButtonUtils {
    /**
     * Create gradient drawable
     */
    fun createGradientDrawable(
        shape: Shape,
        gradient: Shader
    ) = ShapeDrawable().apply {
        setPadding(Rect())

        this.shape = shape

        paint.shader = gradient
    }

    /**
     * Create stroke drawable
     */
    fun createStrokeDrawable(
        shape: Shape,
        strokeColor: Int,
        strokeWidth: Float
    ) = ShapeDrawable().apply {
        setPadding(Rect())

        this.shape = shape

        paint.color = strokeColor
        paint.strokeWidth = strokeWidth
        paint.style = Paint.Style.STROKE
    }

    /**
     * Create glow drawable
     */
    fun createGlowDrawable(
        shape: Shape,
        glowColor: Int,
        size: Float
    ) = ShapeDrawable().apply {
        setPadding(Rect())

        this.shape = shape

        paint.setShadowLayer(size, 0f, 0f, glowColor)
        paint.color = Color.TRANSPARENT
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = size
    }

    /**
     * Create vertical linear gradient shape
     */
    fun createVerticalLinearGradient(
        size: Float,
        startColor: Int,
        endColor: Int
    ): LinearGradient {
        return LinearGradient(
            0f, 0f, 0f, size,
            startColor, endColor, Shader.TileMode.CLAMP
        )
    }

    /**
     * Create horizontal linear gradient shape
     */
    fun createHorizontalLinearGradient(
        size: Float,
        startColor: Int,
        endColor: Int
    ): LinearGradient {
        return LinearGradient(
            0f, 0f, size, 0f,
            startColor, endColor, Shader.TileMode.CLAMP
        )
    }

    /**
     * Create circle gradient shape
     */
    fun createCircleGradient(
        size: Float,
        startColor: Int,
        endColor: Int
    ): RadialGradient {
        return RadialGradient(
            size / 2f, size / 2f, max(size / 2f, 1f),
            startColor, endColor, Shader.TileMode.CLAMP
        )
    }


    /**
     * Create glow drawable
     */
    fun createDefaultDrawable(
        shape: Shape,
        color: Int
    ) = ShapeDrawable().apply {
        setPadding(Rect())

        this.shape = shape

        paint.style = Paint.Style.FILL
        paint.color = color
    }

    /**
     * Create ripple drawable
     */
    fun createRippleDrawable(
        shape: Shape,
        color: Int
    ): RippleDrawable {
        val colorState = ColorStateList.valueOf(color)
        val content = ShapeDrawable().apply {
            setPadding(Rect())
            this.shape = shape
            paint.style = Paint.Style.FILL
            paint.color = Color.WHITE
        }

        return RippleDrawable(colorState, null, content)
    }


    fun getCutRectPath(
        rect: RectF,
        leftTop: Float,
        leftBottom: Float,
        rightTop: Float,
        rightBottom: Float
    ): Path {
        val minLength = min(rect.width(), rect.height())

        return if (leftTop > minLength
            || leftBottom > minLength
            || rightTop > minLength
            || rightBottom > minLength
        ) {
            createDefaultCutRectPath(rect)
        } else {
            createCutRectPath(rect, leftTop, leftBottom, rightTop, rightBottom)
        }
    }

    private fun createDefaultCutRectPath(rect: RectF) = Path().apply {
        addRect(rect, Path.Direction.CCW)
    }

    private fun createCutRectPath(
        rect: RectF,
        leftTop: Float,
        leftBottom: Float,
        rightTop: Float,
        rightBottom: Float
    ) = Path().apply {
        // start left top
        moveTo(0f, leftTop)
        lineTo(leftTop, 0f)

        // line to right top
        lineTo(rect.width() - rightTop, 0f)
        lineTo(rect.width(), rightTop)

        // line to right bottom
        lineTo(rect.width(), rect.height() - rightBottom)
        lineTo(rect.width() - rightBottom, rect.height())

        // line to left bottom
        lineTo(leftBottom, rect.height())
        lineTo(0f, rect.height() - leftBottom)

        // close
        close()
    }

    /**
     * Int value to DP
     */
    fun intToDp(value: Int): Float {
        val display = Resources.getSystem().displayMetrics
        return value * display.density + 0.5f
    }
}