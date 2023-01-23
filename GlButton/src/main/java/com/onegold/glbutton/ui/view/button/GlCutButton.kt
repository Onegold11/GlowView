package com.onegold.glbutton.ui.view.button

import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import com.onegold.glbutton.R
import com.onegold.glbutton.ui.shape.GlCutRectShape
import com.onegold.glbutton.ui.view.base.GlButton
import com.onegold.glbutton.utils.GlButtonUtils
import com.onegold.glbutton.utils.GlValue

class GlCutButton : GlButton {
    /** corner */
    private var _leftTopCorner: Float = 0f
    private var _leftBottomCorner: Float = 0f
    private var _rightTopCorner: Float = 0f
    private var _rightBottomCorner: Float = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun getShape(): Shape = GlCutRectShape(
        _leftTopCorner, _leftBottomCorner, _rightTopCorner, _rightBottomCorner
    )

    override fun initAttrDefaultValue() {
        // corner
        _leftTopCorner = GlButtonUtils.intToDp(GlValue.CUT_CORNER_VALUE)
        _leftBottomCorner = GlButtonUtils.intToDp(GlValue.CUT_CORNER_VALUE)
        _rightTopCorner = GlButtonUtils.intToDp(GlValue.CUT_CORNER_VALUE)
        _rightBottomCorner = GlButtonUtils.intToDp(GlValue.CUT_CORNER_VALUE)
    }

    override fun initAttrValue(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.GlCutButton, defStyle, 0
        )

        // corner
        _leftTopCorner =
            a.getDimension(R.styleable.GlCutButton_gl_leftTopCorner, _leftBottomCorner)
        _leftBottomCorner =
            a.getDimension(R.styleable.GlCutButton_gl_leftBottomCorner, _leftBottomCorner)
        _rightTopCorner =
            a.getDimension(R.styleable.GlCutButton_gl_rightTopCorner, _rightTopCorner)
        _rightBottomCorner =
            a.getDimension(R.styleable.GlCutButton_gl_rightBottomCorner, _rightBottomCorner)

        a.recycle()
    }
}