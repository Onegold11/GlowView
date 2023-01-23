package com.onegold.glbutton.ui.view.button

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import com.onegold.glbutton.R
import com.onegold.glbutton.ui.shape.GlRoundRectShape
import com.onegold.glbutton.ui.view.base.GlButton
import com.onegold.glbutton.utils.GlButtonUtils
import com.onegold.glbutton.utils.GlValue.CORNER_RADIUS

/**
 * Round corner glow button
 */
class GlRoundButton : GlButton {
    /** corner */
    private var _cornerRadius: Float = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    /**
     * Get view shape
     */
    override fun getShape(): Shape = GlRoundRectShape(_cornerRadius)

    override fun initAttrDefaultValue() {
        // corner
        _cornerRadius = GlButtonUtils.intToDp(CORNER_RADIUS)
    }

    @SuppressLint("CustomViewStyleable")
    override fun initAttrValue(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.GlRoundButton, defStyle, 0
        )

        // corner
        _cornerRadius = a.getDimension(R.styleable.GlRoundButton_gl_cornerRadius, _cornerRadius)

        a.recycle()
    }
}