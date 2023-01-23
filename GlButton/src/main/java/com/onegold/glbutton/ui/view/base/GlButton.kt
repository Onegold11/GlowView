package com.onegold.glbutton.ui.view.base

import android.content.Context
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatButton
import com.onegold.glbutton.R
import com.onegold.glbutton.enums.GradientMode
import com.onegold.glbutton.utils.GlButtonUtils
import com.onegold.glbutton.utils.GlValue

/**
 * Abstract GlButton class
 */
abstract class GlButton: AppCompatButton {
    /** Solid color */
    private var _solidColor = 0
    private var _gradientColor = GlValue.NON_COLOR

    /** Stroke color */
    private var _strokeColor = GlValue.NON_COLOR
    private var _glowColor = GlValue.NON_COLOR

    /** size */
    /** width(xml) = glowSize + strokeWidth + contentSize(auto) */
    private var _strokeWidth: Float = 0f
    private var _glowWidth: Float = 0f

    /** mode */
    private var _isStroke = true
    private var _isGlow = true
    private var _gradientMode: Int = GradientMode.VERTICAL.value

    constructor(context: Context) : super(context) {
        initDefaultValue()
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initDefaultValue()
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initDefaultValue()
        init(attrs, defStyle)
    }

    /**
     *  초기값 설정
     */
    private fun initDefaultValue() {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)

        // Solid color
        _solidColor = typedValue.data
        _gradientColor = GlValue.NON_COLOR
        _strokeColor = GlValue.NON_COLOR
        _glowColor = GlValue.NON_COLOR

        // size
        _strokeWidth = GlButtonUtils.intToDp(GlValue.STROKE_WIDTH)
        _glowWidth = GlButtonUtils.intToDp(GlValue.GLOW_WIDTH)

        // mode
        _isStroke = true
        _isGlow = true
        _gradientMode = GradientMode.VERTICAL.value

        initAttrDefaultValue()
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.GlButton, defStyle, 0
        )


        // Solid color
        _solidColor = a.getColor(R.styleable.GlButton_gl_backgroundColor, _solidColor)
        _gradientColor = a.getColor(R.styleable.GlButton_gl_gradientColor, _gradientColor)

        // Stroke color
        _strokeColor = a.getColor(R.styleable.GlButton_gl_strokeColor, _strokeColor)
        _glowColor = a.getColor(R.styleable.GlButton_gl_glowColor, _glowColor)

        // size
        _strokeWidth = a.getDimension(R.styleable.GlButton_gl_strokeWidth, _strokeWidth)
        _glowWidth = a.getDimension(R.styleable.GlButton_gl_glowWidth, _glowWidth)

        // mode
        _isStroke = a.getBoolean(R.styleable.GlButton_gl_strokeVisible, _isStroke)
        _isGlow = a.getBoolean(R.styleable.GlButton_gl_glowVisible, _isGlow)
        _gradientMode = a.getInt(R.styleable.GlButton_gl_gradient_mode, _gradientMode)

        a.recycle()

        initAttrValue(attrs, defStyle)
    }

    /**
     * Create background drawable
     */
    private fun createBackgroundDrawable(): Drawable {
        val drawableList = arrayListOf<Drawable>()

        // Create glow
        if (_glowColor != GlValue.NON_COLOR && _isGlow) {
            drawableList.add(createGlowDrawable())
        }

        // Create stroke
        if (_strokeColor != GlValue.NON_COLOR && _isStroke) {
            drawableList.add(createStrokeDrawable())
        }

        // Create gradient background or normal background
        val paint = if (_gradientColor != GlValue.NON_COLOR) {
            val drawable = createGradientDrawable()
            drawableList.add(drawable)
            drawable.paint
        } else {
            val drawable = createDefaultDrawable()
            drawableList.add(drawable)
            drawable.paint
        }
        setLayerType(LAYER_TYPE_HARDWARE, paint)

        // Create ripple background
        drawableList.add(createRippleBackground())

        val drawable = LayerDrawable(drawableList.toTypedArray())

        // Set drawable inset
        val value = _glowWidth.toInt()
        for (i in drawableList.indices) {
            drawable.setLayerInset(i, value, value, value, value)
        }

        return drawable
    }

    /**
     * Create gradient drawable
     */
    private fun createGradientDrawable(): ShapeDrawable =
        GlButtonUtils.createGradientDrawable(getShape(), createGradient())


    /**
     * Create default drawable
     */
    private fun createDefaultDrawable(): ShapeDrawable =
        GlButtonUtils.createDefaultDrawable(getShape(), _solidColor)


    /**
     * Create stroke drawable
     */
    private fun createStrokeDrawable(): ShapeDrawable =
        GlButtonUtils.createStrokeDrawable(getShape(), _strokeColor, _strokeWidth)

    /**
     * Create glow drawable
     */
    private fun createGlowDrawable(): ShapeDrawable =
        GlButtonUtils.createGlowDrawable(getShape(), _glowColor, _glowWidth)

    /**
     * Get view gradient
     */
    private fun createGradient(): Shader = when (_gradientMode) {
        GradientMode.VERTICAL.value -> GlButtonUtils
            .createVerticalLinearGradient(width.toFloat(), _solidColor, _gradientColor)
        GradientMode.HORIZONTAL.value -> GlButtonUtils
            .createHorizontalLinearGradient(width.toFloat(), _solidColor, _gradientColor)
        else -> GlButtonUtils
            .createCircleGradient(width.toFloat(), _solidColor, _gradientColor)
    }

    /**
     * Get ripple effect background
     */
    private fun createRippleBackground() =
        GlButtonUtils.createRippleDrawable(getShape(), GlValue.RIPPLE_COLOR)

    override fun onFinishInflate() {
        super.onFinishInflate()

        background = createBackgroundDrawable()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        background = createBackgroundDrawable()
    }

    /**
     * Get view shape
     */
    protected abstract fun getShape(): Shape

    /**
     * Init attr value default
     */
    protected abstract fun initAttrDefaultValue()

    /**
     * Init attr value
     */
    protected abstract fun initAttrValue(attrs: AttributeSet?, defStyle: Int)
}