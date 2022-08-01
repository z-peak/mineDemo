package com.zfeng.sample.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class DashboardView : View {

    private val RADIUS = Utils.dp2px(150)
    private val OPEN_ANGLE = 120f
    private val STROKE_WIDTH = Utils.dp2px(3)
    private val POINTER_LENGTH = Utils.dp2px(100)

    private val path = Path()
    private lateinit var pathMeasure: PathMeasure
    private lateinit var effect: PathDashPathEffect
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dash = Path()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = STROKE_WIDTH
        dash.addRect(0f, 0f, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CCW)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + OPEN_ANGLE / 2,
            360 - OPEN_ANGLE
        )

        pathMeasure = PathMeasure(path, false)
        effect = PathDashPathEffect(
            dash,
            (pathMeasure.length - Utils.dp2px(2)) / 20,
            0f,
            PathDashPathEffect.Style.ROTATE
        )

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 画弧
        canvas?.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + OPEN_ANGLE / 2,
            360 - OPEN_ANGLE,
            false,
            paint
        )

        // 画刻度
        paint.pathEffect = effect
        canvas?.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + OPEN_ANGLE / 2,
            360 - OPEN_ANGLE,
            false,
            paint
        )
        paint.pathEffect = null

        // 画指针
        canvas?.drawLine(
            width / 2f,
            height / 2f,
            (width / 2f + POINTER_LENGTH * Math.cos(getAngleFromMark(5)).toFloat()),
            (height / 2f + POINTER_LENGTH * Math.sin(getAngleFromMark(5)).toFloat()), paint
        )
    }

    private fun getAngleFromMark(mark: Int): Double {
        return Math.toRadians(OPEN_ANGLE + (360 - OPEN_ANGLE) / 20.0 * mark)
    }

}